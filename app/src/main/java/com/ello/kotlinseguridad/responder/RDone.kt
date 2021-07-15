package com.ello.kotlinseguridad.responder

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
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
import com.ceylonlabs.imageviewpopup.ImagePopup
import com.ello.kotlinseguridad.Adapter.EquipaCheckAdapter
import com.ello.kotlinseguridad.Adapter.EvidenciaAdapter
import com.ello.kotlinseguridad.Adapter.RespondidoAdapter
import com.ello.kotlinseguridad.Adapter.RespuestaAdapter
import com.ello.kotlinseguridad.BIN.BIN
import com.ello.kotlinseguridad.BIN.Snippetk
import com.ello.kotlinseguridad.BIN.Snippets
import com.ello.kotlinseguridad.Estado
import com.ello.kotlinseguridad.ParseObj.Pregunta
import com.ello.kotlinseguridad.R

import com.ello.kotlinseguridad.databinding.ActivityRDoneBinding
import java.io.IOException


class RDone : AppCompatActivity() {


    private var mPosition: Int = 0
    private lateinit var mOneImageAdapt: ImageView

    private lateinit var mBind: ActivityRDoneBinding
    private lateinit var vm: RDoneVM
    private lateinit var mAdapter: RespondidoAdapter
    private lateinit var mRecyclerView: RecyclerView

    private lateinit var mAdapterEvidencia: EvidenciaAdapter
    private lateinit var mAdapterEquip: EquipaCheckAdapter
    private lateinit var mRecyclerViewEvidencias: RecyclerView
    private lateinit var mRecyclerViewEquipacheq: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Init()
        InitRecycler(mBind.root)
        Titulo()


        vm.Cargar()








        vm._listRES.observe(this, Observer {respuestas->
            if (respuestas.isNotEmpty()) {

                mAdapter.SetPregResp(vm._listPRE.value!!,vm._listRES.value!!)
                mBind.rdoneProgressbar1.visibility=View.GONE
                mAdapter.notifyDataSetChanged()

        for (primera in respuestas){
                if(primera.firs_of_list){



                with(primera){


                val cant_imag=contarImagenes();
                    mAdapterEvidencia.clearImages()
                    Log.e("cant de imagenes","$cant_imag")

                for (i in 0 until cant_imag){ mAdapterEvidencia.addImages(getEvidencia(i)!!,false) }
                if (cant_imag==0){mBind.layoutEvidencias.visibility=View.GONE}

                if (equipos!=null){mAdapterEquip.addrawEq(equipos!!)}
}
        }
        }
            }
            else{  Log.e("sin respuestas","sin respuestas")}




            mBind.rdoneProgressbar2.visibility=View.GONE
            mAdapterEvidencia.notifyDataSetChanged()
            mAdapterEquip.notifyDataSetChanged()
        })
    }

    private fun Titulo() {
        val u=BIN.getThisUser()
        val f= BIN.getThisForm()
        var str=f!!.tipo
            str+=": "
            str+= f.nombre
        str+=" ("
        str+=u!!.nom_apell
        str+=")"



        mBind.unaFormularioNombre.text=str

    }


    fun LoadFoto(i:ImageView,p:Int) {

    }










    private fun Init() {
        mBind = ActivityRDoneBinding.inflate(layoutInflater)


        mBind.included.toolbar.title = BIN.getThisAct()!!.nombre
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

        mRecyclerViewEvidencias = root.findViewById(R.id.recycler_evidencias)
        val llm2 = LinearLayoutManager(root.context);
        //val gl = GridLayoutManager(getThis(), 2)
        llm2.orientation = LinearLayoutManager.HORIZONTAL;

        mRecyclerViewEvidencias.layoutManager = llm2;
        mAdapterEvidencia = EvidenciaAdapter(root.context) { imageView, pos->

            val imagePopup: ImagePopup
            imagePopup= ImagePopup(imageView.getContext());
            imagePopup.setBackgroundColor(Color.TRANSPARENT);  // Optional
            imagePopup.setFullScreen(true); // Optional
            //imagePopup.setHideCloseIcon(true);  // Optional
            imagePopup.setImageOnClickClose(true);  // Optional
            imagePopup.initiatePopup(imageView.getDrawable());
            imagePopup.viewPopup();


        }
        mRecyclerViewEvidencias.adapter = mAdapterEvidencia;

        mRecyclerViewEquipacheq = root.findViewById(R.id.recyclercheckequip)
        val llm3 = LinearLayoutManager(root.context);
        llm3.orientation = LinearLayoutManager.HORIZONTAL;
        mRecyclerViewEquipacheq.layoutManager =llm3// GridLayoutManager(root.context, 2)
        mAdapterEquip = EquipaCheckAdapter(root.context,true) { foto, pos-> }
        mRecyclerViewEquipacheq.adapter = mAdapterEquip;

    }

    private fun getThis(): Context { return this }
    fun AtrasClick(view: View) {finish()}

}