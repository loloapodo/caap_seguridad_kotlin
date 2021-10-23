package com.ello.kotlinseguridad.simple

import android.content.Context
import android.content.res.Resources
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ello.kotlinseguridad.bin.CRUD
import com.ello.kotlinseguridad.bin.PlantillaReporte
import com.ello.kotlinseguridad.bin.Snippetk
import com.ello.kotlinseguridad.parseobj.Actividad
import com.ello.kotlinseguridad.parseobj.Pregunta
import com.ello.kotlinseguridad.parseobj.Respuesta
import com.ello.kotlinseguridad.parseobj.Usuario
import com.ello.twelveseconds.Formulario
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.text.ParseException


class RespuestasPorFechaVM(val filesdir: File, val resourcesss:Resources,val context: Context) : ViewModel() {


    var ListaRespuestas=ArrayList<Respuesta>()



    fun CargarRespuestas(desde:Long,hasta:Long,fg: (respuestas:List<Respuesta>) -> Unit, fb:(str:String) -> Unit) {


        try {

            viewModelScope.launch (Dispatchers.IO){
                CRUD.CargarTodasRespuestasRangeFechas(desde,hasta,{respuestas->

                    Log.d("RespuestasRangeFechas","Total de respuestas:${respuestas.size}")
                    if (respuestas.isNullOrEmpty()) {
                        fb("No se encontraron reportes para esas fechas")
                    }
                    else{
                        ListaRespuestas.addAll(respuestas)
                        fg(respuestas)
                    }
                },{
                    fb("Ha ocurrido un error")
                })
            }
        }catch (e: ParseException){fb(e.toString())}










    }

    fun BuscaroLLenarPlantilla(objectId: String,fg: () -> Unit,fb: (str: String) -> Unit) {
       val rs = ListaRespuestas.filter { it.objectId==objectId}
        if (rs.isEmpty()){fb("Error al filtrar id"); return}

        val respTrue=rs[0]
        if (PlantillaReporte.BuscarIdLocalyAbrir(respTrue.objectId,context)){fg();return}







    viewModelScope.launch  { withContext(Dispatchers.IO){



        val respuestasElegidas=ListaRespuestas.filter {
            respTrue.ref_formulario==it.ref_formulario&&
                    respTrue.ref_actividad==it.ref_actividad&&
                    respTrue.ref_usuario==it.ref_usuario
        }

        val plantilla= PlantillaReporte(respTrue.objectId)

        for (r in respuestasElegidas){
            Log.d("analizando", r.objectId)


            val pregunta: Pregunta? = r.ref_pregunta as Pregunta?
            if (pregunta!=null){
                Log.d("La pregunta","not null")
                if (pregunta.isDataAvailable){
                    var num=1
                    pregunta.numero?.let { num=it+1 }

                    val str_preg= num.toString() + "- " + pregunta.nombre

                    r.respuesta?.let { plantilla.addPregResp(str_preg, it,r.checked) }
                }
            }




            if (r.firs_of_list){

                val esta_act= r.ref_actividad?.fetch() as Actividad?

                val esta_form=r.ref_formulario?.fetch() as Formulario?

                val esta_usu=r.ref_usuario?.fetch() as Usuario?


                plantilla.setDocId(r.objectId)
                plantilla.setUpdt(Snippetk.LeerFechaR(r.fecha))


                if (esta_act != null) {
                    esta_act.nombre?.let { plantilla.setActName(it) }
                }
                if (esta_act != null) {
                    esta_act.desc?.let { plantilla.setActDesc(it) }
                }
                if (esta_act != null) {
                    plantilla.setActDate(Snippetk.LeerFechaR(esta_act.fecha))
                }
                if (esta_form != null) {
                    esta_form.nombre?.let { plantilla.setFormName(it) }
                }
                if (esta_form != null) {
                    esta_form.tipo?.let { plantilla.setFormType(it) }
                }

                if (esta_act != null) {
                    esta_act.sitio?.let { plantilla.setActSitio(it) }
                }
                if (esta_act != null) {
                    esta_act.ubicacion?.let {

                        if(it.length>34){
                            val pos1=it.indexOf("Lat:",0,true)
                            val pos2=it.indexOf("Lon:",5,true)
                            plantilla.setActUbicac(it.substring(pos1+4,pos1+4+9)+"; "+it.substring(pos2+4,pos2+4+9))
                            Log.e("pos1 y pos2","$pos1 y $pos2")
                            Log.e("substr1 y substr2","${it.substring(pos1,pos1+13)} y ${it.substring(pos2,pos2+13)}")
                        } else {
                            plantilla.setActUbicac(it)
                        }


                    }
                }
                plantilla.setFormCantPreg(respuestasElegidas.size.toString())

                if (esta_usu != null) {
                    if(esta_usu.nom_apell!=null&&esta_usu.apell!=null){plantilla.setPersName(esta_usu.nom_apell+" "+esta_usu.apell)} else{esta_usu.nom_apell?.let { plantilla.setPersName(it) }}
                }

                if (esta_usu != null) {
                    esta_usu.cedula?.let { plantilla.setPersCedul(it) }
                }
                if (esta_usu != null) {
                    esta_usu.direccion?.let { plantilla.setPersDirecc(it) }
                }
                if (esta_usu != null) {
                    esta_usu.telefono?.let { plantilla.setPersTelef(it) }
                }

                r.equipos?.let {
                    var str=it.replace("*",", ")
                    if (str[str.length-2] ==','){
                        Log.e("Equipos","Al final existe una coma");
                        str=str.substring(0,str.length-2)
                    }
                    Log.e("anadiendo","equipos");
                    plantilla.setEquipami(str)}


                plantilla.addImage(r.e0)
                plantilla.addImage(r.e1)
                plantilla.addImage(r.e2)
                plantilla.addImage(r.e3)
                plantilla.addImage(r.e4)
                plantilla.addImage(r.e5)
                plantilla.addImage(r.e6)
                plantilla.addImage(r.e7)
                plantilla.addImage(r.e8)
                Log.d("llego","aqui--")
            }else{Log.d("No es first","no first")}
            Log.d("llego","aqui--2")
        }

        Log.d("llego","aqui--3")
        plantilla.PoblarArchivo(filesdir,resourcesss)
        val ruta=plantilla.ObtenerRutaPlantillaSalida()

        if (ruta==null){
            fb("Ha ocurrido un error")
        }else
        {
            plantilla.AbrirXLS(context)
            fg()
        }




    }
    }








    }



}