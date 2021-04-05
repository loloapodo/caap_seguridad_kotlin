package com.ello.kotlinseguridad.Simple

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ello.kotlinseguridad.Adapter.VerEstDelFormAdapter
import com.ello.kotlinseguridad.Adapter.VerPreDelFormAdapter
import com.ello.kotlinseguridad.BIN.BIN
import com.ello.kotlinseguridad.BIN.BIN.Companion.REQ_LLENAR_FORMULARIO
import com.ello.kotlinseguridad.Editar.EForm
import com.ello.kotlinseguridad.R
import com.ello.kotlinseguridad.BIN.Snippetk
import com.ello.kotlinseguridad.Estado
import com.ello.kotlinseguridad.databinding.ActivitySFormBinding
import com.ello.kotlinseguridad.responder.RForm

import kotlinx.coroutines.launch


/**
 * Muestra un formulario simple.
 * Si es admin puede ver las preguntas del formulario y el estado de envio para todos sus usuarios boton1
 * Si es user puede responder o ver el formulario respondido boton2
 */
class SForm : AppCompatActivity() {




    companion object{ val EXTRA_RESUELTO="extra_resuelto"}

    private lateinit var vm: SFormVM
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapterPreguntas: VerPreDelFormAdapter
    private lateinit var mAdapterEstados: VerEstDelFormAdapter

    private  lateinit var mBind: ActivitySFormBinding





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




        Init()
        InitRecycler()
        CreateMyOptionMenu()



        vm.CargarElFormularioLocal({ form->


            mBind.unaFormularioNombre.text=form.nombre
            mBind.unFormulFecha.text= Snippetk.LeerFechaR(form.fecha)
            mBind.unFormulFechaExp.text= Snippetk.LeerFechaR(form.fecha_limite)
            mBind.unFormulHora.text= Snippetk.LeerHoraR(form.fecha_limite)



                vm.CargarTodasPreguntasDelFormulario(form,{
                mAdapterPreguntas.setPreguntas(it)
                mAdapterPreguntas.notifyDataSetChanged()



                },{finish()})
        }){
            finish()
        }


    }

    private fun InitRecycler() {
        mRecyclerView=mBind.recycler
        val llm = LinearLayoutManager(this);
        llm.orientation = LinearLayoutManager.VERTICAL;
        mRecyclerView.layoutManager = llm;
        mAdapterPreguntas= VerPreDelFormAdapter(this,{}) ;
        mAdapterEstados= VerEstDelFormAdapter(this,{}) ;
        mRecyclerView.adapter=mAdapterPreguntas;
    }

    private fun Init() {
        mBind= ActivitySFormBinding.inflate(layoutInflater)
        mBind.included.toolbar.title = resources.getString(R.string.titleformulario)
        setContentView(mBind.root)
        vm= SFormVM()
        vm.id_formulario= intent.getStringExtra("id")!!

        if (BIN.ES_ADMIN()){ mBind.buResponderOVerrespuestas.visibility=View.GONE;mBind.buAbrirEstadoDeEnviosDelForm.visibility=View.VISIBLE}
        else                {
            mBind.buAbrirEstadoDeEnviosDelForm.visibility=View.GONE;mBind.buResponderOVerrespuestas.visibility=View.VISIBLE
            if (intent.getBooleanExtra(EXTRA_RESUELTO,false)){
                mBind.buResponderOVerrespuestas.text=resources.getString(R.string.ver_mi_respuesta)
            }
        }



    }

    private fun CreateMyOptionMenu() {

        val menu=mBind.included.toolbar.menu
        menuInflater.inflate(R.menu.simpleusuariomenu, menu)
        val elimiItem: MenuItem? = menu?.findItem(R.id.menu_item_eliminar)
        elimiItem?.setOnMenuItemClickListener {

            vm.BorrarFormulario(vm.id_formulario,{
                Toast.makeText(getThis(),resources.getString(R.string.formulario_eliminado),Toast.LENGTH_SHORT).show();finish()
            },{
                Toast.makeText(getThis(),resources.getString(R.string.error_eliminar_form),Toast.LENGTH_SHORT).show();finish()
            })
            return@setOnMenuItemClickListener false;
        }


        val editarItem: MenuItem? = menu?.findItem(R.id.menu_item_editar)
        editarItem?.setOnMenuItemClickListener {

            lifecycleScope.launch {
                val i = Intent(getThis(), EForm::class.java);
                i.putExtra(EForm.EXTRA_OBJ_ID,vm.id_formulario)
                startActivity(i)
            }
            return@setOnMenuItemClickListener true;
        }
    }

    private fun getThis(): Context =this




    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {


        if (requestCode==REQ_LLENAR_FORMULARIO){
            setResult(resultCode)
            finish()
        }

        super.onActivityResult(requestCode, resultCode, data)

    }



    fun Responder_O_VerRespuestaClick(view: View) {

        if (intent.getBooleanExtra(EXTRA_RESUELTO,false))
        {
            //todo
            Toast.makeText(getThis(),"TODO",Toast.LENGTH_SHORT).show();
        }
        else{
            val i=Intent(this, RForm::class.java)
            i.putExtra(BIN.EXTRA_ID,vm.id_formulario)
            i.putExtra(BIN.EXTRA_NOMBRE,vm.nombre_formulario)
            startActivityForResult(i,REQ_LLENAR_FORMULARIO)
        }

    }


    fun MostrarEstadoEnvio_O_Preguntas(view: View) {

        if (mRecyclerView.adapter==mAdapterPreguntas&&vm.estado.value==Estado.Idle){

            vm.DeterminarListadoEnvios({list ->
                mRecyclerView.adapter=mAdapterEstados
                mAdapterEstados.setRespuestas(list)
                mAdapterEstados.notifyDataSetChanged()
                mBind.sformAdapctertitle.text=resources.getText(R.string.estado_formularios)
                mBind.buAbrirEstadoDeEnviosDelForm.text=resources.getText(R.string.bu_ver_preguntas_del_formulario)


            },{ Log.e("Error","MostrarEstadoEnvio_O_Preguntas")})
        }
        else
        {
        mRecyclerView.adapter=mAdapterPreguntas
            mBind.sformAdapctertitle.text=resources.getText(R.string.preguntas)
            mBind.buAbrirEstadoDeEnviosDelForm.text=resources.getText(R.string.bu_ver_estados_del_formulario)
        }






    }
}