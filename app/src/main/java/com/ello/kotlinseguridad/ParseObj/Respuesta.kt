package com.ello.kotlinseguridad.ParseObj

import com.parse.ParseClassName
import com.parse.ParseFile
import com.parse.ParseGeoPoint
import com.parse.ParseObject

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
        var field_ref_actividad = "ref_actividad"
        var field_ref_formulario = "ref_formulario"
        var field_ref_pregunta = "ref_pregunta"
        var field_ref_usuario = "ref_usuario"

        var field_first_of_list = "first_of_list"
        var field_equipos = "equipos"
        var field_e0 = "e0"
        var field_e1 = "e1"
        var field_e2 = "e2"
        var field_e3 = "e3"
        var field_e4 = "e4"
        var field_e5 = "e5"
        var field_e6 = "e6"
        var field_e7 = "e7"
        var field_e8 = "e8"




    }


    fun setEvidencia(i: Int, p: ParseFile) {

             if (i==0){e0=p}
        else if (i==1){e1=p}
        else if (i==2){e2=p}
        else if (i==3){e3=p}
        else if (i==4){e4=p}
        else if (i==5){e5=p}
        else if (i==6){e6=p}
        else if (i==7){e7=p}
        else if (i==8){e8=p}

    }
    fun getEvidencia(i: Int): ParseFile? {

        if (i==0){return e0}
        if (i==1){return e1}
        if (i==2){return e2}
        if (i==3){return e3}
        if (i==4){return e4}
        if (i==5){return e5}
        if (i==6){return e6}
        if (i==7){return e7}
        else{return e8}


    }


     fun contarImagenes():Int{

        for (i in 0 until 8){ if (getEvidencia(i)==null){return i} }
         return 9

     }

    var e0: ParseFile?
        get() = getParseFile(field_e0)
        set(arg) { put(field_e0, arg!!) }

    var e1: ParseFile?
        get() = getParseFile(field_e1)
        set(arg) { put(field_e1, arg!!) }

    var e2: ParseFile?
        get() = getParseFile(field_e2)
        set(arg) { put(field_e2, arg!!) }

    var e3: ParseFile?
        get() = getParseFile(field_e3)
        set(arg) { put(field_e3, arg!!) }

    var e4: ParseFile?
        get() = getParseFile(field_e4)
        set(arg) { put(field_e4, arg!!) }

    var e5: ParseFile?
        get() = getParseFile(field_e5)
        set(arg) { put(field_e5, arg!!) }

    var e6: ParseFile?
        get() = getParseFile(field_e6)
        set(arg) { put(field_e6, arg!!) }

    var e7: ParseFile?
        get() = getParseFile(field_e7)
        set(arg) { put(field_e7, arg!!) }

    var e8: ParseFile?
        get() = getParseFile(field_e8)
        set(arg) { put(field_e8, arg!!) }


    var firs_of_list: Boolean
        get() {
            val b:Boolean?=getBoolean(field_first_of_list)
            if (b==null){return false}
            return b
        }
        set(arg) { put(field_first_of_list, arg) }




    var equipos: String?
        get() = getString(field_equipos)
        set(arg) { put(field_equipos, arg!!) }


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

    var ref_actividad: ParseObject?
        get() = getParseObject(field_ref_actividad)
        set(arg) { put(field_ref_actividad, arg!!) }









}
