package com.ello.kotlinseguridad.BIN

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.ello.kotlinseguridad.ParseObj.Actividad
import com.ello.kotlinseguridad.ParseObj.Equip
import com.ello.kotlinseguridad.ParseObj.Rol
import com.ello.kotlinseguridad.ParseObj.Usuario
import com.ello.kotlinseguridad.R
import com.ello.twelveseconds.Formulario
import com.parse.ParseException
import com.parse.ParseGeoPoint
import com.parse.ParseObject
import com.parse.ParseQuery
import java.util.*


class BIN {


companion object {



    fun SALVAR_USUARIO_LOGED(u: Usuario) {
        u.pin(PIN_USUARIO_LOGUEADO)



        CRUD.CargarTodasRol({
            val r = u.rol
            var flag_pinned = false

            for (i in it) {
                if (i.nombre_rol == r) {
                    ParseObject.unpinAll(PIN_ROL_LOGUEADO)
                    i.pin(PIN_ROL_LOGUEADO)
                    flag_pinned = true
                    Log.e("ROL_PINED ", "${i.nombre_rol}")
                    break
                }
            }
            if (!flag_pinned) {
                val rol = Rol()
                rol.nombre_rol = EMPTY_ROL
                rol.per_actividad = false
                rol.per_empleado = false
                rol.per_formulario = false
                rol.per_equipamiento = false
                rol.pin(PIN_ROL_LOGUEADO)
                Log.e("ROL_PINED ", "${rol.nombre_rol}")
            }
        }, {})

    }




    fun CARGAR_USUARIO_LOGED(): Usuario? {

        val query = ParseQuery.getQuery<Usuario>(Usuario.class_name)
        query.fromPin(PIN_USUARIO_LOGUEADO)
        try {
            return query.first
        } catch (e: ParseException) {
            return null
        }
    }




    fun BORRAR_USUARIO_LOGED() {
        ParseObject.unpinAll(PIN_USUARIO_LOGUEADO)
        ParseObject.unpinAll(PIN_TODAS_MIS_ACT)
        ParseObject.unpinAll(PIN_ROL_LOGUEADO)
    }

    /** Todos - Respondidos = NoRespondidos
     *
     */
    fun RESTAR_LISTAS(listado: List<Formulario>, respondidos: List<Formulario>): List<Formulario> {

        val ret = mutableListOf<Formulario>()

        for (i in listado) {
            if (!respondidos.contains(i)) {
                ret.add(i.fetchIfNeeded())
            }
        }
        return ret
    }
    fun RESTAR_LISTAS2(listado: List<Actividad>, respondidos: List<Actividad>): List<Actividad> {

        val ret = mutableListOf<Actividad>()

        for (i in listado) {
            if (!respondidos.contains(i)) {
                ret.add(i.fetchIfNeeded())
            }
        }
        return ret
    }



    val TIPO_FORMULARIO0: String="Tipo de formulario"
    val TIPO_FORMULARIO1: String="Análisis de riesgo"
    val TIPO_FORMULARIO2: String="Trabajo en las alturas"
    val TIPO_FORMULARIO3: String="Permiso de trabajo"
    val TIPO_FORMULARIO4: String="Notificación de riesgo"






    val EXTRA_TIENE_ACTIVIDAD_ASOCIADA: String="extra_solo_admin"
    val EMPTY_ROL: String = "Asignar Rol"
    val EMPTY_SPINNER_ANEXAR_FORMULARIO: String = "Anexar formulario"
    val EMPTY_TIPO_FORMULARIO= "Ninguno"


    val STR_ENVIADO: String = "LLENADO"
    val STR_POR_LLENAR: String = "POR LLENAR"
    val STR_EXPIRADO: String = "EXPIRADO"

    val REQUEST_MY_PERMISSIONS_LOCATION = 22
    val REQUEST_MY_PERMISSIONS_READ = 23
    val REQ_LLENAR_FORMULARIO = 10
    val REQ_VER_RESPUESTAS_FORMULARIO = 13

    val REQ_EDITAR_EQUIP = 400
    val REQ_EDITAR_ROL = 401

    val PIN_ROL_SELECTED = "editar_rol"
    val PIN_EQU_SELECTED = "editar_equ"
    val PIN_USU_SELECTED = "editar_usu"
    val PIN_EMP_SELECTED = "editar_emp"
    val PIN_ACT_SELECTED = "editar_act"
    val PIN_FOR_SELECTED = "editar_for"
    val PIN_USUARIO_LOGUEADO = "usuario_logueado"
    val PIN_ROL_LOGUEADO = "rol_logueado"


    val PIN_TODAS_ACT = "PIN_TODAS_ACT"
    val PIN_TODAS_USU = "PIN_TODAS_USU"
    val PIN_TODAS_EQU = "PIN_TODAS_EQU"
    val PIN_TODAS_FOR = "PIN_TODAS_FOR"
    val PIN_TODAS_ROL = "PIN_TODAS_ROL"
    val PIN_TODAS_PRE = "PIN_TODAS_PRE"
    val PIN_TODAS_RES = "PIN_TODAS_RES"
    val PIN_TODAS_MIS_ACT = "PIN_TODAS_MIS_ACT"
    val PIN_TODAS_MIS_FOR_RESP = "PIN_TODAS_MIS_FOR_RESP"

    val PIN_TODAS_MIS_ACT_RESP = "PIN_TODAS_MIS_ACT_RESP"
    val PIN_TODAS_MIS_FOR_SIN_RESP = "PIN_TODAS_MIS_FOR_SIN_RESP"
    val PIN_TODAS_MIS_ACT_SIN_RESP = "PIN_TODAS_MIS_FOR_SIN_RESP"
    val PIN_SUFIJO_ACT_RELATION = "SUF_ACT_REL"
    val PIN_SUFIJO_RESUELTOS = "PIN_SUFIJO_RESUELTOS"





    val EXTRA_SHOW_BOTON_RESPONDER = "show_boton"
    val EXTRA_NOMBRE = "name"
    val EXTRA_ID_ACTIVIDAD="id_actividad"
    val EXTRA_ID = "id"
    val EXTRA_USUARIO = "us"


    val SHAREDS="SHARED_ESTA_RESUELTO_PARA_UN_USUARIO"





    val COMPRESS_PERCENT = 54
    val REQUEST_SELECT_IMAGE = 200
    val REQUEST_TAKE_PHOTO = 201


    fun ES_ADMIN(): Boolean {



        val query = ParseQuery.getQuery<Usuario>(Usuario.class_name)
        query.fromPin(PIN_USUARIO_LOGUEADO)
        try {
            return query.first.adm
        } catch (e: ParseException) {
            return false
        }


    }

    fun GET_EMPTY_BITMAP(): Bitmap {
        val w: Int = 2
        val h: Int = 2
        val conf = Bitmap.Config.ARGB_8888 // see other conf types
        return Bitmap.createBitmap(w, h, conf) // this creates a MUTABLE bitmap
    }


    fun TengoPermisoLocalizacion(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun PedirLocationAppPermission(mAct: Activity?) {
        ActivityCompat.requestPermissions(
                mAct!!,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_MY_PERMISSIONS_LOCATION
        )
    }


    fun TengoPermisoREAD(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun PedirREADPermission(mAct: Activity?) {
        ActivityCompat.requestPermissions(
                mAct!!,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_MY_PERMISSIONS_READ
        )
    }


    fun getMyParseGeoLocation(context: Context, done_Callback: (g: ParseGeoPoint) -> Unit) {


        //NO PIDO PERMISO DE LOCALIZACION AQUI PORQUE LA ACTIVIDAD QUE LO LLAMA YA LO PIDIO



        SingleShotLocationProvider.requestSingleUpdate(
                context,
                SingleShotLocationProvider.LocationCallback { location ->


                    if (location.latitude > 90.0 || location.latitude < -90.0) {
                        return@LocationCallback
                    }
                    if (location.longitude > 180.0 || location.longitude < -180.0) {
                        return@LocationCallback
                    }
                    Log.e("set LAT", location.latitude.toString())
                    Log.e("set LON", location.longitude.toString())
                    val geoP = ParseGeoPoint()
                    geoP.longitude = location.longitude.toDouble()
                    geoP.latitude = location.latitude.toDouble()
                    done_Callback(geoP)
                })



    }


    fun FechaEsCorrecta(fechaLimite: Long): Boolean {
        val now = Calendar.getInstance().timeInMillis
        if ((now + 120000L) < fechaLimite) {
            return true
        }
        return false
    }

    fun PinSelected(p: ParseObject) {
        if (p is Rol) {
            ParseObject.unpinAll(PIN_ROL_SELECTED);p.pin(PIN_ROL_SELECTED);
        }
        if (p is Usuario) {
            ParseObject.unpinAll(PIN_USU_SELECTED);p.pin(PIN_USU_SELECTED);
        }
        if (p is Formulario) {
            ParseObject.unpinAll(PIN_FOR_SELECTED);p.pin(PIN_FOR_SELECTED);
        }
        if (p is Actividad) {
            ParseObject.unpinAll(PIN_ACT_SELECTED);p.pin(PIN_ACT_SELECTED);
        }
        if (p is Equip) {
            ParseObject.unpinAll(PIN_EQU_SELECTED);p.pin(PIN_EQU_SELECTED);
        }
    }
       fun getThisRol(): Rol {
           val query = ParseQuery.getQuery<Rol>(Rol.class_name)
         return  query.fromPin(PIN_ROL_SELECTED).first
       }

    fun getThisAct(): Actividad? {
        Log.e("getThisAct","BIN")
        val query = ParseQuery.getQuery<Actividad>(Actividad.class_name)
        return  query.fromPin(PIN_ACT_SELECTED).first
    }
    fun getThisForm(): Formulario? {
        Log.e("getThisForm","BIN")
        val query = ParseQuery.getQuery<Formulario>(Formulario.class_name)
        return  query.fromPin(PIN_FOR_SELECTED).first
    }
    fun getThisUser(): Usuario? {
        Log.e("getThisUser","BIN")
        val query = ParseQuery.getQuery<Usuario>(Usuario.class_name)
        return  query.fromPin(PIN_USU_SELECTED).first
    }
    fun getThisEquip(): Equip? {
        Log.e("getThisEquip","BIN")
        val query = ParseQuery.getQuery<Equip>(Equip.class_name)
        return  query.fromPin(PIN_EQU_SELECTED).first
    }















    fun PUEDE_PEDIR_LISTO():Boolean {

        Log.e("PUEDE_PEDIR_LISTO", "CALLED")
        val q = ParseQuery.getQuery<Rol>(Rol.class_name).fromPin(PIN_ROL_LOGUEADO)
        try { q.first;return true }
        catch (e: ParseException){return false}
    }


    fun PUEDE_ACTIVIDADES():Boolean {


        val q = ParseQuery.getQuery<Rol>(Rol.class_name).fromPin(PIN_ROL_LOGUEADO)
        try {
            if (q.first.per_actividad!!){return true}
        }catch (e: ParseException){}
        return false
    }

    fun PUEDE_FORMULARIOS():Boolean {

        val q = ParseQuery.getQuery<Rol>(Rol.class_name).fromPin(PIN_ROL_LOGUEADO)
        try {
            if (q.first.per_formulario!!){return true}
        }catch (e: ParseException){return false}
    return false
    }

    fun PUEDE_EMPLEADOS():Boolean {

        val q = ParseQuery.getQuery<Rol>(Rol.class_name).fromPin(PIN_ROL_LOGUEADO)
        try {
            if (q.first.per_empleado!!)
            {return true}
        }catch (e: ParseException){}
        return false
    }

    fun PUEDE_EQUIPAMIENTOS():Boolean {

        val q = ParseQuery.getQuery<Rol>(Rol.class_name).fromPin(PIN_ROL_LOGUEADO)
        try {
            if (q.first.per_equipamiento!!){return true}
        }catch (e: ParseException){}
        return false
    }

    fun ESTA_RESUELTO_LA_ACT_PARA_ESTE_USUARIO(c: Context, FueResuelto: () -> Unit) {
        val pref: SharedPreferences = c.getSharedPreferences(SHAREDS, Context.MODE_PRIVATE)
        val esteUsuario=CARGAR_USUARIO_LOGED()!!
        val estaActividad=getThisAct()!!

        val SHARED=esteUsuario.objectId+estaActividad.objectId


        if (!pref.getBoolean(SHARED, false)){
            Log.e("SIN SHARED RESUELTO","Obtenido mediante Shareds Preferences $SHARED")
            CRUD.CargarTodosActYFormRespondidos(esteUsuario, {

                for (f in it) {
                    if (f.objectId == estaActividad.objectId) {
                        GUARDAR_SHARED_RESUELTO(c,SHARED)
                        FueResuelto()
                    }
                }


            }, {}, {})
        }else{FueResuelto();Log.e("Resuelto","Obtenido mediante Shareds Preferences $SHARED")}

    }

    private fun GUARDAR_SHARED_RESUELTO(c: Context, SHARED: String) {

        val editor: SharedPreferences.Editor = c.applicationContext.getSharedPreferences(SHAREDS, Context.MODE_PRIVATE).edit()
        editor.putBoolean(SHARED, true)
        editor.commit()

    }

    fun TengoInternet(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo.isConnected
    }
    fun TengoInternet(context: Context,mostrarToast:Boolean): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val b= connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo.isConnected

        if (!b){ Toast.makeText(context, R.string.sin_conexion_intern,Toast.LENGTH_SHORT).show() }
        return b

    }
}

}