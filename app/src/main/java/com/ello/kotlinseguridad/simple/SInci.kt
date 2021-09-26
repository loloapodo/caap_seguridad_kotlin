package com.ello.kotlinseguridad.simple

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ello.kotlinseguridad.*
import com.ello.kotlinseguridad.adapter.EvidenciaAdapter
import com.ello.kotlinseguridad.adapter.VerUsDeActAdapter
import com.ello.kotlinseguridad.bin.BIN
import com.ello.kotlinseguridad.bin.Snippetk
import com.ello.kotlinseguridad.databinding.ActivitySActBinding
import com.ello.kotlinseguridad.databinding.ActivitySIncBinding
import com.ello.kotlinseguridad.databinding.IIncidenteBinding
import com.ello.kotlinseguridad.parseobj.Actividad
import com.ello.twelveseconds.Formulario
import com.parse.ParseQuery


import kotlinx.coroutines.launch

class SInci : AppCompatActivity() {

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: EvidenciaAdapter
    var vm: SInciVM = SInciVM()
    private lateinit var mBind:ActivitySIncBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        Init()
        InitRecycler()

        //CreateMyOptionMenu()

        vm._incidente.observe(this, Observer { o ->
            BIN.PinSelected(o)
            if (o.name!=null){mBind.unaIncidenteName.text=o.name}
            if(o.desc!=null){mBind.unaIncidenteDescripci.text=o.desc}

            if (o.e0!=null){mAdapter.addImages(o.e0!!,false)}
            if (o.e1!=null){mAdapter.addImages(o.e1!!,false)}
            if (o.e2!=null){mAdapter.addImages(o.e2!!,false)}
            if (o.e3!=null){mAdapter.addImages(o.e3!!,false)}
            if (mAdapter.itemCount>0){mAdapter.notifyDataSetChanged()}


        })

        vm.CargarLaIncidente()

    }







    private fun Init() {

        mBind= ActivitySIncBinding.inflate(layoutInflater)
        mBind.included.toolbar.title = resources.getString(R.string.titleinci)
        setContentView(mBind.root)
        vm.id_incidente=intent.getStringExtra(BIN.EXTRA_ID)!!



    }

    private fun InitRecycler() {
        mRecyclerView=mBind.recycler
        val llm = LinearLayoutManager(this);
        llm.orientation = LinearLayoutManager.VERTICAL;
        mRecyclerView.layoutManager = llm;
        mAdapter= EvidenciaAdapter(this,{i,pos->}) ;
        mRecyclerView.adapter=mAdapter;
    }

    private fun getThis(): Context { return this; }
    fun CancelarClick(view: View) {finish()}



}




