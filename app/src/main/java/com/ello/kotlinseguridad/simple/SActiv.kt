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
import com.ello.kotlinseguridad.adapter.VerUsDeActAdapter
import com.ello.kotlinseguridad.bin.BIN
import com.ello.kotlinseguridad.bin.Snippetk
import com.ello.kotlinseguridad.databinding.ActivitySActBinding
import com.ello.kotlinseguridad.parseobj.Actividad
import com.ello.twelveseconds.Formulario
import com.parse.ParseQuery


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


        vm._actividad.observe(this, Observer { o ->


            BIN.PinSelected(o)


            LlenarInfoDeLaActividad(o)
            LlenarInfoDelFormularioDeLaActividad(o) //DE ESTE DEPENDE LA VISIBILIDAD DEL BOTON VER PREGUNTAS
            LlenarInfoUsuariosDeActividad(o)



        })

            vm.ya_cargo_formul_de_la_act.observe(this, Observer {
                if (it==true){
                    mBind.linearformularioDeLaActividad.visibility=View.VISIBLE
                    mBind.buRevisarPreguntas.visibility=View.VISIBLE
                }else{
                    mBind.linearformularioDeLaActividad.visibility=View.GONE
                    mBind.buRevisarPreguntas.visibility=View.INVISIBLE
                }

            })

        vm.CargarLaActividad()









    }

    private fun LlenarInfoUsuariosDeActividad(o:Actividad) {
        vm._listado_usuarios_dela_act.observe(this, Observer {
            mAdapter.setLista(it);
            mAdapter.notifyDataSetChanged()
        })
        vm.CargarTodosUsuariosdeActividad(o);
    }

    private fun LlenarInfoDelFormularioDeLaActividad(o: Actividad) {

        val f = o.ref_formulario
        if (f == null) {
            Log.e("ref_formulario", "formulario de la actividad es null")
        }

        val query = ParseQuery.getQuery<Formulario>(Formulario.class_name)
        query.fromPin(o.objectId + BIN.PIN_SUFIJO_ACT_RELATION2FORM)
        query.getFirstInBackground { feteched, e ->
            if (e == null) {
                mBind.unaActividadFormularioNombre.text = feteched.nombre
                mBind.unaActividadFormularioTipo.text = feteched.tipo
                vm.ya_cargo_formul_de_la_act.value=true
            } else {
                Log.e(
                    "--",
                    "No encontrado en local storage el formulario correspondiente a la act"
                )
                Log.e("--", "Se hara fetch entonces")
                Log.e("error", e.toString())
                f?.fetchIfNeededInBackground<Formulario> { fetchedOnline, eOnline ->
                    if (eOnline == null) {
                        fetchedOnline.pin(o.objectId + BIN.PIN_SUFIJO_ACT_RELATION2FORM)
                        mBind.unaActividadFormularioNombre.text = fetchedOnline.nombre
                        mBind.unaActividadFormularioTipo.text = fetchedOnline.tipo
                        vm.ya_cargo_formul_de_la_act.value=true
                    } else {
                        Log.e("--", "fetchInBackground")
                        Log.e("error", eOnline.toString())
                    }
                }
            }
        }
    }

    private fun LlenarInfoDeLaActividad(o: Actividad) {
        mBind.unaActividadName.text = o.nombre

        mBind.unaActividadFecha.text = o.LeerFechaR()
        mBind.unaActividadHora.text = Snippetk.LeerHoraR(o.fecha)
        mBind.unaActividadDescripci.text = o.desc
        mBind.unaActividadSitio.text = o.sitio
        mBind.unaActividadLugar.text = o.ubicacion

    }

    private fun Init() {

        mBind= ActivitySActBinding.inflate(layoutInflater)
        mBind.included.toolbar.title = resources.getString(R.string.titleactiv)
        setContentView(mBind.root)
        vm.id_actividad=intent.getStringExtra(BIN.EXTRA_ID)!!


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
        if (!vm.ya_cargo_formul_de_la_act.value!!){Toast.makeText(getThis(),resources.getString(R.string.no_pudo_cargarelform_deactividad),Toast.LENGTH_SHORT).show();return}


            val form= BIN.getThisAct()?.ref_formulario!!.fetchIfNeeded<Formulario>()
            if(!BIN.ES_ADMIN()){ BIN.PinSelected(BIN.CARGAR_USUARIO_LOGED()!!) }
            startActivity(Intent(this, SForm::class.java).putExtra(BIN.EXTRA_ID,form.objectId).putExtra(BIN.EXTRA_TIENE_ACTIVIDAD_ASOCIADA,true))


    }



}




