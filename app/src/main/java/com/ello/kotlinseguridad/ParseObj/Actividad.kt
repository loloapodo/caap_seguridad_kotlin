package com.ello.kotlinseguridad.ParseObj

import com.parse.ParseClassName
import com.parse.ParseObject
import com.parse.ParseRelation

import java.util.*


@ParseClassName("tabla_actividad")
class Actividad : ParseObject() {



    companion object {
        var class_name = "tabla_actividad"
        var field_created = "createdAt"
        var field_updated = "updatedAt"
        var field_nombre = "nombre"
        var field_desc = "desc"
        var field_fecha = "fecha"

        var field_rel_usuarios = "rel_usuarios"
    }


    var nombre: String?
        get() = getString(field_nombre)
        set(arg) { put(field_nombre, arg!!) }

    var desc: String?
        get() = getString(field_desc)
        set(arg) { put(field_desc, arg!!) }

    var rel_usuarios: ParseRelation<Usuario>?
        get() = getRelation<Usuario>(field_rel_usuarios)
        set(arg) { put(field_rel_usuarios, arg!!)}


    fun addUsuarios(usuarios:List<Usuario>) {

        for (u in usuarios){getRelation<Usuario>(field_rel_usuarios).add(u)}

    }




    var fecha: Long?
        get() = getNumber(field_fecha)?.toLong()
        set(arg) { put(field_fecha, arg!!) }

    fun GenerarFecha(){
        put(field_fecha, Calendar.getInstance().timeInMillis)
    }

    fun LeerFechaR():String{

        var lo= getNumber(field_fecha)?.toLong();
        if (lo==null){return "null"}

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = lo
        val mYear = calendar[Calendar.YEAR]
        val mMonth = calendar[Calendar.MONTH]
        val mDay = calendar[Calendar.DAY_OF_MONTH]
        return "$mDay/$mMonth/$mYear"
    }




}