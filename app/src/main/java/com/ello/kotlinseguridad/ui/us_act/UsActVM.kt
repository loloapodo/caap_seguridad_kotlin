package com.ello.kotlinseguridad.ui.us_act

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ello.kotlinseguridad.BIN.BIN
import com.ello.kotlinseguridad.BIN.CRUD
import com.ello.kotlinseguridad.BIN.Snippetk
import com.ello.kotlinseguridad.ParseObj.Actividad
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class UsActVM : ViewModel() {


    private val _text = MutableLiveData<String>().apply {
        value = "This is slideshow Fragment"
    }
    val _listado = MutableLiveData<List<Actividad>>()
    val text: LiveData<String> = _text

    fun Cargar()
    {

        val usuario=BIN.CARGAR_USUARIO_LOGED()
        if (usuario==null){Log.e("Error","3329");return}


        val c=Calendar.getInstance()
        c.set(Calendar.HOUR_OF_DAY,0)
        c.set(Calendar.MINUTE,0)
        val starting_date=c.timeInMillis
        Log.e("Cargar","Actividades del Usuario + ${usuario.nom_apell}+ ${starting_date.toString()}+ ${Snippetk.LeerFechaR(starting_date)}")


        viewModelScope.launch(Dispatchers.Main) {
            CRUD.CargarTodasActividadesDelUsuarioLocal(usuario,starting_date,{_listado.value=it},{})
            delay(4000)
            withContext(Dispatchers.IO) { CRUD.CargarTodasActividadesDelUsuario(usuario, starting_date, { _listado.value = it }, {}) }
        }

    }






}