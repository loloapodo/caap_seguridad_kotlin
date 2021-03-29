package com.ello.kotlinseguridad.ui.AdmFormulario

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ello.kotlinseguridad.CRUD
import com.ello.twelveseconds.Formulario
import com.parse.FindCallback
import com.parse.ParseObject
import com.parse.ParseQuery
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AdmFormularioVM : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is gallery Fragment"
    }
    val text: LiveData<String> = _text
    val _listado = MutableLiveData<List<Formulario>>()


    fun Cargar()
        {
        viewModelScope.launch(Dispatchers.IO) {
            CRUD.CargarTodosFormulariosLocal({_listado.value=it;Log.e("CargarTodosForm done","ss");},{})
            delay(4000)
            CRUD.CargarTodosFormularios({_listado.value=it;Log.e("CargarTodosForm done","ss")},{})
        }

    }








}