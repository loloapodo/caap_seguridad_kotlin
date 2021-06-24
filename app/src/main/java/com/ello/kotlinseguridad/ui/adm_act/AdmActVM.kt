package com.ello.kotlinseguridad.ui.adm_act

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ello.kotlinseguridad.BIN.CRUD
import com.ello.kotlinseguridad.ParseObj.Actividad
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AdmActVM : ViewModel() {


    private val _text = MutableLiveData<String>().apply {
        value = "This is slideshow Fragment"
    }
    val _listado = MutableLiveData<List<Actividad>>()
    val text: LiveData<String> = _text

    init {

    }

    fun Cargar()
    {
        viewModelScope.launch(Dispatchers.Main) {
            CRUD.CargarTodasActividadesLocal({ _listado.value = it; CargarDelServidor()}, {CargarDelServidor()})}
    }

    fun CargarDelServidor() {
        viewModelScope.launch(Dispatchers.IO) {
        CRUD.CargarTodasActividades({_listado.value=it},{})
        }
    }


}