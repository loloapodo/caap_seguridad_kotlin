package com.ello.kotlinseguridad.ui.adm_equip

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ello.kotlinseguridad.BuildConfig
import com.ello.kotlinseguridad.bin.CRUD
import com.ello.kotlinseguridad.parseobj.Equip
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AdmEquipVM : ViewModel() {


    private val _text = MutableLiveData<String>().apply {
        value = "This is slideshow Fragment"
    }
    val _listado = MutableLiveData<List<Equip>>()
    val text: LiveData<String> = _text

    init {

    }

    fun Cargar()
    {
        viewModelScope.launch(Dispatchers.Main) {
            CRUD.CargarTodasEquipamientoLocal({ _listado.value = it;CargarDelServidor()}, {CargarDelServidor()})
        }
    }

    fun CargarDelServidor() {

        viewModelScope.launch(Dispatchers.IO) {
        CRUD.CargarTodasEquipamiento({_listado.value=it},{})
        }
    }


}