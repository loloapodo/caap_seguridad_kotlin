package com.ello.kotlinseguridad.Editar

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.ello.kotlinseguridad.ParseObj.Usuario
import com.ello.kotlinseguridad.CRUD
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class EActivVM(var cxt: Context) : ViewModel() {

lateinit var id_actividad: String

    var day: Int? = null
    var month: Int? = null
    var year: Int? = null



     fun CrearActividad(usuarios:List<Usuario> , str_nombre: String,str_desc: String,long_fecha:Long ,fg: () -> Unit,fb: () -> Unit) {
         val Fecha=getFechaDeActividad()
         viewModelScope.launch{withContext(Dispatchers.IO){CRUD.CrearActividad(usuarios,str_nombre,str_desc,Fecha,fg,fb) }}
    }


     fun EditarActividad(str_ObjectId: String,usuarios:List<Usuario> ,str_nombre: String,str_desc: String,long_fecha:Long ,fg: () -> Unit,fb: () -> Unit){
         val Fecha=getFechaDeActividad()
        viewModelScope.launch { withContext(Dispatchers.IO){CRUD.EditarActividad(str_ObjectId,usuarios,str_nombre,str_desc,Fecha,fg,fb)}  }
    }


    fun CargarTodosUsuariosLocal(fg: (list:List<Usuario>) -> Unit,fb: () -> Unit) {
        viewModelScope.launch { withContext(Dispatchers.IO){CRUD.CargarTodosUsuarioLocal(fg,fb)} }
    }

    /*
     fun BorrarActividad(str_ObjectId: String,fg: () -> Unit,fb: () -> Unit){
        viewModelScope.launch { withContext(Dispatchers.IO){Vmodel.BorrarActividad(str_ObjectId,fg,fb)} }
    }
*/

    fun EstaListoParaCrear():Boolean
    {

        return day!=null
    }
    private fun getFechaDeActividad(): Long {

        val c= Calendar.getInstance()
        c.set(Calendar.HOUR_OF_DAY,23)
        c.set(Calendar.MINUTE,59)

        c.set(Calendar.YEAR,year!!)
        c.set(Calendar.MONTH,month!!)
        c.set(Calendar.DAY_OF_MONTH,day!!)

        return c.time.time

    }

    fun CamposEstanMal(checkedList: ArrayList<Usuario>, t1: TextInputEditText, t2: TextInputEditText): Boolean {


        if (t1.text.toString().isNullOrEmpty()||t2.text.toString().isNullOrEmpty()) {return true}
        if (checkedList.isEmpty()){return true}

        return false
    }


}
