package com.ello.kotlinseguridad.simple

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ello.kotlinseguridad.bin.BIN
import com.ello.kotlinseguridad.bin.CRUD
import com.ello.kotlinseguridad.parseobj.Rol
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SRolVM() : ViewModel() {

    lateinit var id_rol:String

    suspend fun BorrarRol(str_ObjectId: String, fg: () -> Unit, fb: () -> Unit) {

        viewModelScope.launch { withContext(Dispatchers.IO) { CRUD.BorrarRol(str_ObjectId,fg,fb) }}

    }



    fun CargarElRol(fg: (activ: Rol) -> Unit, fb:() -> Unit) {
        CRUD.CargarUnRolPIN(BIN.PIN_ROL_SELECTED,fg,fb)
    }


}