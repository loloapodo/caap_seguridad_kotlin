package com.ello.kotlinseguridad.editar

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ello.kotlinseguridad.adapter.CheckAdapter
import com.ello.kotlinseguridad.bin.BIN
import com.ello.kotlinseguridad.R
import com.ello.kotlinseguridad.databinding.ActivityEActBinding
import com.ello.kotlinseguridad.drawer1
import com.parse.ParseGeoPoint
import java.util.*
import kotlin.collections.ArrayList


class EInci : AppCompatActivity() {



    var mObjId_toEdit:String? = ""

    companion object{
        var EXTRA_OBJ_ID="extra_editar"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)









    }

    private fun Init() {
    }







    fun AcpetarClick(view: View) {

    }


    fun getThis(): Context {return this}

    private fun InitRecycler() {

    }

    fun MostrarDatePicker(view: View) {

    }

    fun MostrarTimePicker(view: View) {


    }





    fun CancelarClick(view: View) {finish()}


}