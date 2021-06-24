package com.ello.kotlinseguridad.responder

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ello.kotlinseguridad.BIN.BIN
import com.ello.kotlinseguridad.BIN.CRUD
import com.ello.kotlinseguridad.BIN.Snippetk
import com.ello.kotlinseguridad.Estado
import com.ello.kotlinseguridad.ParseObj.*
import com.ello.twelveseconds.Formulario
import com.parse.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject
import java.util.*


class RFormVM : ViewModel() {



    lateinit var id: String
    lateinit var form: Formulario
    lateinit var act: Actividad



    var _equipamietos = MutableLiveData<List<Equip>>()
    var _listado = MutableLiveData<List<Pregunta>>()
    var estado = MutableLiveData<Estado>()

    init {
        estado.value=Estado.Idle
    }

    fun Cargar()
    {

        estado.value= Estado.Idle
        CRUD.CargarUnFormularioLocal(id,{f ->
            form=f
            viewModelScope.launch(Dispatchers.IO){CRUD.CargarTodasPreguntasDelFormularioLocal(f, {_listado.value=it},{})}
        },{})

        CRUD.CargarUnActividadPIN(BIN.PIN_ACT_SELECTED,{act=it},{})


        viewModelScope.launch(Dispatchers.Main) { CRUD.CargarTodasEquipamientoLocal({_equipamietos.value=it;CargarServidorEQ()},{CargarServidorEQ()}) }



    }

    private fun CargarServidorEQ() {
        viewModelScope.launch (Dispatchers.IO){  CRUD.CargarTodasEquipamiento({_equipamietos.value=it},{})}

    }

    fun EnviarRespuesta(c:Context,listResp: ArrayList<Respuesta>,listPreg:ArrayList<Pregunta>,listBitm:ArrayList<Bitmap>,equipamientos:String,fvalidar: (mesg:String) -> Unit,fg: () -> Unit,fb: () -> Unit) {

       var validation= Validar(listResp)
        if (equipamientos.isNullOrEmpty()){validation="Seleccionar equipamiento"}


       if (validation.isNotEmpty()){
           fvalidar(validation)
           return
       }
        estado.value= Estado.SearchLocat
        BIN.getMyParseGeoLocation(c) {geopoint->
            estado.value= Estado.Network

            val u=BIN.CARGAR_USUARIO_LOGED()
            val fecha=Calendar.getInstance().timeInMillis
            val size=listResp.size

            Log.e("EnviarRespuesta","before global scope")

            GlobalScope.launch(Dispatchers.IO) {



                listResp[0].firs_of_list=true
                listResp[0].equipos=equipamientos

                if (listBitm.size>0){

                var C=listBitm.size


                if (listBitm.size>0){val f0= Snippetk.BitmapToParseFile(listBitm[0]);f0.saveInBackground { e: ParseException? ->       if (e==null){C--;Log.e("contador","$C");listResp[0].e0=f0;if (C==0){Continuar(listResp,size,fecha,geopoint,u!!,listPreg,listBitm,fg, fb)}}else {Log.e("error","33w3")}
                    if (listBitm.size>1){val f1= Snippetk.BitmapToParseFile(listBitm[1]);f1.saveInBackground { e: ParseException? ->     if (e==null){C--;Log.e("contador","$C");listResp[0].e1=f1;if (C==0){Continuar(listResp,size,fecha,geopoint,u!!,listPreg,listBitm,fg, fb)}}else {Log.e("error","33w3")}
                        if (listBitm.size>2){val f2= Snippetk.BitmapToParseFile(listBitm[2]);f2.saveInBackground { e: ParseException? ->   if (e==null){C--;Log.e("contador","$C");listResp[0].e2=f2;if (C==0){Continuar(listResp,size,fecha,geopoint,u!!,listPreg,listBitm,fg, fb)}}else {Log.e("error","33w3")}
                            if (listBitm.size>3){val f3= Snippetk.BitmapToParseFile(listBitm[3]);f3.saveInBackground { e: ParseException? ->   if (e==null){C--;Log.e("contador","$C");listResp[0].e3=f3;if (C==0){Continuar(listResp,size,fecha,geopoint,u!!,listPreg,listBitm,fg, fb)}}else {Log.e("error","33w3")}
                                if (listBitm.size>4){val f4= Snippetk.BitmapToParseFile(listBitm[4]);f4.saveInBackground { e: ParseException? ->    if (e==null){C--;Log.e("contador","$C");listResp[0].e4=f4;if (C==0){Continuar(listResp,size,fecha,geopoint,u!!,listPreg,listBitm,fg, fb)}}else {Log.e("error","33w3")}
                                    if (listBitm.size>5){val f5= Snippetk.BitmapToParseFile(listBitm[5]);f5.saveInBackground { e: ParseException? ->  if (e==null){C--;Log.e("contador","$C");listResp[0].e5=f5;if (C==0){Continuar(listResp,size,fecha,geopoint,u!!,listPreg,listBitm,fg, fb)}}else {Log.e("error","33w3")}
                                        if (listBitm.size>6){val f6= Snippetk.BitmapToParseFile(listBitm[6]);f6.saveInBackground { e: ParseException? -> if (e==null){C--;Log.e("contador","$C");listResp[0].e6=f6;if (C==0){Continuar(listResp,size,fecha,geopoint,u!!,listPreg,listBitm,fg, fb)}}else {Log.e("error","33w3")}
                                            if (listBitm.size>7){val f7= Snippetk.BitmapToParseFile(listBitm[7]);f7.saveInBackground { e: ParseException? -> if (e==null){C--;Log.e("contador","$C");listResp[0].e7=f7;if (C==0){Continuar(listResp,size,fecha,geopoint,u!!,listPreg,listBitm,fg, fb)}}else {Log.e("error","33w3")}
                                                if (listBitm.size>8){val f8= Snippetk.BitmapToParseFile(listBitm[8]);f8.saveInBackground { e: ParseException? -> if (e==null){C--;Log.e("contador","$C");listResp[0].e8=f8;if (C==0){Continuar(listResp,size,fecha,geopoint,u!!,listPreg,listBitm,fg, fb)}}else {Log.e("error","33w3")}
                                                }}
                                            }}
                                        }}
                                    }}
                                }}
                            }}
                        }}
                    }}
                }}
                }
                else{

                    Continuar(listResp,size,fecha,geopoint,u!!,listPreg,listBitm,fg, fb)
                }





            }
        }


    }

