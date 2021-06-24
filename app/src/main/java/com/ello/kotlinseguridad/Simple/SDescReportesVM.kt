package com.ello.kotlinseguridad.Simple

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ello.kotlinseguridad.ParseObj.Actividad
import com.ello.kotlinseguridad.ParseObj.Usuario
import com.ello.kotlinseguridad.BIN.CRUD
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SDescReportesVM() : ViewModel() {

    lateinit var id:String

    fun Cargar(fg:() -> Unit, fb:() -> Unit) {

    }


}