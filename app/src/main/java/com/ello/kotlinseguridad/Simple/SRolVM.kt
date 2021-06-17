package com.ello.kotlinseguridad.Simple

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ello.kotlinseguridad.BIN.BIN
import com.ello.kotlinseguridad.BIN.CRUD
import com.ello.kotlinseguridad.ParseObj.Equip
import com.ello.kotlinseguridad.ParseObj.Rol
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