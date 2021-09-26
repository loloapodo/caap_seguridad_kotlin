package com.ello.kotlinseguridad.ui.adm_equip

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ello.kotlinseguridad.BuildConfig
import com.ello.kotlinseguridad.bin.CRUD
import com.ello.kotlinseguridad.parseobj.Rol
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AdmRolesVM : ViewModel() {


    private val _text = MutableLiveData<String>().apply {
        value = "This is slideshow Fragment"
    }
    val _listado = MutableLiveData<List<Rol>>()
    val text: LiveData<String> = _text

    init {

    }

    fun Cargar()
    {
        viewModelScope.launch(Dispatchers.Main) {
            CRUD.CargarTodasRolLocal({ _listado.value = it;CargarDelServidor()}, {CargarDelServidor()})
        }
    }

    fun CargarDelServidor() {

        viewModelScope.launch(Dispatchers.IO) {
        CRUD.CargarTodasRol({_listado.value=it},{})
        }
    }


}