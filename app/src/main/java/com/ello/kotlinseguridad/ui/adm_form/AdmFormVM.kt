package com.ello.kotlinseguridad.ui.adm_form

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ello.kotlinseguridad.BIN.CRUD
import com.ello.twelveseconds.Formulario
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AdmFormVM : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is gallery Fragment"
    }
    val text: LiveData<String> = _text
    val _listado = MutableLiveData<List<Formulario>>()


    init {
        viewModelScope.launch(Dispatchers.Main) {
            CRUD.CargarTodosFormulariosLocal({ _listado.value = it;Log.e("CargarTodosForm done", "ss"); }, {})
        }
    }

    fun Cargar()
        {
        viewModelScope.launch(Dispatchers.IO) {
            CRUD.CargarTodosFormularios({_listado.value=it;Log.e("CargarTodosForm done","ss")},{})
        }

    }

    fun CargarDelServidor() {
        viewModelScope.launch(Dispatchers.IO) {
            CRUD.CargarTodosFormularios({_listado.value=it;Log.e("CargarTodosForm done","ss")},{})
        }
    }


}