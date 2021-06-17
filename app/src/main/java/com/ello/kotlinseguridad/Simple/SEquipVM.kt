package com.ello.kotlinseguridad.Simple

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ello.kotlinseguridad.BIN.BIN
import com.ello.kotlinseguridad.BIN.CRUD
import com.ello.kotlinseguridad.ParseObj.Equip
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SEquipVM() : ViewModel() {

    lateinit var id_equip:String

    suspend fun BorrarEquipamiento(str_ObjectId: String,fg: () -> Unit,fb: () -> Unit){

        viewModelScope.launch { withContext(Dispatchers.IO) { CRUD.BorrarEquipamiento(str_ObjectId,fg,fb) }}

    }



    fun CargarElEquipamiento(fg:(activ:Equip) -> Unit, fb:() -> Unit) {
        CRUD.CargarUnEquiPIN(BIN.PIN_EQU_SELECTED,fg,fb)
    }


}