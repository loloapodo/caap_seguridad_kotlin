package com.ello.kotlinseguridad.ParseObj

import com.ello.twelveseconds.Formulario
import com.parse.ParseClassName
import com.parse.ParseObject

@ParseClassName("tabla_rol")
class Rol() : ParseObject() {




 


    companion object {
        var class_name = "tabla_rol"
        var field_created = "createdAt"
        var field_updated = "updatedAt"
        var field_rol = "rol"

        
    }


    var nombre_rol: String?
        get() = getString(field_rol)
        set(arg) { put(field_rol, arg!!) }

}
