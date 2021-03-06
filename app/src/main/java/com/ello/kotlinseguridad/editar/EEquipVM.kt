package com.ello.kotlinseguridad.editar

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ello.kotlinseguridad.bin.BIN

import com.ello.kotlinseguridad.bin.CRUD
import com.ello.kotlinseguridad.Estado
import com.google.android.material.textfield.TextInputEditText
import com.parse.ParseFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EEquipVM() : ViewModel() {

    var estado = MutableLiveData<Estado>()


    var nombre :String=""
    var uso :String=""
    var descrip :String=""
    var parsef_image:ParseFile? = null


    init {
        estado.value= Estado.Idle
    }



     fun CrearEquipamiento(str_nombre: String,str_uso: String,str_desc: String, foto: Bitmap?, fg: () -> Unit, fb: () -> Unit)
    {
         viewModelScope.launch (Dispatchers.IO){CRUD.CrearEquipamiento(str_nombre,str_uso,str_desc,foto,fg,fb)  }

    }

     fun EditarEquipamiento(str_ObjectId: String,str_usuario: String,str_uso: String,str_desc: String, foto: Bitmap?,fg: () -> Unit,fb: () -> Unit)
    {
        viewModelScope.launch { withContext(Dispatchers.IO){ CRUD.EditarEquipamiento(str_ObjectId,str_usuario,str_uso,str_desc,foto,fg,fb) } }
    }

    fun CamposEstanMal(t1: TextInputEditText, t2: TextInputEditText, t3: TextInputEditText):Boolean {

        if (t1.text.toString().isEmpty()) {return true}
        if (t2.text.toString().isEmpty()) {return true}
        if (t3.text.toString().isEmpty()) {return true}

        return  false
    }

    fun CargarObjeto() {
        estado.value=Estado.SearchLocat


        CRUD.CargarUnEquiPIN(BIN.PIN_EQU_SELECTED,
            {
            nombre=it.nombre!!
            uso=it.uso!!
            descrip=it.descripcion!!
            parsef_image=it.foto
            estado.value=Estado.Idle
            }
            ,
            {
                estado.value=Estado.Idle;Log.e("EROLVM","Cargar Equip Pinned to edit pinname:${BIN.PIN_EQU_SELECTED}" )});
        }

}
