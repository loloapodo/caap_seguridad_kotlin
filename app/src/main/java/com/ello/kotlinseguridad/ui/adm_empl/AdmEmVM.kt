package com.ello.kotlinseguridad.ui.adm_us

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ello.kotlinseguridad.BuildConfig
import com.ello.kotlinseguridad.bin.CRUD
import com.ello.kotlinseguridad.parseobj.Usuario
import kotlinx.coroutines.*

class AdmEmVM : ViewModel() {


    init {

    }

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text
    public val _listado = MutableLiveData<List<Usuario>>()

   fun Cargar()
   {
       viewModelScope.launch (Dispatchers.Main) {
           CRUD.CargarTodosUsuarioLocal(false,{ _listado.value = it;CargarDelServidor()}, {CargarDelServidor()})
       }
   }

    fun CargarDelServidor() {

        viewModelScope.launch (Dispatchers.IO) {
            CRUD.CargarTodosUsuario(false,{ _listado.value = it;}, {})
        }
    }


}