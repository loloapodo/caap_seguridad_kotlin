package com.ello.kotlinseguridad.BIN

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.ello.kotlinseguridad.ParseObj.Usuario
import com.ello.twelveseconds.Formulario
import com.parse.ParseException
import com.parse.ParseGeoPoint
import com.parse.ParseObject
import com.parse.ParseQuery
import java.util.*


class BIN {


companion object{
    fun SALVAR_USUARIO_LOGED(u:Usuario) {
        u.pin(PIN_USUARIO_LOGUEADO)
    }

    fun CARGAR_USUARIO_LOGED(): Usuario? {

        val query = ParseQuery.getQuery<Usuario>(Usuario.class_name)
        query.fromPin(PIN_USUARIO_LOGUEADO)
        try {
            return query.first
        }catch (e:ParseException) {
            return null
        }
    }

    fun BORRAR_USUARIO_LOGED() {
        ParseObject.unpinAll(PIN_USUARIO_LOGUEADO)
    }

    /** Todos - Respondidos = NoRespondidos
     *
     */
    fun RESTAR_LISTAS(listado: List<Formulario>, respondidos: List<Formulario>): List<Formulario> {

        val ret= mutableListOf<Formulario>()

        for (i in listado){
            if (!respondidos.contains(i)){ret.add(i.fetchIfNeeded())}
        }
        return ret


    }



    val STR_ENVIADO: String="LLENADO"
    val STR_POR_LLENAR: String="POR LLENAR"
    val STR_EXPIRADO: String="EXPIRADO"

    val REQUEST_MY_PERMISSIONS_LOCATION=22
    val REQ_LLENAR_FORMULARIO=10


    val PIN_FORMULARIO_RESPONDIDOS="formulario_a_respondido"
     val PIN_FORMULARIO_SIN_RESPONDER="formulario_a_responder"
    val PIN_ACTIVIDADES_USUARIO= "actividades_usuario"


    val EXTRA_SHOW_BOTON_RESPONDER="show_boton"
    val EXTRA_NOMBRE="name"
    val EXTRA_ID="id"

    val PIN_USUARIO_LOGUEADO="usuario_logueado"

    val COMPRESS_PERCENT =54
   val REQUEST_SELECT_IMAGE=200
   val REQUEST_TAKE_PHOTO=201


    fun ES_ADMIN(): Boolean {

        var u:Usuario

        val query = ParseQuery.getQuery<Usuario>(Usuario.class_name)
        query.fromPin(PIN_USUARIO_LOGUEADO)
        try {
            return query.first.adm
        }catch (e:ParseException) {
            return false
        }


    }

    fun EMPTY_BITMAP():Bitmap {
        val w: Int = 2
        val h: Int = 2
        val conf = Bitmap.Config.ARGB_8888 // see other conf types
        return  Bitmap.createBitmap(w, h, conf) // this creates a MUTABLE bitmap
    }


    fun TengoPermisoLocalizacion(context: Context): Boolean {return  ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED }
    fun PedirLocationAppPermission(mAct: Activity?) { ActivityCompat.requestPermissions(mAct!!, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_MY_PERMISSIONS_LOCATION) }

    fun getMyParseGeoLocation(context: Context,done_Callback: (g:ParseGeoPoint) -> Unit) {


        //NO PIDO PERMISO DE LOCALIZACION AQUI PORQUE LA ACTIVIDAD QUE LO LLAMA YA LO PIDIO
            SingleShotLocationProvider.requestSingleUpdate(context, SingleShotLocationProvider.LocationCallback { location ->
                if (location.latitude > 90.0 || location.latitude < -90.0) {
                    return@LocationCallback
                }
                if (location.longitude > 180.0 || location.longitude < -180.0) {
                    return@LocationCallback
                }
                Log.e("set LAT", location.latitude.toString())
                Log.e("set LON", location.longitude.toString())
                val geoP=ParseGeoPoint()
                geoP.longitude= location.longitude.toDouble()
                geoP.latitude=location.latitude.toDouble()
                done_Callback(geoP)
            })


    }






    fun FechaEsCorrecta(fechaLimite: Long): Boolean {
        val now= Calendar.getInstance().timeInMillis
        if ((now+120000L)<fechaLimite){
            return true
        }
        return false
    }




}


}