    private  fun Continuar(listResp:List<Respuesta>, size: Int, fecha:Long, geopoint:ParseGeoPoint, u: Usuario, listPreg: ArrayList<Pregunta>, listBitm: ArrayList<Bitmap>, fg: () -> Unit, fb: () -> Unit) {


        var good =0
        var bad= 0



        Log.e("listResp","tamaño ${listResp.size}")
        for (i in 0 until size) {

            listResp[i].fecha=fecha
            listResp[i].ubicacion=geopoint




            Log.e("listResp","iteration ${i.toString()}")
            CRUD.RegistrarRespuesta(u!!,form,listPreg[i],listResp[i],act,listBitm[i],
                    {
                        good++
                        Log.e("EnviarRespuesta","good->$good +  bad->$bad")
                        EsperandoFinal(u,good, bad, size,fg,fb)

                    },{
                bad++
                Log.e("EnviarRespuesta","good->$good +  bad->$bad")
                EsperandoFinal(u,good, bad, size,fg,fb)
            })
        }
    }

    private fun Validar(listResp: ArrayList<Respuesta>):String {


        if (!listResp.isNullOrEmpty())
        {

        for (l in listResp){

            if (!l.checked || l.respuesta.isNullOrEmpty()){
                return "Rectifique las respuestas por favor"
                }
            }
        }
return ""
    }

    private fun EsperandoFinal(u:Usuario,good: Int, bad: Int, size:Int,fg: () -> Unit,fb: () -> Unit) {
        if (good+bad==size)
        {
            estado.value= Estado.Idle
        if (good>bad){fg();EnviarNotificacion(u)}
        else {fb()}
        }
    }

    private fun EnviarNotificacion(u:Usuario) {


        val data = JSONObject()
// Put data in the JSON object
// Put data in the JSON object
        try {
            data.put(Usuario.field_nom, u.nom_apell)
            data.put(Formulario.field_nombre, form.nombre)
        } catch (e: JSONException) {
            // should not happen
            throw IllegalArgumentException("unexpected parsing error", e)
        }
// Configure the push
// Configure the push
        val push = ParsePush()
        push.setData(data)
        push.sendInBackground {e->
            if (e==null){Log.e("Notificacion","SENT")}
            else{Log.e("Notificacion","NOT SENT")}

        }




    }

    fun isNotIdle(): Boolean {
       return estado.value!=Estado.Idle
    }


}