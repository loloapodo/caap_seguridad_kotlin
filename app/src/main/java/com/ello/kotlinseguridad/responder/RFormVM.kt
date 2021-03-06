package com.ello.kotlinseguridad.responder

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ello.kotlinseguridad.bin.BIN
import com.ello.kotlinseguridad.bin.CRUD
import com.ello.kotlinseguridad.bin.Snippetk
import com.ello.kotlinseguridad.Estado
import com.ello.kotlinseguridad.parseobj.*
import com.ello.twelveseconds.Formulario
import com.parse.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import kotlin.coroutines.coroutineContext


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
        form=BIN.getThisForm()!!
        act=BIN.getThisAct()!!
        CargarPreguntas(form)
        CargarEquipam()

    }

    private fun CargarEquipam() {
        viewModelScope.launch(Dispatchers.Main) { CRUD.CargarTodasEquipamientoLocal({_equipamietos.value=it;CargarServidorEQ()},{CargarServidorEQ()}) }
    }

    fun CargarPreguntas(form:Formulario){
        viewModelScope.launch(Dispatchers.Main){CRUD.CargarTodasPreguntasDelFormularioLocal(form, {_listado.value=it},
                { viewModelScope.launch(Dispatchers.IO){CRUD.CargarTodasPreguntasDelFormulario(form,{_listado.value=it},{})} })}

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





                listResp[0].firs_of_list=true
                listResp[0].equipos=equipamientos


                if (BIN.TengoInternet(c)){
                    GlobalScope.launch(Dispatchers.IO) {
                Log.e("Enviando Con Internet","Enviando Con Internet")

                if (listBitm.size==0){ Continuar(listResp,size,fecha,geopoint,u!!,listPreg,listBitm,fg, fb)}
               else if (listBitm.size>0){

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

                    }
                }else{
                    Log.e("Enviando SIN Internet","Enviando SIN Internet")
                    Continuar2(listResp,size,fecha,geopoint,u!!,listPreg,listBitm,fg, fb)

                }





        }


    }




    private  fun Continuar(listResp:List<Respuesta>, size: Int, fecha:Long, geopoint:ParseGeoPoint, u: Usuario, listPreg: ArrayList<Pregunta>, listBitm: ArrayList<Bitmap>, fg: () -> Unit, fb: () -> Unit) {

        var good =0
        var bad= 0


        Log.e("listPreg","tama??o ${listPreg.size}")
        Log.e("listResp","tama??o ${listResp.size}")
        for (i in 0 until size) {

            listResp[i].fecha=fecha
            listResp[i].ubicacion=geopoint




            CRUD.RegistrarRespuesta(u,form,listPreg[i],listResp[i],act,
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

    private  fun Continuar2(listResp:List<Respuesta>, size: Int, fecha:Long, geopoint:ParseGeoPoint, u: Usuario, listPreg: ArrayList<Pregunta>, listBitm: ArrayList<Bitmap>, fg: () -> Unit, fb: () -> Unit) {

        for (i in 0 until size) {
            listResp[i].fecha=fecha
            listResp[i].ubicacion=geopoint
            listResp[i].ref_pregunta=listPreg[i]
            listResp[i].ref_formulario=form
            listResp[i].ref_usuario=u
            listResp[i].ref_actividad=act



            listResp[i].saveEventually {
                    e->
                if(e==null){


                    if (listResp[i].firs_of_list){
                        EnviarNotificacion(u);Log.e("ok","se envia la notificacion")}
                        Log.e("ok eventually",listResp[i].respuesta!!)


                    if (listBitm.size>0){

                        var C=listBitm.size

                        if (listBitm.size>0){val f0= Snippetk.BitmapToParseFile(listBitm[0]);f0.saveInBackground { e: ParseException? ->       if (e==null){C--;Log.e("contador","$C");listResp[0].e0=f0;if (C==0){Editar(listResp[i])}}else {Log.e("error","33w3")}
                            if (listBitm.size>1){val f1= Snippetk.BitmapToParseFile(listBitm[1]);f1.saveInBackground { e: ParseException? ->     if (e==null){C--;Log.e("contador","$C");listResp[0].e1=f1;if (C==0){Editar(listResp[i])}}else {Log.e("error","33w3")}
                                if (listBitm.size>2){val f2= Snippetk.BitmapToParseFile(listBitm[2]);f2.saveInBackground { e: ParseException? ->   if (e==null){C--;Log.e("contador","$C");listResp[0].e2=f2;if (C==0){Editar(listResp[i])}}else {Log.e("error","33w3")}
                                    if (listBitm.size>3){val f3= Snippetk.BitmapToParseFile(listBitm[3]);f3.saveInBackground { e: ParseException? ->   if (e==null){C--;Log.e("contador","$C");listResp[0].e3=f3;if (C==0){Editar(listResp[i])}}else {Log.e("error","33w3")}
                                        if (listBitm.size>4){val f4= Snippetk.BitmapToParseFile(listBitm[4]);f4.saveInBackground { e: ParseException? ->    if (e==null){C--;Log.e("contador","$C");listResp[0].e4=f4;if (C==0){Editar(listResp[i])}}else {Log.e("error","33w3")}
                                            if (listBitm.size>5){val f5= Snippetk.BitmapToParseFile(listBitm[5]);f5.saveInBackground { e: ParseException? ->  if (e==null){C--;Log.e("contador","$C");listResp[0].e5=f5;if (C==0){Editar(listResp[i])}}else {Log.e("error","33w3")}
                                                if (listBitm.size>6){val f6= Snippetk.BitmapToParseFile(listBitm[6]);f6.saveInBackground { e: ParseException? -> if (e==null){C--;Log.e("contador","$C");listResp[0].e6=f6;if (C==0){Editar(listResp[i])}}else {Log.e("error","33w3")}
                                                    if (listBitm.size>7){val f7= Snippetk.BitmapToParseFile(listBitm[7]);f7.saveInBackground { e: ParseException? -> if (e==null){C--;Log.e("contador","$C");listResp[0].e7=f7;if (C==0){Editar(listResp[i])}}else {Log.e("error","33w3")}
                                                        if (listBitm.size>8){val f8= Snippetk.BitmapToParseFile(listBitm[8]);f8.saveInBackground { e: ParseException? -> if (e==null){C--;Log.e("contador","$C");listResp[0].e8=f8;if (C==0){Editar(listResp[i])}}else {Log.e("error","33w3")}
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











                }
                else {
                    Log.e("error eventually","save eventually")
                    e.printStackTrace()
                }
            }
        }
        EsperandoFinal2(u,fg)





    }

    private fun Editar(respuesta: Respuesta) {

        Log.e("Llego a editar","Llego a editar")
        respuesta.saveInBackground { e->
            if (e==null){Log.e("Se salvo imagenes","Se salvo imagenes")}
            else{Log.e("No salvo imagenes","No salvo imagenes")}
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
        Log.e("EsperandoFinal","EsperandoFinal")
        if (good+bad==size)
        {
            estado.value= Estado.Idle
        if (good>bad){fg();EnviarNotificacion(u)}
        else {fb()}
        }
    }
    private fun EsperandoFinal2(u:Usuario,fg: () -> Unit) {

            estado.value= Estado.Idle
            fg();
            EnviarNotificacion(u)


    }

    private fun EnviarNotificacion(u:Usuario) {


        val data = JSONObject()
// Put data in the JSON object
// Put data in the JSON object
        try {
            data.put(Usuario.field_nom, u.nom_apell)
            data.put(Actividad.field_nombre, act.nombre)
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