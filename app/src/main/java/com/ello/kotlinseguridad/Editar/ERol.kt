package com.ello.kotlinseguridad.Editar

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ello.kotlinseguridad.*
import com.ello.kotlinseguridad.databinding.ActivityERolBinding


class ERol : AppCompatActivity() {



    private lateinit var mBind: ActivityERolBinding
    private lateinit var vm: ERolVM


    var mObjId_toEdit:String? = "" // Editar, 1 Crear

    companion object{
       var EXTRA_OBJ_ID="extra_editar"
   }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Init()

    }

    private fun Init() {
        vm= ERolVM()
        mBind= ActivityERolBinding.inflate(layoutInflater)

        setContentView(mBind.root)
        if (intent.hasExtra(EXTRA_OBJ_ID)) { mObjId_toEdit=intent.getStringExtra(EXTRA_OBJ_ID) }

        if (mObjId_toEdit.isNullOrEmpty()){mBind.included.toolbar.title = resources.getString(R.string.titleCRol)}
        else{ mBind.included.toolbar.title = resources.getString(R.string.titleErol) }




    }

    fun AcpetarClick(view: View) {


            with(mBind) {

                if (vm.CamposEstanMal(editusuarioNombre)) {
                    Toast.makeText(
                        getThis(),
                        resources.getString(R.string.toast_campos_mal),
                        Toast.LENGTH_SHORT
                    ).show();
                    return
                }
            }


                if (mObjId_toEdit.isNullOrEmpty())//Crear
                {
                Log.e("Crear", "Crear")
                    vm.CrearRol(mBind.editusuarioNombre.text.toString(),
                        {
                            Toast.makeText(
                                getThis(),
                                resources.getString(R.string.guardado_editar_usuario),
                                Toast.LENGTH_SHORT
                            ).show();setResult(drawer1.RES_OK_CREAR_ROL);finish()
                        },
                        {
                            Toast.makeText(
                                getThis(),
                                resources.getString(R.string.error_editar_usuario),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    )



            }else //Editar
            {
                Log.e("Editar", "Editar")
                vm.EditarRol(
                    mObjId_toEdit!!,mBind.editusuarioNombre.text.toString(),
                    {
                        Toast.makeText(
                            getThis(),
                            resources.getString(R.string.guardado_editar_usuario),
                            Toast.LENGTH_SHORT
                        ).show();setResult(drawer1.RES_OK_CREAR_EQUIP);finish()
                    },
                    {
                        Toast.makeText(
                            getThis(),
                            resources.getString(R.string.error_editar_usuario),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                )
            }



        }


    fun getThis():Context{return this}



    fun CancelarClick(view: View) {
        finish();
    }





}