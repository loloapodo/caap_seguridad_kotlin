package com.ello.kotlinseguridad.Editar

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ello.kotlinseguridad.BIN.BIN

import com.ello.kotlinseguridad.ParseObj.Usuario
import com.ello.kotlinseguridad.BIN.CRUD
import com.ello.kotlinseguridad.Estado
import com.ello.kotlinseguridad.R
import com.ello.twelveseconds.Formulario
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.collections.ArrayList

class EActivVM(var cxt: Context) : ViewModel() {

    lateinit var id_actividad: String

    var formularioList= MutableLiveData<List<Formulario>>()
    var ref_formulario_position=0
    var day: Int? = null
    var month: Int? = null
    var year: Int? = null
    var hour: Int? = null
    var minutes: Int? = null

    var estado = MutableLiveData<Estado>()

    init {
        estado.value= Estado.Idle
    }


     fun CrearActividad(usuarios:List<Usuario> , str_nombre: String,str_desc: String,str_sitio: String,str_ubicacion: String,str_tipo_formulario:String,long_fecha:Long ,fvalidate: (str:String) -> Unit,fg: () -> Unit,fb: () -> Unit) {

         if (str_nombre.isNullOrEmpty()||str_desc.isNullOrEmpty()||str_ubicacion.isNullOrEmpty()||str_sitio.isNullOrEmpty()||str_tipo_formulario== BIN.EMPTY_TIPO_FORMULARIO||day==null||minutes==null)
         {fvalidate(cxt.resources.getString(R.string.toast_campos_mal));return}
         val fecha=getFechaDeActividad()
         if (!FechaEsCorrectaParaAct(fecha)){fvalidate(cxt.resources.getString(R.string.fecha_imposible));return}
         viewModelScope.launch{withContext(Dispatchers.IO){ CRUD.CrearActividad(usuarios,str_nombre,str_desc,str_sitio,str_ubicacion, formularioList.value?.get(ref_formulario_position)!!,fecha,fg,fb) }}
    }


     fun EditarActividad(str_ObjectId: String,usuarios:List<Usuario> , str_nombre: String,str_desc: String,str_sitio: String,str_ubicacion: String,str_tipo_formulario:String,long_fecha:Long ,fvalidate: (str:String) -> Unit,fg: () -> Unit,fb: () -> Unit){
         if (str_nombre.isNullOrEmpty()||str_desc.isNullOrEmpty()||str_ubicacion.isNullOrEmpty()||str_sitio.isNullOrEmpty()||str_tipo_formulario== BIN.EMPTY_TIPO_FORMULARIO||day==null||minutes==null)
         {fvalidate(cxt.resources.getString(R.string.toast_campos_mal));return}
         val fecha=getFechaDeActividad()
         if (!FechaEsCorrectaParaAct(fecha)){fvalidate(cxt.resources.getString(R.string.fecha_imposible));return}
        viewModelScope.launch { withContext(Dispatchers.IO){ CRUD.EditarActividad(str_ObjectId,usuarios,str_nombre,str_desc,str_sitio,str_ubicacion, formularioList.value?.get(ref_formulario_position)!!,fecha,fg,fb)}  }
    }


    fun CargarTodosUsuariosLocal(fg: (list:List<Usuario>) -> Unit,fb: () -> Unit) {
        viewModelScope.launch { withContext(Dispatchers.IO){ CRUD.CargarTodosUsuarioLocal(false,fg,fb)} }
    }

    /*
     fun BorrarActividad(str_ObjectId: String,fg: () -> Unit,fb: () -> Unit){
        viewModelScope.launch { withContext(Dispatchers.IO){Vmodel.BorrarActividad(str_ObjectId,fg,fb)} }
    }
*/


    private fun getFechaDeActividad(): Long {

        val c= Calendar.getInstance()
        c.set(Calendar.HOUR_OF_DAY,hour!!)
        c.set(Calendar.MINUTE, minutes!!)

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

    /**
     * La actividad solo puedes ser programada para hoy o en el futuro.
     */
    fun FechaEsCorrectaParaAct(fecha: Long): Boolean {
        val now= Calendar.getInstance()
        now.set(Calendar.HOUR_OF_DAY,0)
        if (now.timeInMillis<fecha){
            return true
        }
        return false
    }

    fun CargarFormulariosdelSpinner() {
        //TODO Rectificar que no se descargue de internet todo el tiempo
        Log.e("CargarFormulario","CargarFormulariosdelSpinner")
        viewModelScope.launch { withContext(Dispatchers.IO){
                CRUD.CargarTodasFormularios({

                                            formularioList.value =it

                },{})




              }


    }
}

}
