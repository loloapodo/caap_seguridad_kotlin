package com.ello.kotlinseguridad.ui.adm_us

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ello.kotlinseguridad.BIN.CRUD
import com.ello.kotlinseguridad.ParseObj.Usuario
import kotlinx.coroutines.*

class AdmUsVM : ViewModel() {


    init {
        viewModelScope.launch (Dispatchers.Main) {
            CRUD.CargarTodosUsuarioLocal({ _listado.value = it;Log.e("local", "usuario"); }, {})
        }
    }

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text
    public val _listado = MutableLiveData<List<Usuario>>()

   fun Cargar()
   {
       viewModelScope.launch (Dispatchers.IO){
              CRUD.CargarTodosUsuario({_listado.value=it;Log.e("server","usuario");}, {})

       }

   }

    fun CargarDelServidor() {
        viewModelScope.launch (Dispatchers.IO) {
            CRUD.CargarTodosUsuario({ _listado.value = it;Log.e("server", "usuario"); }, {})
        }
    }


}