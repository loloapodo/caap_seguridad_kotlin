package com.ello.kotlinseguridad.ui.AdmActividades

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ello.kotlinseguridad.CRUD
import com.ello.kotlinseguridad.MDebug
import com.ello.kotlinseguridad.ParseObj.Actividad
import com.ello.twelveseconds.Formulario
import com.parse.FindCallback
import com.parse.ParseObject
import com.parse.ParseQuery
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AdmActividadesVM : ViewModel() {


    private val _text = MutableLiveData<String>().apply {
        value = "This is slideshow Fragment"
    }
    val _listado = MutableLiveData<List<Actividad>>()
    val text: LiveData<String> = _text

    fun Cargar()
    {

        viewModelScope.launch(Dispatchers.IO) {
            CRUD.CargarTodasActividadesLocal({_listado.value=it},{})
            delay(4000)
            CRUD.CargarTodasActividades({_listado.value=it},{})
        }

    }






}