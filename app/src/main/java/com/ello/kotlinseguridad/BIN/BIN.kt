package com.ello.kotlinseguridad.BIN

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.ello.kotlinseguridad.ParseObj.Actividad
import com.ello.kotlinseguridad.ParseObj.Equip
import com.ello.kotlinseguridad.ParseObj.Rol
import com.ello.kotlinseguridad.ParseObj.Usuario
import com.ello.kotlinseguridad.R
import com.ello.twelveseconds.Formulario
import com.google.android.material.navigation.NavigationView
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
            val r= u.rol
            var flag_pinned=false

            for (i in it) {
                if (i.nombre_rol==r) {
                    i.pin(PIN_ROL_LOGUEADO)
                    flag_pinned=true
                    Log.e("ROL_PINED ","${i.nombre_rol}")
                    break
                }
            }
            if (!flag_pinned){
                val rol=Rol()
                rol.nombre_rol=EMPTY_ROL
                rol.per_actividad=false
                rol.per_empleado=false
                rol.per_formulario=false
                rol.per_equipamiento=false
                rol.pin(PIN_ROL_LOGUEADO)
                Log.e("ROL_PINED ","${rol.nombre_rol}")
            }
        },{})

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


    val EXTRA_SOLO_ADMIN: String="extra_solo_admin"
    val EMPTY_ROL: String = "Asignar Rol"
    val STR_ENVIADO: String = "LLENADO"
    val STR_POR_LLENAR: String = "POR LLENAR"
    val STR_EXPIRADO: String = "EXPIRADO"

    val REQUEST_MY_PERMISSIONS_LOCATION = 22
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


    val PIN_FORMULARIO_RESPONDIDOS = "formulario_a_respondido"
    val PIN_FORMULARIO_SIN_RESPONDER = "formulario_a_responder"
    val PIN_ACTIVIDADES_USUARIO = "actividades_usuario"


    val EXTRA_SHOW_BOTON_RESPONDER = "show_boton"
    val EXTRA_NOMBRE = "name"
    val EXTRA_ID = "id"
    val EXTRA_USUARIO = "us"

    val PIN_USUARIO_LOGUEADO = "usuario_logueado"
    val PIN_ROL_LOGUEADO = "rol_logueado"


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

    fun EMPTY_BITMAP(): Bitmap {
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



    fun PUEDE_PEDIR_LISTO():Boolean {

        Log.e("PUEDE_PEDIR_LISTO","CALLED")
        val q = ParseQuery.getQuery<Rol>(Rol.class_name).fromPin(PIN_ROL_LOGUEADO)
        try { q.first;return true }
        catch (e:ParseException){return false}
    }


    fun PUEDE_ACTIVIDADES(fg: (b: Boolean) -> Unit) {


        val q = ParseQuery.getQuery<Rol>(Rol.class_name).fromPin(PIN_ROL_LOGUEADO)
        try {
            if (q.first.per_actividad == true){fg(true)}
        }catch (e:ParseException){}
    }

    fun PUEDE_FORMULARIOS(fg: (b: Boolean) -> Unit) {

        val q = ParseQuery.getQuery<Rol>(Rol.class_name).fromPin(PIN_ROL_LOGUEADO)
        try {
            if (q.first.per_formulario == true){fg(true)}
        }catch (e:ParseException){}
    }

    fun PUEDE_EMPLEADOS(fg: (b: Boolean) -> Unit) {

        val q = ParseQuery.getQuery<Rol>(Rol.class_name).fromPin(PIN_ROL_LOGUEADO)
        try {
            if (q.first.per_empleado == true)
            {fg(true)}
        }catch (e:ParseException){}
    }

    fun PUEDE_EQUIPAMIENTOS(fg: (b: Boolean) -> Unit) {

        val q = ParseQuery.getQuery<Rol>(Rol.class_name).fromPin(PIN_ROL_LOGUEADO)
        try {
            if (q.first.per_equipamiento == true){fg(true)}
        }catch (e:ParseException){}
    }

}

}