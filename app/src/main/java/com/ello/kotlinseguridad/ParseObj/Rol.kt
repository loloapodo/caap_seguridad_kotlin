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
        var field_per_empleado =    "per_empleado"
        var field_per_formulario =  "per_formulario"
        var field_per_equipamiento= "per_equipamiento"
        var field_per_actividad = "per_actividad"

        
    }


    var nombre_rol: String?
        get() = getString(field_rol)
        set(arg) { put(field_rol, arg!!) }

    //Permisos
    var per_empleado:Boolean?
    get()=getBoolean(field_per_empleado)
    set(flag){put(field_per_empleado,flag!!)}

    var per_formulario:Boolean?
        get()=getBoolean(field_per_formulario)
        set(flag){put(field_per_formulario,flag!!)}

    var per_actividad:Boolean?
        get()=getBoolean(field_per_actividad)
        set(flag){put(field_per_actividad,flag!!)}

    var per_equipamiento:Boolean?
        get()=getBoolean(field_per_equipamiento)
        set(flag){put(field_per_equipamiento,flag!!)}




}
