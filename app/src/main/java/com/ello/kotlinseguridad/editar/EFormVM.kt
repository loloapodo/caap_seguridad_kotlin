package com.ello.kotlinseguridad.editar

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ello.kotlinseguridad.bin.BIN

import com.ello.kotlinseguridad.bin.CRUD
import com.ello.kotlinseguridad.Estado
import com.ello.kotlinseguridad.R
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
    var hour: Int = 23
    var minutes: Int = 59

    var estado = MutableLiveData<Estado>()

    init {
        estado.value= Estado.Idle
    }



    fun CrearFormulario(str_nombre: String,str_Tipo:String, str_fecha: Long,listPreg: ArrayList<String> ,fvalidate: (str:String) -> Unit,fg: () -> Unit,fb: () -> Unit) {
        if (!EstaListoParaCrear()){fvalidate(cxt.resources.getString(R.string.complete_fechas));return}
        val fecha_limite= getFechaLimite()
        if (!BIN.FechaEsCorrecta(fecha_limite)){fvalidate(cxt.resources.getString(R.string.fecha_imposible));return}
        viewModelScope.launch(Dispatchers.IO){ CRUD.CrearFormulario(str_nombre,str_Tipo,str_fecha,fecha_limite,listPreg,fg,fb)}
    }

    fun EditarFormulario(str_ObjectId: String,str_nombre: String,str_Tipo:String, long_fecha: Long,listPreg: ArrayList<String> ,fvalidate: (str:String) -> Unit,fg: () -> Unit,fb: () -> Unit) {
        if (!EstaListoParaCrear()){fvalidate(cxt.resources.getString(R.string.complete_fechas));return}
        val fecha_limite= getFechaLimite()
        if (!BIN.FechaEsCorrecta(fecha_limite)){fvalidate(cxt.resources.getString(R.string.fecha_imposible));return}
        viewModelScope.launch(Dispatchers.IO) { CRUD.EditarFormulario(str_ObjectId,str_nombre,str_Tipo,long_fecha,fecha_limite,listPreg,fg,fb)}
    }



    private fun getFechaLimite(): Long {

       val c= Calendar.getInstance()
        c.set(Calendar.HOUR_OF_DAY,hour)
        c.set(Calendar.MINUTE,minutes)

        c.set(Calendar.YEAR,year!!)
        c.set(Calendar.MONTH,month!!)
        c.set(Calendar.DAY_OF_MONTH,day!!)

        return c.time.time

    }




    fun EstaListoParaCrear():Boolean
    {

        if (year==null||month==null||day==null)
        {return false}
        return true
    }

    fun CamposEstanMal(t1: TextInputEditText): Boolean {

            if (t1.text.toString().isNullOrEmpty())
            {return true}
            return  false

    }


}






