package com.ello.kotlinseguridad.Simple

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.ello.kotlinseguridad.*
import com.ello.kotlinseguridad.Adapter.VerUsDeActAdapter
import com.ello.kotlinseguridad.BIN.BIN
import com.ello.kotlinseguridad.Editar.ERol
import com.ello.kotlinseguridad.databinding.ActivitySRolBinding
import com.ello.kotlinseguridad.drawer1.Companion.RES_OK_CREAR_ROL


import kotlinx.coroutines.launch

class SRol : AppCompatActivity() {

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: VerUsDeActAdapter
    private lateinit var mBind: ActivitySRolBinding
    private  var vm: SRolVM = SRolVM()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        Init()
        InitRecycler()
        CreateMyOptionMenu()

        vm.CargarElRol({ o->


            //mBind.included.toolbar.setTitleTextColor(Color.BLACK)
            //mBind.included.toolbar.setSubtitleTextColor(Color.BLACK)
            with(mBind){
                unaActividadName.text = o.nombre_rol

                if(!o.per_actividad!!)
                {
                    radioAllowActividades.visibility=View.GONE
                }
                if(!o.per_empleado!!)
                {
                    radioAllowEmpleado.visibility=View.GONE
                }
                if(!o.per_formulario!!)
                {
                    radioAllowFormularios.visibility=View.GONE
                }
                if(!o.per_equipamiento!!)
                {
                    radioAllowEquipamiento.visibility=View.GONE
                }

                if (!o.per_actividad!!&&!o.per_equipamiento!!&&!o.per_empleado!!&&!o.per_formulario!!) { radioNungunPermiso.visibility=View.VISIBLE }
                else { radioNungunPermiso.visibility=View.GONE}

            }








        }) { Log.e("SRol","Error al cargar el Rol")}

    }

    private fun Init() {

        mBind= ActivitySRolBinding.inflate(layoutInflater)
        mBind.included.toolbar.title = resources.getString(R.string.titlerol)
        setContentView(mBind.root)
        val id= intent.getStringExtra("id");
        vm.id_rol=id
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
        val i=Intent(this, ERol::class.java)
        i.putExtra(ERol.EXTRA_OBJ_ID,vm.id_rol)
        startActivityForResult(i, BIN.REQ_EDITAR_EQUIP)
    }

     fun EliminarClick(view: View) {

         lifecycleScope.launch {
             vm.BorrarRol(vm.id_rol,{
                 Toast.makeText(getThis(),resources.getString(R.string.rol_eliminado), Toast.LENGTH_SHORT).show();finish()
             },{
                 Toast.makeText(getThis(),resources.getString(R.string.rol_error_eliminado), Toast.LENGTH_SHORT).show();finish()
             })
         }


    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == BIN.REQ_EDITAR_ROL && resultCode == RES_OK_CREAR_ROL) {

            setResult(RES_OK_CREAR_ROL)
            finish() // CREO Q SE ACTUALIZA SOLO}
            super.onActivityResult(requestCode, resultCode, data)
        }
    }



}




