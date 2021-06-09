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

class ERolVM() : ViewModel() {

    var estado = MutableLiveData<Estado>()


    var nombre :String=""





    init {
        estado.value= Estado.Idle
    }



     fun CrearRol(str_nombre: String, fg: () -> Unit, fb: () -> Unit)
    {
         viewModelScope.launch (Dispatchers.IO){CRUD.CrearRol(str_nombre,fg,fb)  }

    }

     fun EditarRol(str_ObjectId: String, str_usuario: String, fg: () -> Unit, fb: () -> Unit)
    {
        viewModelScope.launch { withContext(Dispatchers.IO){ CRUD.EditarRol(str_ObjectId,str_usuario,fg,fb) } }
    }

    fun CamposEstanMal(t1: TextInputEditText):Boolean {

        if (t1.text.toString().isNullOrEmpty()) {return true}

        return  false
    }










}
