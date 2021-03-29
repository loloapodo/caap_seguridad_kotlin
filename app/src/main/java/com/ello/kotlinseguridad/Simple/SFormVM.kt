package com.ello.kotlinseguridad.Simple

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ello.kotlinseguridad.ParseObj.Pregunta
import com.ello.kotlinseguridad.CRUD
import com.ello.twelveseconds.Formulario
import kotlinx.coroutines.launch


class SFormVM() : ViewModel() {

    lateinit var id_formulario:String




     fun CargarTodasPreguntasDelFormulario(formulario: Formulario, fg: (list:List<Pregunta>) -> Unit, fb: () -> Unit){

    viewModelScope.launch { CRUD.CargarTodasPreguntasDelFormulario(formulario,fg,fb) }
    }

     fun CargarElFormularioLocal(fg: (f:Formulario) -> Unit, fb: () -> Unit){
         viewModelScope.launch { CRUD.CargarUnFormularioLocal(id_formulario,fg,fb) }
     }



     fun BorrarFormulario(str_ObjectId: String,fg: () -> Unit,fb: () -> Unit) {
    viewModelScope.launch { CRUD.BorrarFormulario(str_ObjectId,fg,fb) }
}



}