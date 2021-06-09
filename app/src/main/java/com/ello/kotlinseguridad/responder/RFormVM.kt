package com.ello.kotlinseguridad.responder

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ello.kotlinseguridad.BIN.BIN
import com.ello.kotlinseguridad.BIN.CRUD
import com.ello.kotlinseguridad.Estado
import com.ello.kotlinseguridad.ParseObj.Pregunta
import com.ello.kotlinseguridad.ParseObj.Respuesta
import com.ello.kotlinseguridad.ParseObj.Usuario
import com.ello.twelveseconds.Formulario
import com.parse.ParsePush
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject
import java.util.*


class RFormVM : ViewModel() {



    lateinit var id: String
    lateinit var form: Formulario


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
            viewModelScope.launch(Dispatchers.IO){CRUD.CargarTodasPreguntasDelFormulario(f, {_listado.value=it},{})}
        },{})
    }

    fun EnviarRespuesta(c:Context,listResp: ArrayList<Respuesta>,listPreg:ArrayList<Pregunta>,listBitm:ArrayList<Bitmap?>,fvalidar: (mesg:String) -> Unit,fg: () -> Unit,fb: () -> Unit) {

       val validation= Validar(listResp)
       if (validation.isNotEmpty()){
           fvalidar(validation)
           return
       }
        estado.value= Estado.SearchLocat
        BIN.getMyParseGeoLocation(c) {geopoint->
            estado.value= Estado.Network

            val u=BIN.CARGAR_USUARIO_LOGED()
            val fecha=Calendar.getInstance().timeInMillis
            var good =0
            var bad= 0
            val size=listResp.size

            Log.e("EnviarRespuesta","before global scope")

            GlobalScope.launch(Dispatchers.IO) {



                Log.e("listResp","tamaño ${listResp.size}")
                for (i in 0 until size) {

                    listResp[i].fecha=fecha
                    listResp[i].ubicacion=geopoint




                    Log.e("listResp","iteration ${i.toString()}")
                    CRUD.RegistrarRespuesta(u!!,form,listPreg[i],listResp[i],listBitm[i],
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