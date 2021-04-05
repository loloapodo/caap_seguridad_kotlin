package com.ello.twelveseconds

import android.provider.MediaStore
import android.util.Log
import com.parse.ParseClassName
import com.parse.ParseFile
import com.parse.ParseGeoPoint
import com.parse.ParseObject
import java.io.*

@ParseClassName("tabla_formulario")
class Formulario : ParseObject() {





 companion object {
        val class_name = "tabla_formulario"
        val field_created = "createdAt"
        val field_updated = "updatedAt"
        val field_nombre = "nombre"

        val field_fecha = "fecha"
        val field_fecha_limite = "fecha_limite"
    }


    var nombre: String?
        get() = getString(field_nombre)
        set(arg) { put(field_nombre, arg!!) }

    var fecha: Long?
        get() = getLong(field_fecha)
        set(arg) { put(field_fecha, arg!!) }

        var fecha_limite: Long?
        get() = getLong(field_fecha_limite)
        set(arg) { put(field_fecha_limite, arg!!) }

    
}