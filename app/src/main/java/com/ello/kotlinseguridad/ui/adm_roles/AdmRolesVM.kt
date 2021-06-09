package com.ello.kotlinseguridad.ui.adm_equip

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ello.kotlinseguridad.BIN.CRUD
import com.ello.kotlinseguridad.ParseObj.Actividad
import com.ello.kotlinseguridad.ParseObj.Equip
import com.ello.kotlinseguridad.ParseObj.Rol
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AdmRolesVM : ViewModel() {


    private val _text = MutableLiveData<String>().apply {
        value = "This is slideshow Fragment"
    }
    val _listado = MutableLiveData<List<Rol>>()
    val text: LiveData<String> = _text

    init {
        viewModelScope.launch(Dispatchers.Main) {
            CRUD.CargarTodasRolLocal({ _listado.value = it }, {})
        }
    }

    fun Cargar()
    {
        Log.e("Cargar Equip","Cargar Equip")


        viewModelScope.launch(Dispatchers.IO) {
            CRUD.CargarTodasRol({_listado.value=it;Log.e("CargarTodosEquipos done","ss")},{})
        }

    }

    fun CargarDelServidor() {
        viewModelScope.launch(Dispatchers.IO) {
        CRUD.CargarTodasRol({_listado.value=it},{})
        }
    }


}