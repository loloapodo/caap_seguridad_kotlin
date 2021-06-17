package com.ello.kotlinseguridad.Editar

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ello.kotlinseguridad.BIN.BIN

import com.ello.kotlinseguridad.BIN.CRUD
import com.ello.kotlinseguridad.Estado
import com.ello.kotlinseguridad.ParseObj.Rol
import com.google.android.material.textfield.TextInputEditText
import com.parse.ParseException
import com.parse.ParseQuery
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ERolVM() : ViewModel() {

    var estado = MutableLiveData<Estado>()


    var nombre :String=""
    var per_formulario :Boolean=false
    var per_empleado :Boolean=false
    var per_actividad :Boolean=false
    var per_equipamiento :Boolean=false





    init {
        estado.value= Estado.Idle
    }


//Actividades,Empleado,Equipamiento,Formularios
     fun CrearRol(str_nombre: String,p_act:Boolean,p_emp:Boolean,p_equ:Boolean,p_for:Boolean, fg: () -> Unit, fb: () -> Unit)
    {

         viewModelScope.launch (Dispatchers.IO){CRUD.CrearRol(str_nombre,p_act,p_emp,p_equ,p_for,fg,fb)  }

    }

     fun EditarRol(str_ObjectId: String, str_usuario: String,p_act:Boolean,p_emp:Boolean,p_equ:Boolean,p_for:Boolean, fg: () -> Unit, fb: () -> Unit)
    {
        viewModelScope.launch { withContext(Dispatchers.IO){ CRUD.EditarRol(str_ObjectId,str_usuario,p_act,p_emp,p_equ,p_for,fg,fb) } }
    }

    fun CamposEstanMal(t1: TextInputEditText):Boolean {

        if (t1.text.toString().isNullOrEmpty()) {return true}

        return  false
    }

    fun CargarObjeto() {

        estado.value=Estado.SearchLocat

        val query = ParseQuery.getQuery<Rol>(Rol.class_name)
        query.fromPin(BIN.PIN_ROL_SELECTED)
        try {
             val r=query.first
                nombre=r.nombre_rol!!
                per_empleado=r.per_empleado!!
                per_actividad=r.per_actividad!!
                per_equipamiento=r.per_equipamiento!!
                per_formulario=r.per_formulario!!
        }catch (e: ParseException) {
            Log.e("EROLVM","Cargar Rol Pinned to edit pinname:${BIN.PIN_ROL_SELECTED}" )
        }

        estado.value=Estado.Idle

    }


}
