package com.ello.kotlinseguridad.BIN

import android.content.Context
import com.ello.kotlinseguridad.ParseObj.Actividad


import com.parse.ParseException
import com.parse.ParseQuery
import java.util.*

object Consultas {

    val localQuery: ParseQuery<Actividad>
        get() {
            val q = ParseQuery.getQuery<Actividad>(Actividad.class_name)


            q.fromLocalDatastore()
            return q
        }

    @JvmStatic
    fun getServerQuery(ctx: Context?): ParseQuery<Actividad> {
        val q = ParseQuery.getQuery<Actividad>(Actividad.class_name)

        return q
    }

    @JvmStatic
    fun getPinned(id: String): Actividad? {
        val q = ParseQuery.getQuery<Actividad>(Actividad.class_name)
        q.fromLocalDatastore()
        try {
            return q[id]
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return null
    }



    @JvmStatic
    fun getAllPinned(pinname: String?): ParseQuery<Actividad> {
        val q = ParseQuery.getQuery<Actividad>(Actividad.class_name)
        q.fromPin(pinname)
        return q
    }

    @JvmStatic
    fun getAllLocal(): ParseQuery<Actividad> {
        return ParseQuery.getQuery<Actividad>(Actividad.class_name)

    }












}