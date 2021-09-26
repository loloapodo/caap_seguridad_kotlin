package com.ello.kotlinseguridad.editar

import android.content.Context
import android.graphics.Bitmap
import android.widget.Spinner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ello.kotlinseguridad.bin.BIN

import com.ello.kotlinseguridad.bin.CRUD
import com.ello.kotlinseguridad.Estado
import com.google.android.material.textfield.TextInputEditText
import com.parse.ParseFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import java.util.regex.Pattern
import kotlin.collections.ArrayList

class EUsuarioVM(var cxt: Context) : ViewModel() {

    var estado = MutableLiveData<Estado>()
    var _roles= MutableLiveData<List<String>>()

    var nombre :String=""
    var apellido :String=""
    var usuario:String=""
    var contrasena:String=""
    var rol:String=""
    var cedula:String=""
    var direccion:String=""
    var telefono:String=""
    var parseFileFoto:ParseFile? = null


    init {
        estado.value= Estado.Idle
        _roles.value= ArrayList();
    }



     fun CrearUsuario(str_usuario: String, str_contrasena: String, str_nom_apell: String, str_apell: String, str_cedula: String, str_direcc: String, str_telef: String, adm:Boolean, rol: String, foto: Bitmap?, fg: () -> Unit, fb: () -> Unit)
    {
        viewModelScope.launch(Dispatchers.IO){ CRUD.CrearUsuario(str_usuario.toLowerCase(Locale.ROOT), str_contrasena, str_nom_apell, str_apell, str_cedula, str_direcc, str_telef,adm, rol,foto, fg, fb)}
    }

     fun EditarUsuario(str_ObjectId: String, str_usuario: String, str_contrasena: String, str_nom_apell: String, str_apell: String, str_cedula: String, str_direcc: String, str_telef: String,adm:Boolean,rol:String, foto: Bitmap?, fg: () -> Unit, fb: () -> Unit)
    {
        viewModelScope.launch { withContext(Dispatchers.IO){ CRUD.EditarUsuario(str_ObjectId, str_usuario.toLowerCase(Locale.ROOT), str_contrasena, str_nom_apell, str_apell, str_cedula, str_direcc, str_telef,adm,rol, foto, fg, fb) } }



    }

    fun CamposEstanMal(t1: TextInputEditText, t2: TextInputEditText, t3: TextInputEditText, t4: TextInputEditText, t5Cedula: TextInputEditText, t51Direcc: TextInputEditText, t52Telef: TextInputEditText, t6Spinner: Spinner):Boolean {

        if (t1.text.toString().isNullOrEmpty()||t2.text.toString().isNullOrEmpty()||t3.text.toString().isNullOrEmpty()||
                t3.text.toString().isNullOrEmpty()||t5Cedula.text.toString().isNullOrEmpty()||t51Direcc.text.toString().isNullOrEmpty()||t52Telef.text.toString().isNullOrEmpty()
        )
        {return true}
        if (!t5Cedula.text.toString().all {it.isDigit()}){return true}

        if (!isValidMobile(t52Telef.text.toString())){return true}
        if (t6Spinner.selectedItem.toString().contains(BIN.EMPTY_ROL)){return true}
        if (t6Spinner.selectedItem.toString().contains("Asignar")){return true}


        return  false
    }

    fun CargarTodosRoles() {
        viewModelScope.launch(Dispatchers.IO) {
            CRUD.CargarTodasRol({

                val temp = ArrayList<String>()
                temp.add(BIN.EMPTY_ROL)
                for (i in it) {
                    temp.add(i.nombre_rol!!)
                }
                _roles.value = temp

            }, {})


        }
    }

    fun CargarDatosUsuario() {

        viewModelScope.launch(Dispatchers.Main){

            estado.value=Estado.Network
            CRUD.CargarUnUsuPIN(BIN.PIN_USU_SELECTED, {

                estado.value = Estado.Network
                nombre = it.nom_apell!!
                apellido = it.apell!!
                usuario = it.usuario!!
                contrasena = it.contrasena!!
                rol = it.rol!!
                cedula = it.cedula!!
                telefono=it.telefono!!
                direccion=it.direccion!!
                parseFileFoto = it.foto
                estado.value = Estado.Idle

            }, { estado.value = Estado.Idle })
    }


    }

    private fun isValidMobile(phone: String): Boolean {
        return if (!Pattern.matches("[a-zA-Z]+", phone)) {
            phone.length in 2..13
        } else false
    }

}
