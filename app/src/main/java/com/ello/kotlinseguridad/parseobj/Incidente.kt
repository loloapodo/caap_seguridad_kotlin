package com.ello.kotlinseguridad.parseobj

import com.parse.ParseClassName
import com.parse.ParseFile
import com.parse.ParseObject

@ParseClassName("tabla_incidentes")
class Incidente : ParseObject() {

    companion object {
        var class_name = "tabla_incidentes"
        var field_created = "createdAt"
        var field_updated = "updatedAt"

        //var field_foto = "foto"

        var field_name="name"
        var field_desc="desc"
        var field_e0 = "e0"
        var field_e1 = "e1"
        var field_e2 = "e2"
        var field_e3 = "e3"

    }
    var name: String?
        get() = getString(field_name)
        set(arg) { put(field_name, arg!!) }

    var desc: String?
        get() = getString(field_desc)
        set(arg) { put(field_desc, arg!!) }

    fun getEvidencia(i: Int): ParseFile? {

        if (i==0){return e0}
        if (i==1){return e1}
        if (i==2){return e2}
        if (i==3){return e3}
        else{return e3}
    }

    fun contarImagenes():Int{

        for (i in 0 until 4){ if (getEvidencia(i)==null){return i} }
        return 4
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




}