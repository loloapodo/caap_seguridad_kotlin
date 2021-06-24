package com.ello.kotlinseguridad.ParseObj

import android.util.Log
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
        var field_ref_formulario = "ref_formulario"

        var field_sitio = "sitio"
        var field_ubicacion = "ubicacion"

        var field_rel_usuarios = "rel_usuarios"
    }


    var nombre: String?
        get() = getString(field_nombre)
        set(arg) { put(field_nombre, arg!!) }

    var sitio: String?
        get() = getString(field_sitio)
        set(arg) { put(field_sitio, arg!!) }

    var ubicacion: String?
        get() = getString(field_ubicacion)
        set(arg) { put(field_ubicacion, arg!!) }

    var desc: String?
        get() = getString(field_desc)
        set(arg) { put(field_desc, arg!!) }

    var rel_usuarios: ParseRelation<Usuario>?
        get() = getRelation<Usuario>(field_rel_usuarios)
        set(arg) { put(field_rel_usuarios, arg!!)}


    var ref_formulario: ParseObject?
        get() = getParseObject(field_ref_formulario)
        set(arg) { put(field_ref_formulario, arg!!) }


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
        if (lo==null){ return "";}

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = lo
        val mYear = calendar[Calendar.YEAR]
        val mMonth = calendar[Calendar.MONTH]+1
        val mDay = calendar[Calendar.DAY_OF_MONTH]
        return "$mDay/$mMonth/$mYear"
    }




}