package com.ello.kotlinseguridad.Activ.Login

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModel

import com.ello.kotlinseguridad.BIN
import com.ello.kotlinseguridad.ParseObj.Usuario
import com.ello.kotlinseguridad.U.UsuarAct
import com.ello.kotlinseguridad.drawer1
import com.parse.ParseQuery

class LoginVM(var cxt: Context) : ViewModel() {



    init {

        Autentificar("i","i",{},{},{},{});

    }


         fun Autentificar(str_usuario: String, str_contrasena:String, fgusuario: () -> Unit,fgadm: () -> Unit,fbqueryNotFound: () -> Unit, fb: () -> Unit)
        {

            val query = ParseQuery.getQuery<Usuario>(Usuario.class_name)
            query.whereEqualTo(Usuario.field_usuario,str_usuario)
            query.whereEqualTo(Usuario.field_contrasena,str_contrasena)
            query.getFirstInBackground {usuario, e ->

                if (e==null){
                    if (usuario==null){Log.e("Error","TODO notificar al usuario");return@getFirstInBackground;}
                        usuario.pin(BIN.PIN_USUARIO_LOGUEADO)
                        if(usuario.adm==true){ LogearseComoAdm(usuario);fgadm() }
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
    }

    fun LogearseComoUsuario(u:Usuario) {

        cxt.startActivity(Intent(cxt, UsuarAct::class.java))
    }










}
