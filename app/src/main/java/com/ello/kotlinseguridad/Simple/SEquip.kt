package com.ello.kotlinseguridad.Simple

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.ello.kotlinseguridad.*
import com.ello.kotlinseguridad.Adapter.VerUsDeActAdapter
import com.ello.kotlinseguridad.BIN.BIN
import com.ello.kotlinseguridad.BIN.Snippetk
import com.ello.kotlinseguridad.Editar.EEquip
import com.ello.kotlinseguridad.databinding.ActivitySEquBinding
import com.ello.kotlinseguridad.drawer1.Companion.RES_OK_CREAR_EQUIP
import kotlinx.coroutines.GlobalScope


import kotlinx.coroutines.launch

class SEquip : AppCompatActivity() {

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: VerUsDeActAdapter
    private lateinit var mBind: ActivitySEquBinding
    private  var vm: SEquipVM = SEquipVM()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        Init()
        InitRecycler()
        CreateMyOptionMenu()

        vm.CargarElEquipamiento({ o->


            //mBind.included.toolbar.setTitleTextColor(Color.BLACK)
            //mBind.included.toolbar.setSubtitleTextColor(Color.BLACK)

            mBind.unaActividadName.text=o.nombre
            mBind.unEquipoUso.text=o.uso
            mBind.unEquipoDescripcion.text=o.descripcion

            lifecycleScope.launch {Snippetk.PonerFoto(mBind.unaPersonaImage,o.foto)}



        },{})


    }

    private fun Init() {

        mBind= ActivitySEquBinding.inflate(layoutInflater)
        mBind.included.toolbar.title = resources.getString(R.string.titleequip)
        setContentView(mBind.root)
        val id= intent.getStringExtra("id");
        vm.id_equip=id
    }

    private fun InitRecycler() {

    }

    private fun CreateMyOptionMenu() {


    }



    private fun getThis(): Context {
        return this;
    }

    fun CancelarClick(view: View) {
        finish()

    }

    fun EditarClick(view: View) {
        val i=Intent(this, EEquip::class.java)
        i.putExtra(EEquip.EXTRA_OBJ_ID,vm.id_equip)
        startActivityForResult(i, BIN.REQ_EDITAR_EQUIP)
    }

     fun EliminarClick(view: View) {

         lifecycleScope.launch {
             vm.BorrarEquipamiento(vm.id_equip,{
                 Toast.makeText(getThis(),resources.getString(R.string.equipamiento_eliminado), Toast.LENGTH_SHORT).show();finish()
             },{
                 Toast.makeText(getThis(),resources.getString(R.string.equipamiento_error_eliminado), Toast.LENGTH_SHORT).show();finish()
             })
         }


    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == BIN.REQ_EDITAR_EQUIP && resultCode == RES_OK_CREAR_EQUIP) {

            setResult(RES_OK_CREAR_EQUIP)
            finish() // CREO Q SE ACTUALIZA SOLO}
            super.onActivityResult(requestCode, resultCode, data)
        }
    }



}




