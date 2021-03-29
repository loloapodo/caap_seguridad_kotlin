package com.ello.kotlinseguridad.Simple

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ello.kotlinseguridad.Adapter.VerFormulAdapter
import com.ello.kotlinseguridad.Editar.EForm
import com.ello.kotlinseguridad.R
import com.ello.kotlinseguridad.Snippetk
import com.ello.kotlinseguridad.databinding.ActivitySimpleFormularioBinding
import kotlinx.coroutines.launch

class SForm : AppCompatActivity() {


    private lateinit var vm: SFormVM
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: VerFormulAdapter
    private  lateinit var mBind: ActivitySimpleFormularioBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




        Init()
        InitRecycler()
        CreateMyOptionMenu()



        vm.CargarElFormularioLocal({ form->


            mBind.unaFormularioNombre.text=form.nombre
            mBind.unFormulFecha.text=Snippetk.LeerFechaR(form.fecha)
            mBind.unFormulFechaExp.text=Snippetk.LeerFechaR(form.fecha_limite)



                vm.CargarTodasPreguntasDelFormulario(form,{
                mAdapter.setPreguntas(it)
                mAdapter.notifyDataSetChanged()



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
        mAdapter= VerFormulAdapter(this,{}) ;
        mRecyclerView.adapter=mAdapter;
    }

    private fun Init() {
        mBind= ActivitySimpleFormularioBinding.inflate(layoutInflater)
        mBind.included.toolbar.title = resources.getString(R.string.titleformulario)
        setContentView(mBind.root)
        vm= SFormVM()
        vm.id_formulario= intent.getStringExtra("id")!!;
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
}