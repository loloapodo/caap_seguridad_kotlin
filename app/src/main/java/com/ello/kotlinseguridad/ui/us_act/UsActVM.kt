package com.ello.kotlinseguridad.ui.us_act

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ello.kotlinseguridad.BuildConfig
import com.ello.kotlinseguridad.bin.BIN
import com.ello.kotlinseguridad.bin.CRUD
import com.ello.kotlinseguridad.bin.Snippetk
import com.ello.kotlinseguridad.parseobj.Actividad
import com.ello.kotlinseguridad.parseobj.Usuario
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
            CRUD.CargarTodasActividadesDelUsuarioLocal(usuario,starting_date,{_listado.value=it;CargarServidor(usuario,starting_date)},{CargarServidor(usuario,starting_date)})
        }

    }


    fun CargarServidor(u:Usuario,l:Long){

        viewModelScope.launch(Dispatchers.IO){CRUD.CargarTodasActividadesDelUsuario(u, l, { _listado.value = it }, {})  }
    }





}