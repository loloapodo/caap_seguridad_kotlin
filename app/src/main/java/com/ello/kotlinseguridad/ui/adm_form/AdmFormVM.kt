package com.ello.kotlinseguridad.ui.adm_form

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ello.kotlinseguridad.BuildConfig
import com.ello.kotlinseguridad.bin.CRUD
import com.ello.twelveseconds.Formulario
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AdmFormVM : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is gallery Fragment"
    }
    val text: LiveData<String> = _text
    val _listado = MutableLiveData<List<Formulario>>()


    init {

    }

    fun Cargar()
        {
            viewModelScope.launch(Dispatchers.Main) {
                CRUD.CargarTodasFormulariosLocal({ _listado.value = it;CargarDelServidor()}, {CargarDelServidor()})
            }
    }

    fun CargarDelServidor() {

        viewModelScope.launch(Dispatchers.IO) {
            CRUD.CargarTodasFormularios({_listado.value=it;},{})
            CRUD.CargarTodasPreguntas({},{})
        }
    }


}