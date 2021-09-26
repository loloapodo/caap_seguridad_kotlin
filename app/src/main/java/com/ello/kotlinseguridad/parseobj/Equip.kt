package com.ello.kotlinseguridad.parseobj

import com.parse.ParseClassName
import com.parse.ParseFile
import com.parse.ParseObject

@ParseClassName("tabla_equipamiento")
class Equip : ParseObject() {



    companion object {

        var class_name = "tabla_equipamiento"
        var field_created = "createdAt"
        var field_updated = "updatedAt"

        var field_nombre = "nombre"
        var field_uso = "uso"
        var field_descripcion = "descripcion"
        var field_foto = "foto"

    }

    var nombre: String?
        get() = getString(field_nombre)
        set(arg) { put(field_nombre, arg!!) }

    var uso: String?
        get() = getString(field_uso)
        set(arg) { put(field_uso, arg!!) }

    var descripcion: String?
        get() = getString(field_descripcion)
        set(arg) { put(field_descripcion, arg!!) }


    var foto: ParseFile?
        get() = getParseFile(field_foto)
        set(arg) { put(field_foto, arg!!) }



}