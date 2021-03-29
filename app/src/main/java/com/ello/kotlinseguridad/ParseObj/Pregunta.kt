package com.ello.kotlinseguridad.ParseObj

import com.ello.twelveseconds.Formulario
import com.parse.ParseClassName
import com.parse.ParseObject

@ParseClassName("tabla_pregunta")
class Pregunta() : ParseObject() {




 


    companion object {
        var class_name = "tabla_pregunta"
        var field_created = "createdAt"
        var field_updated = "updatedAt"
        var field_nombre = "nombre"
        var field_numero = "numero"
        var field_ref_formulario = "ref_formulario"
        
    }


    var nombre: String?
        get() = getString(field_nombre)
        set(arg) { put(field_nombre, arg!!) }

    var numero: Int?
        get() = getInt(field_numero)
        set(arg) { put(field_numero, arg!!) }

    var ref_formulario: ParseObject?
        get() = getParseObject(field_ref_formulario)
        set(arg) { put(field_ref_formulario, arg!!) }


}
