package com.ello.kotlinseguridad.Editar

import android.content.Context
import android.graphics.Bitmap
import android.widget.Spinner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ello.kotlinseguridad.BIN.BIN

import com.ello.kotlinseguridad.BIN.CRUD
import com.ello.kotlinseguridad.Estado
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.collections.ArrayList

class EUsuarioVM(var cxt: Context) : ViewModel() {

    var estado = MutableLiveData<Estado>()
    var _roles= MutableLiveData<List<String>>()

    var nombre :String=""
    var apellido :String=""
    var usuario:String=""
    var contrasena:String=""
    var rol:String=""
    var _cedula= MutableLiveData<String>()





    init {
        estado.value= Estado.Idle
        _roles.value= ArrayList();
    }



     fun CrearUsuario(str_usuario: String, str_contrasena: String, str_nom_apell: String,str_cedula: String, foto: Bitmap?, fg: () -> Unit, fb: () -> Unit)
    {
        viewModelScope.launch(Dispatchers.IO){ CRUD.CrearUsuario(str_usuario.toLowerCase(Locale.ROOT),str_contrasena,str_nom_apell,str_cedula,foto,fg,fb)}
    }

     fun EditarUsuario(str_ObjectId: String,str_usuario: String, str_contrasena: String, str_nom_apell: String, str_cedula: String, foto: Bitmap?,fg: () -> Unit,fb: () -> Unit)
    {
        viewModelScope.launch { withContext(Dispatchers.IO){ CRUD.EditarUsuario(str_ObjectId,str_usuario.toLowerCase(Locale.ROOT),str_contrasena,str_nom_apell,str_cedula,foto,fg,fb) } }



    }

    fun CamposEstanMal(t1: TextInputEditText, t2: TextInputEditText, t3: TextInputEditText,t4: TextInputEditText, t5Cedula: TextInputEditText,t5Spinner:Spinner):Boolean {

        if (t1.text.toString().isNullOrEmpty()||t2.text.toString().isNullOrEmpty()||t3.text.toString().isNullOrEmpty()||t3.text.toString().isNullOrEmpty()||t5Cedula.text.toString().isNullOrEmpty())
        {return true}
        if (!t5Cedula.text.toString().all {it.isDigit()}){return true}
        //TODO t5roles verificar!!!

        return  false
    }

    fun CargarTodosRoles() {
        viewModelScope.launch(Dispatchers.IO) {
            CRUD.CargarTodosRoles({

                val temp= ArrayList<String>()
                temp.add(BIN.EMPTY_ROL)
                for (i in it) { temp.add(i.nombre_rol!!) }
                _roles.value=temp

            },{})


        }
    }

    fun CargarDatosUsuario(id: String) {
    viewModelScope.launch (Dispatchers.IO){
        CRUD.CargarUnUsuario(id,{

            nombre=it.nom_apell!!
            usuario=it.usuario!!
            contrasena=it.contrasena!!
            rol=it.rol!!
                //TODO CARGAR LA FOTO TAMBIEN

            _cedula.value=it.cedula
        },{})
    }


    }


}
