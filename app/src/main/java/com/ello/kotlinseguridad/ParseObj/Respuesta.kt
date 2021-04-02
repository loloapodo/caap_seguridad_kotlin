package com.ello.kotlinseguridad.ParseObj

import android.provider.MediaStore
import android.util.Log
import com.parse.ParseClassName
import com.parse.ParseFile
import com.parse.ParseGeoPoint
import com.parse.ParseObject
import java.io.*

@ParseClassName("tabla_respuesta")
class Respuesta : ParseObject() {



    companion object {
        var class_name = "tabla_respuesta"
        var field_created = "createdAt"
        var field_updated = "updatedAt"
        var field_respuesta = "respuesta"
        var field_foto = "foto"
        var field_checked="checked"
		var field_ubicacion = "ubicacion"
		var field_fecha = "fecha"
        var field_ref_formulario = "ref_formulario"
        var field_ref_pregunta = "ref_pregunta"
        var field_ref_usuario = "ref_usuario"



    }





    var respuesta: String?
        get() = getString(field_respuesta)
        set(arg) { put(field_respuesta, arg!!) }

    var checked: Boolean
        get() {
            val b:Boolean?=getBoolean(field_checked)
            if (b==null){return false}
            return b
        }
        set(arg) { put(field_checked, arg) }


    var foto: ParseFile?
        get() = getParseFile(field_foto)
        set(arg) { put(field_foto, arg!!) }


var ubicacion: ParseGeoPoint?
        get() = getParseGeoPoint(field_ubicacion)
        set(arg) { put(field_ubicacion, arg!!) }


var fecha: Long?
        get() = getLong(field_fecha)
        set(arg) { put(field_fecha, arg!!) }


    
	var ref_usuario: ParseObject?
        get() = getParseObject(field_ref_usuario)
        set(arg) { put(field_ref_usuario, arg!!) }


	var ref_pregunta: ParseObject?
        get() = getParseObject(field_ref_pregunta)
        set(arg) { put(field_ref_pregunta, arg!!) }

    var ref_formulario: ParseObject?
        get() = getParseObject(field_ref_formulario)
        set(arg) { put(field_ref_formulario, arg!!) }







}
