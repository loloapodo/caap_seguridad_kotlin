package com.ello.kotlinseguridad.loginn

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope


import com.ello.kotlinseguridad.bin.BIN
import com.ello.kotlinseguridad.bin.CRUD
import com.ello.kotlinseguridad.Estado

import com.ello.kotlinseguridad.drawer1
import com.ello.kotlinseguridad.drawer2
import com.ello.kotlinseguridad.parseobj.*
import com.ello.twelveseconds.Formulario
import com.parse.ParseObject
import com.parse.ParseQuery
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class LoginVM(var cxt: Context) : ViewModel() {

    var estado = MutableLiveData<Estado>()

    init {
        estado.value=Estado.Idle
        TraerDatosTOAlmacLocal()

    }

    private fun TraerDatosTOAlmacLocal() {




        //if (!BuildConfig.DEBUG){


            val queryForm = ParseQuery.getQuery<Formulario>(Formulario.class_name)
            queryForm.findInBackground { formularios, e ->
                if(e==null){
                    Log.e("Ok","TraerDatosTOAlmacLocal FORMULARIO")
                    ParseObject.unpinAll(BIN.PIN_TODAS_FOR)
                    ParseObject.pinAll(BIN.PIN_TODAS_FOR,formularios)
                }else{
                    Log.e("Error","TraerDatosTOAlmacLocal FORMULARIO")
                }
            }

        val queryIncide = ParseQuery.getQuery<Incidente>(Incidente.class_name)
        queryIncide.findInBackground { incidententes, e ->
            if(e==null){
                Log.e("Ok","TraerDatosTOAlmacLocal Incidente")
                ParseObject.unpinAll(BIN.PIN_TODAS_INC)
                ParseObject.pinAll(BIN.PIN_TODAS_INC,incidententes)
            }else{
                Log.e("Error","TraerDatosTOAlmacLocal Incidente")
            }
        }


        val queryActiv = ParseQuery.getQuery<Actividad>(Actividad.class_name)
        queryActiv.findInBackground { actividades, e ->
            if(e==null){
                Log.e("Ok","TraerDatosTOAlmacLocal ACTIVIDADES")
                for (i in actividades) {
                    viewModelScope.launch {
                        withContext(Dispatchers.IO) {
                            CRUD.CargarTodosUsuariosdeActividad(i, {}, {}) }
                    }

                    i.ref_formulario?.fetchInBackground<Formulario> { formfetched, errr ->
                        if (errr==null){
                            formfetched.pin(i.objectId+BIN.PIN_SUFIJO_ACT_RELATION2FORM)
                        }
                    }
                }

                ParseObject.unpinAll(BIN.PIN_TODAS_ACT)
                ParseObject.pinAll(BIN.PIN_TODAS_ACT,actividades)
            }else{
                Log.e("Error","TraerDatosTOAlmacLocal ACTIVIDADES")
            }
        }

        val queryUsua = ParseQuery.getQuery<Usuario>(Usuario.class_name)
        queryUsua.findInBackground { usuarios, e ->
            if(e==null){
                Log.e("Ok","TraerDatosTOAlmacLocal USUARIOS-EMPLEADOS")
                for (i in usuarios){i.foto?.dataInBackground}
                ParseObject.unpinAll(BIN.PIN_TODAS_USU)
                ParseObject.pinAll(BIN.PIN_TODAS_USU,usuarios)
            }else{
                Log.e("Error","TraerDatosTOAlmacLocal USUARIOS-EMPLEADOS")
            }
        }


        val queryEquip = ParseQuery.getQuery<Equip>(Equip.class_name)
        queryEquip.findInBackground { equipamenmtos, e ->
            if(e==null){
                Log.e("Ok","TraerDatosTOAlmacLocal EQUIPAMENTOS")
                for (i in equipamenmtos){i.foto?.dataInBackground}
                ParseObject.unpinAll(BIN.PIN_TODAS_EQU)
                ParseObject.pinAll(BIN.PIN_TODAS_EQU,equipamenmtos)
            }else{
                Log.e("Error","TraerDatosTOAlmacLocal EQUIPAMENTOS")
            }
        }



        val queryRol = ParseQuery.getQuery<Rol>(Rol.class_name)
        queryRol.findInBackground { roles, e ->
            if(e==null){
                Log.e("Ok","TraerDatosTOAlmacLocal ROLES")
                ParseObject.unpinAll(BIN.PIN_TODAS_ROL)
                ParseObject.pinAll(BIN.PIN_TODAS_ROL,roles)
            }else{
                Log.e("Error","TraerDatosTOAlmacLocal ROLES")
            }
        }





//}
    }


    fun Autentificar(str_usuario: String, str_contrasena:String, fgusuario: () -> Unit,fgadm: () -> Unit,fbqueryNotFound: () -> Unit, fb: () -> Unit)
        {
            estado.value=Estado.Network
            val query = ParseQuery.getQuery<Usuario>(Usuario.class_name)
            query.fromPin(BIN.PIN_TODAS_USU)
            query.whereEqualTo(Usuario.field_usuario, str_usuario.toLowerCase(Locale.ROOT))
            query.whereEqualTo(Usuario.field_contrasena,str_contrasena)
            query.getFirstInBackground {usuario, e ->

                if (e==null){

                    if (usuario==null){Log.e("Error","TODO notificar al usuario");estado.value=Estado.Idle;return@getFirstInBackground;}

                        if(usuario.adm){ LogearseComoAdm(usuario);fgadm() }
                        else                 {LogearseComoUsuario(usuario);fgusuario()}

                }else if (e.message.equals("no results found for query"))
                {
                    estado.value=Estado.Idle
                    fbqueryNotFound()
                    Log.e("Error log","Decir que la cuenta no existe")
                }

                else {
                    estado.value=Estado.Idle
                    fb()
                    Log.e("Error login",e.message!!)
                }
            }
        }


    fun LogearseComoAdm(u:Usuario) {

        cxt.startActivity(Intent(cxt,drawer1::class.java))
        viewModelScope.launch(Dispatchers.IO) {CRUD.CargarTodasPreguntas({},{})}
        BIN.SALVAR_USUARIO_LOGED(u)
    }

    fun LogearseComoUsuario(u:Usuario) {

        cxt.startActivity(Intent(cxt, drawer2::class.java))
        viewModelScope.launch(Dispatchers.IO) {CRUD.CargarTodasPreguntas({},{})}
        BIN.SALVAR_USUARIO_LOGED(u)

    }

    fun Continuar_Con_Seccion():Boolean {

       val u= BIN.CARGAR_USUARIO_LOGED()
       if (u!=null){


           if(u.adm){ LogearseComoAdm(u);}
           else           {LogearseComoUsuario(u);}
           return true
       }
        return false


    }

    fun isNotIdle(): Boolean {
        return estado.value!=Estado.Idle
    }


}
