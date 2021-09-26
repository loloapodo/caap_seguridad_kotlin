package com.ello.kotlinseguridad.parseobj

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
        var field_nom = "nom_apell"
        var field_apell = "apell"
        var field_cedula = "cedula"
        var field_rol = "rol"
        var field_direccion = "direccion"
        var field_telefono = "telefono"
        var field_foto = "foto"

    }

    var usuario: String?
        get() = getString(field_usuario)
        set(arg) { put(field_usuario, arg!!) }

    var contrasena: String?
        get() = getString(field_contrasena)
        set(arg) { put(field_contrasena, arg!!)}

    var nom_apell: String?
        get() = getString(field_nom)
        set(arg) { put(field_nom, arg!!)}

    var apell: String?
        get() = getString(field_apell)
        set(arg) { put(field_apell, arg!!)}

    var cedula: String?
        get() = getString(field_cedula)
        set(arg) { put(field_cedula, arg!!)}

    var rol: String?
        get() = getString(field_rol)
        set(arg) { put(field_rol, arg!!)}

    var direccion: String?
        get() = getString(field_direccion)
        set(arg) { put(field_direccion, arg!!)}

    var telefono: String?
        get() = getString(field_telefono)
        set(arg) { put(field_telefono, arg!!)}


    var adm: Boolean
        get() = getBoolean(field_adm)
        set(arg) { put(field_adm, arg) }

    var foto: ParseFile?
        get() = getParseFile(field_foto)
        set(arg) { put(field_foto, arg!!) }




}