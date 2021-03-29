package com.ello.kotlinseguridad

import android.app.Application
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.ello.kotlinseguridad.ParseObj.Actividad
import com.ello.kotlinseguridad.ParseObj.Pregunta
import com.ello.kotlinseguridad.ParseObj.Respuesta
import com.ello.kotlinseguridad.ParseObj.Usuario
import com.ello.twelveseconds.Formulario
import com.parse.Parse
import com.parse.ParseObject


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
        super.onCreate()
    }
   public fun setVM(){}

}
