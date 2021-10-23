package com.ello.kotlinseguridad.editar

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ello.kotlinseguridad.bin.BIN

import com.ello.kotlinseguridad.bin.CRUD
import com.ello.kotlinseguridad.Estado
import com.ello.kotlinseguridad.bin.Snippetk
import com.ello.kotlinseguridad.parseobj.Incidente
import com.ello.kotlinseguridad.parseobj.Rol
import com.google.android.material.textfield.TextInputEditText
import com.parse.ParseException
import com.parse.ParseQuery
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EInciVM() : ViewModel() {








    init {

    }





    fun CamposEstanMal(t1: TextInputEditText,t2: TextInputEditText):Boolean {

        if (t1.text.toString().isNullOrEmpty()) {return true}
        if (t2.text.toString().isNullOrEmpty()) {return true}

        return  false
    }

    fun EnviarIncidencia(text: String, text1: String, listBitm: ArrayList<Bitmap>, fg: () -> Unit, fb: () -> Unit) {

        val incid=Incidente()
        incid.name=text
        incid.desc=text1
        var C=listBitm.size
        GlobalScope.launch(Dispatchers.IO) {


            if (listBitm.size>0){val f0= Snippetk.BitmapToParseFile(listBitm[0]);f0.saveInBackground { e: ParseException? ->       if (e==null){C--;incid.e0=f0;if (C==0){Continuar(incid,fg, fb)}}
                if (listBitm.size>1){val f1= Snippetk.BitmapToParseFile(listBitm[1]);f1.saveInBackground { e: ParseException? ->     if (e==null){C--;incid.e1=f1;if (C==0){Continuar(incid,fg, fb)}}
                    if (listBitm.size>2){val f2= Snippetk.BitmapToParseFile(listBitm[2]);f2.saveInBackground { e: ParseException? ->   if (e==null){C--;incid.e2=f2;if (C==0){Continuar(incid,fg, fb)}}
                        if (listBitm.size>3){val f3= Snippetk.BitmapToParseFile(listBitm[3]);f3.saveInBackground { e: ParseException? ->
                            if (e == null) { C--;incid.e3 = f3;if (C == 0) { Continuar(incid, fg, fb) } } else { Log.e("error", "33w3") }}}}}}}}}


        }


    }

    private fun Continuar(incid: Incidente, fg: () -> Unit, fb: () -> Unit) {
        CRUD.CrearIncidente(incid,fg,fb)
    }


}
