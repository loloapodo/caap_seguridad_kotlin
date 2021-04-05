package com.ello.kotlinseguridad.Editar

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.ello.kotlinseguridad.BIN.CRUD
import com.ello.kotlinseguridad.Estado
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class EUsuarioVM(var cxt: Context) : ViewModel() {

    var estado = MutableLiveData<Estado>()

    init {
        estado.value= Estado.Idle
    }



     fun CrearUsuario(str_usuario: String, str_contrasena: String, str_nom_apell: String,str_cedula: String, foto: Bitmap?, fg: () -> Unit, fb: () -> Unit)
    {
        viewModelScope.launch(Dispatchers.IO){ CRUD.CrearUsuario(str_usuario.toLowerCase(Locale.ROOT),str_contrasena,str_nom_apell,str_cedula,foto,fg,fb)}
    }

     fun EditarUsuario(str_ObjectId: String,str_usuario: String, str_contrasena: String, str_nom_apell: String, str_cedula: String, foto: Bitmap?,fg: () -> Unit,fb: () -> Unit)
    {
        viewModelScope.launch { withContext(Dispatchers.IO){ CRUD.EditarUsuario(str_ObjectId,str_usuario.toLowerCase(Locale.ROOT),str_contrasena,str_nom_apell,str_cedula,foto,fg,fb) } }



    }

    fun CamposEstanMal(t1: TextInputEditText, t2: TextInputEditText, t3: TextInputEditText, t4Cedula: TextInputEditText):Boolean {

        if (t1.text.toString().isNullOrEmpty()||t2.text.toString().isNullOrEmpty()||t3.text.toString().isNullOrEmpty()||t4Cedula.text.toString().isNullOrEmpty())
        {return true}
        if (!t4Cedula.text.toString().all {it.isDigit()}){return true}


        return  false
    }


}
