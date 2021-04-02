package com.ello.kotlinseguridad.ParseObj

import com.parse.ParseClassName
import com.parse.ParseFile
import com.parse.ParseObject

@ParseClassName("tabla_usuario")
class Usuario : ParseObject() {



    companion object {

        var class_name = "tabla_usuario"
        var field_created = "createdAt"
        var field_updated = "updatedAt"
        var field_usuario = "usuario"
        var field_contrasena = "contrasena"
        var field_adm = "adm"
        var field_nom_apell = "nom_apell"
        var field_cedula = "cedula"
        var field_foto = "foto"

    }

    var usuario: String?
        get() = getString(field_usuario)
        set(arg) { put(field_usuario, arg!!) }

    var contrasena: String?
        get() = getString(field_contrasena)
        set(arg) { put(field_contrasena, arg!!)}

    var nom_apell: String?
        get() = getString(field_nom_apell)
        set(arg) { put(field_nom_apell, arg!!)}

    var cedula: String?
        get() = getString(field_cedula)
        set(arg) { put(field_cedula, arg!!)}

    var adm: Boolean
        get() = getBoolean(field_adm)
        set(arg) { put(field_adm, arg) }

    var foto: ParseFile?
        get() = getParseFile(field_foto)
        set(arg) { put(field_foto, arg!!) }




}