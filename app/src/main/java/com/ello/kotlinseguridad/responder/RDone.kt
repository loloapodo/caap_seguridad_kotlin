package com.ello.kotlinseguridad.responder

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ello.kotlinseguridad.Adapter.RespondidoAdapter
import com.ello.kotlinseguridad.Adapter.RespuestaAdapter
import com.ello.kotlinseguridad.BIN.BIN
import com.ello.kotlinseguridad.BIN.Snippetk
import com.ello.kotlinseguridad.BIN.Snippets
import com.ello.kotlinseguridad.Estado
import com.ello.kotlinseguridad.ParseObj.Pregunta
import com.ello.kotlinseguridad.R
import com.ello.kotlinseguridad.databinding.ActRFormBinding
import com.ello.kotlinseguridad.databinding.ActivityRDoneBinding
import java.io.IOException


class RDone : AppCompatActivity() {


    private var mPosition: Int = 0
    private lateinit var mOneImageAdapt: ImageView

    private lateinit var mBind: ActivityRDoneBinding
    private lateinit var vm: RDoneVM
    private lateinit var mAdapter: RespondidoAdapter
    private lateinit var mRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Init()
        InitRecycler(mBind.root)




        vm.Cargar()
        mBind.unaFormularioNombre.text = intent.getStringExtra(BIN.EXTRA_NOMBRE)




        vm._listRES.observe(this, Observer {
            if (it.isNotEmpty()) { mAdapter.SetPregResp(vm._listPRE.value!!,vm._listRES.value!!) }
            mAdapter.notifyDataSetChanged()
        })

        vm.usuar.observe(this, Observer {
            mBind.unaFormularioNombre.text = "${intent.getStringExtra(BIN.EXTRA_NOMBRE)} (${it.nom_apell})"

        })





    }











    fun LoadFoto(i:ImageView,p:Int) {

    }










    private fun Init() {
        mBind = ActivityRDoneBinding.inflate(layoutInflater)
        mBind.included.toolbar.title = resources.getString(R.string.titleformulario)
        setContentView(mBind.root)
        vm = RDoneVM()


        vm.id=intent.getStringExtra(BIN.EXTRA_ID)!!
        vm.usuarId=intent.getStringExtra(BIN.EXTRA_USUARIO)!!




    }

    private fun InitRecycler(root: View) {

        mRecyclerView = root.findViewById(R.id.recycler)
        val llm = LinearLayoutManager(root.context);
        llm.orientation = LinearLayoutManager.VERTICAL;
        mRecyclerView.layoutManager = llm;
        mAdapter = RespondidoAdapter(root.context) { foto, pos -> /*TODO Agrandar foto al hacer click*/ }
        mRecyclerView.adapter = mAdapter;
    }

    private fun getThis(): Context { return this }
    fun AtrasClick(view: View) {finish()}

}