package com.ello.kotlinseguridad

import android.app.Application
import android.util.Log
import com.ello.kotlinseguridad.ParseObj.Actividad
import com.ello.kotlinseguridad.ParseObj.Pregunta
import com.ello.kotlinseguridad.ParseObj.Respuesta
import com.ello.kotlinseguridad.ParseObj.Usuario
import com.ello.twelveseconds.Formulario
import com.parse.*


class AppClass : Application() {
    override fun onCreate() {
        ParseObject.registerSubclass(Usuario::class.java)
        ParseObject.registerSubclass(Pregunta::class.java)
        ParseObject.registerSubclass(Respuesta::class.java)
        ParseObject.registerSubclass(Formulario::class.java)
        ParseObject.registerSubclass(Actividad::class.java)

        Parse.enableLocalDatastore(this)
        Parse.initialize(
            Parse.Configuration.Builder(this)
                    .applicationId("yDpmp5Mn9CU07PYXKy7yUNHumnQqCuqs82VhKh5F")
                    .clientKey("f4IjvGWAUwlR23Z3zJte2FT2kk3sHpg7JxWqlE8N")
                    .server("https://parseapi.back4app.com")
                .enableLocalDataStore()
                .build()
        )


        Log.e("Parse install", "init")
        val installation = ParseInstallation.getCurrentInstallation()
        installation.put("GCMSenderId", "462389221598")
        installation.saveInBackground { e ->
            if (e == null) {
                Log.e("Parse install", "OK")
            } else {
                Log.e("Parse install", "ERROR")
            }
        }







        super.onCreate()
    }
   public fun setVM(){}

}
