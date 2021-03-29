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
        var class_name = "tabla_formulario"
        var field_created = "createdAt"
        var field_updated = "updatedAt"
        var field_nombre = "nombre"
        var field_fecha = "fecha"
        var field_fecha_limite = "fecha_limite"
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