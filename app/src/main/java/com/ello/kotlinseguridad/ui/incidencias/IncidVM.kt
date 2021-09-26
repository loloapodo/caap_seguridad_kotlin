package com.ello.kotlinseguridad.ui.incidencias

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ello.kotlinseguridad.bin.CRUD
import com.ello.kotlinseguridad.parseobj.Incidente
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class IncidVM : ViewModel() {


    private val _text = MutableLiveData<String>().apply {
        value = "This is slideshow Fragment"
    }
    val _listado = MutableLiveData<List<Incidente>>()
    val text: LiveData<String> = _text

    init {

    }

    fun Cargar()
    {
        viewModelScope.launch(Dispatchers.Main) {
            CRUD.CargarTodasIncidentesLocal({ _listado.value = it; CargarDelServidor()}, {CargarDelServidor()})}
    }

    fun CargarDelServidor() {

        viewModelScope.launch(Dispatchers.IO) {
        CRUD.CargarTodasIncidentes({_listado.value=it},{})
        }
    }


}