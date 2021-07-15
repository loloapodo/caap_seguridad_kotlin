package com.ello.kotlinseguridad.Simple

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ello.kotlinseguridad.*
import com.ello.kotlinseguridad.Adapter.VerUsDeActAdapter
import com.ello.kotlinseguridad.BIN.BIN
import com.ello.kotlinseguridad.BIN.CRUD
import com.ello.kotlinseguridad.BIN.Snippetk
import com.ello.kotlinseguridad.Editar.EActiv
import com.ello.kotlinseguridad.Editar.EUsuario
import com.ello.kotlinseguridad.databinding.ActivitySActBinding
import com.ello.twelveseconds.Formulario
import com.google.android.material.navigation.NavigationView


import kotlinx.coroutines.launch

class SActiv : AppCompatActivity() {

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: VerUsDeActAdapter
    private lateinit var mBind: ActivitySActBinding
    private  var vm: SActivVM = SActivVM()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        Init()
        InitRecycler()

        //CreateMyOptionMenu()


        vm._actividad.observe(this, Observer {o ->


            BIN.PinSelected(o)


            //mBind.included.toolbar.setTitleTextColor(Color.BLACK)
            //mBind.included.toolbar.setSubtitleTextColor(Color.BLACK)

            mBind.unaActividadName.text = o.nombre
            mBind.unaActividadFecha.text = o.LeerFechaR()
            mBind.unaActividadHora.text = Snippetk.LeerHoraR(o.fecha)
            mBind.unaActividadDescripci.text = o.desc
            mBind.unaActividadSitio.text = o.sitio
            mBind.unaActividadLugar.text = o.ubicacion
            val f = o.ref_formulario
            if (f != null) {
                val form = f.fetchIfNeeded<Formulario>()

                mBind.unaActividadFormularioNombre.text = form.nombre
                mBind.unaActividadFormularioTipo.text = form.tipo
            }




            vm.CargarTodosUsuariosdeActividad(o);
            vm._listado_act_del_usu.observe(this, Observer {
                mAdapter.setLista(it);
                mAdapter.notifyDataSetChanged()
            })





        })

        vm.CargarLaActividad()









    }

    private fun Init() {

        mBind= ActivitySActBinding.inflate(layoutInflater)
        mBind.included.toolbar.title = resources.getString(R.string.titleactiv)
        setContentView(mBind.root)
        vm.id_actividad=intent.getStringExtra(BIN.EXTRA_ID)


        if (BIN.ES_ADMIN()){



        }else{

            mBind.buEliminar.visibility=View.GONE
            mBind.buEditar.visibility=View.GONE

        }









    }

    private fun InitRecycler() {
        mRecyclerView=mBind.recycler
        val llm = LinearLayoutManager(this);
        llm.orientation = LinearLayoutManager.VERTICAL;
        mRecyclerView.layoutManager = llm;
        mAdapter= VerUsDeActAdapter(this,{}) ;
        mRecyclerView.adapter=mAdapter;
    }







    private fun getThis(): Context {
        return this;
    }

    fun CancelarClick(view: View) {finish()}
    fun EliminarClick(view: View) {

        if(!BIN.TengoInternet(getThis(),true)){return}

        lifecycleScope.launch {
            vm.BorrarActividad(vm.id_actividad!!, {
                Toast.makeText(
                    getThis(),
                    resources.getString(R.string.usuario_borrado),
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }, {

            });
        }


    }



    fun RevisarPreguntas(view: View) {

        if(!vm.PUEDE_REVISAR_PREGUNTAS()){Toast.makeText(getThis(),resources.getString(R.string.no_pudo_cargar),Toast.LENGTH_SHORT).show();return}


            val form= BIN.getThisAct()?.ref_formulario!!.fetchIfNeeded<Formulario>()
            if(!BIN.ES_ADMIN()){ BIN.PinSelected(BIN.CARGAR_USUARIO_LOGED()!!) }
            startActivity(Intent(this, SForm::class.java).putExtra(BIN.EXTRA_ID,form.objectId).putExtra(BIN.EXTRA_TIENE_ACTIVIDAD_ASOCIADA,true))


    }



}




