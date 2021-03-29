package com.ello.kotlinseguridad

import android.util.Log
import com.ello.kotlinseguridad.ParseObj.Actividad
import com.ello.kotlinseguridad.ParseObj.Pregunta
import com.ello.kotlinseguridad.ParseObj.Usuario
import com.ello.twelveseconds.Formulario

class MDebug {

    companion object{


       fun VerUsuarios(list: List<Usuario>){
           for (i in list){
               Log.e(i.usuario,i.contrasena)
           }
       }

        fun VerFormularios(list: List<Formulario>){
            for (i in list){
                Log.e(i.objectId,i.nombre)
            }
        }

        fun VerPreguntas(list: List<Pregunta>){
            for (i in list){
                Log.e(i.objectId,i.nombre)
            }
        }

        fun VerActividades(list: List<Actividad>){
            for (a in list){
                Log.e(a.objectId,a.nombre)
            }
        }

        fun VerActivyUsuarios(act: Actividad, list: List<Usuario>) {
        Log.e("Actividad",act.nombre)
            VerUsuarios(list)

        }

        //fun VerActividadesConUsuarios Metodo nunca funciono!!!! debido a que include no sirve para ParseRelation


    }

}