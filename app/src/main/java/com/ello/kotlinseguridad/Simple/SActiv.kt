package com.ello.kotlinseguridad.Simple

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ello.kotlinseguridad.*
import com.ello.kotlinseguridad.Adapter.VerUsDeActAdapter
import com.ello.kotlinseguridad.Editar.EActiv
import com.ello.kotlinseguridad.Editar.EUsuario
import com.ello.kotlinseguridad.databinding.ActivitySActBinding


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
        CreateMyOptionMenu()

        vm.CargarLaActividad({ o->

            //mBind.included.toolbar.title = Snippetk.CortarTitle(o.nombre)

            mBind.included.toolbar.setTitleTextColor(Color.BLACK)
            mBind.included.toolbar.setSubtitleTextColor(Color.BLACK)

            mBind.unaActividadName.text=o.nombre
            mBind.unaActividadFecha.text=o.LeerFechaR()
            mBind.unaActividadDescripci.text=o.desc

            vm.CargarTodosUsuariosdeActividad(o,
                    {
                        mAdapter.setLista(it);
                        mAdapter.notifyDataSetChanged()
                    },
                    {
                        Log.e("error", "Actividad sin usuarios")
                    }
            )

        },{})


    }

    private fun Init() {

        mBind= ActivitySActBinding.inflate(layoutInflater)
        mBind.included.toolbar.title = resources.getString(R.string.titleactiv)
        setContentView(mBind.root)
        val id= intent.getStringExtra("id");
        vm.id_actividad=id
    }

    private fun InitRecycler() {
        mRecyclerView=mBind.recycler
        val llm = LinearLayoutManager(this);
        llm.orientation = LinearLayoutManager.VERTICAL;
        mRecyclerView.layoutManager = llm;
        mAdapter= VerUsDeActAdapter(this,{}) ;
        mRecyclerView.adapter=mAdapter;
    }

    private fun CreateMyOptionMenu() {

        val menu=mBind.included.toolbar.menu
        menuInflater.inflate(R.menu.simpleusuariomenu, menu)
        val elimiItem: MenuItem? = menu?.findItem(R.id.menu_item_eliminar)
        elimiItem?.setOnMenuItemClickListener {

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
            return@setOnMenuItemClickListener false;
        }


        val editarItem: MenuItem? = menu?.findItem(R.id.menu_item_editar)
        editarItem?.setOnMenuItemClickListener {

            lifecycleScope.launch {
                val i =Intent(getThis(), EActiv::class.java);
                i.putExtra(EUsuario.EXTRA_OBJ_ID,vm.id_actividad)
                startActivity(i)
            }
            return@setOnMenuItemClickListener true;
        }
    }





    private fun getThis(): Context {
        return this;
    }


    }




