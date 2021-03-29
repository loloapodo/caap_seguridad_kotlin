package com.ello.kotlinseguridad.Editar

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.ello.kotlinseguridad.CRUD
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class EFormVM(var cxt: Context) : ViewModel() {

lateinit var id_formulario: String

    var day: Int? = null
    var month: Int? = null
    var year: Int? = null
    var hour: Int? = null
    var minutes: Int? = null




    fun CrearFormulario(str_nombre: String, str_fecha: Long,str_fecha_limite:Long,listPreg: ArrayList<String> ,fg: () -> Unit,fb: () -> Unit) {
        val Fecha_Limite= getFechaLimite()
        viewModelScope.launch(Dispatchers.IO){CRUD.CrearFormulario(str_nombre,str_fecha,Fecha_Limite,listPreg,fg,fb)}
    }

    fun EditarFormulario(str_ObjectId: String,str_nombre: String, long_fecha: Long,long_fecha_limite:Long,listPreg: ArrayList<String> ,fg: () -> Unit,fb: () -> Unit) {
        val Fecha_Limite= getFechaLimite()
        viewModelScope.launch(Dispatchers.IO) {CRUD.EditarFormulario(str_ObjectId,str_nombre,long_fecha,Fecha_Limite,listPreg,fg,fb)}
    }

    private fun getFechaLimite(): Long {

       val c= Calendar.getInstance()
        c.set(Calendar.HOUR_OF_DAY,hour!!)
        c.set(Calendar.MINUTE,minutes!!)

        c.set(Calendar.YEAR,year!!)
        c.set(Calendar.MONTH,month!!)
        c.set(Calendar.DAY_OF_MONTH,day!!)

        return c.time.time

    }




    fun EstaListoParaCrear():Boolean
    {

        if (day!=null&&minutes!=null)
        {return true}
        return false
    }

    fun CamposEstanMal(t1: TextInputEditText): Boolean {

            if (t1.text.toString().isNullOrEmpty())
            {return true}
            return  false

    }


}






