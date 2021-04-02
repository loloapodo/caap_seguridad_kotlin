package com.ello.kotlinseguridad.Activ.Login

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.ello.kotlinseguridad.BIN.BIN
import com.ello.kotlinseguridad.Estado
import com.ello.kotlinseguridad.ParseObj.Usuario

import com.ello.kotlinseguridad.drawer1
import com.ello.kotlinseguridad.drawer2
import com.parse.ParseQuery

class LoginVM(var cxt: Context) : ViewModel() {

    var estado = MutableLiveData<Estado>()

    init {
        estado.value=Estado.Idle
    }


         fun Autentificar(str_usuario: String, str_contrasena:String, fgusuario: () -> Unit,fgadm: () -> Unit,fbqueryNotFound: () -> Unit, fb: () -> Unit)
        {
            estado.value=Estado.Network
            val query = ParseQuery.getQuery<Usuario>(Usuario.class_name)
            query.whereEqualTo(Usuario.field_usuario,str_usuario)
            query.whereEqualTo(Usuario.field_contrasena,str_contrasena)
            query.getFirstInBackground {usuario, e ->
                estado.value=Estado.Idle
                if (e==null){
                    if (usuario==null){Log.e("Error","TODO notificar al usuario");return@getFirstInBackground;}

                        if(usuario.adm){ LogearseComoAdm(usuario);fgadm() }
                        else                 {LogearseComoUsuario(usuario);fgusuario()}

                }else if (e.message.equals("no results found for query"))
                {
                    fbqueryNotFound()
                    Log.e("Error log","Decir que la cuenta no existe")
                }

                else {
                    fb()
                    Log.e("Error login",e.message)
                }
            }
        }


    fun LogearseComoAdm(u:Usuario) {

        cxt.startActivity(Intent(cxt,drawer1::class.java))
        BIN.SALVAR_USUARIO_LOGED(u)
    }

    fun LogearseComoUsuario(u:Usuario) {

        cxt.startActivity(Intent(cxt, drawer2::class.java))
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
