package com.ello.kotlinseguridad.Editar

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.ello.kotlinseguridad.*
import com.ello.kotlinseguridad.BIN.BIN
import com.ello.kotlinseguridad.BIN.Snippetk
import com.ello.kotlinseguridad.BIN.Snippets
import com.ello.kotlinseguridad.databinding.ActivityEEqBinding
import kotlinx.coroutines.launch
import java.io.IOException


class EEquip : AppCompatActivity() {



    private lateinit var mBind: ActivityEEqBinding
    private lateinit var vm: EEquipVM
    private  var mFoto: Bitmap? = null

    var mObjId_toEdit:String? = "" // Editar, 1 Crear

    companion object{
       var EXTRA_OBJ_ID="extra_editar"
   }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Init()

    }

    private fun Init() {
        vm= EEquipVM()
        mBind= ActivityEEqBinding.inflate(layoutInflater)
        setContentView(mBind.root)


        vm.estado.observe(this, Observer {

        mBind.editusuarioNombre.setText(vm.nombre)
        mBind.editusuarioUso.setText(vm.uso)
        mBind.editusuarioDescripcion.setText(vm.descrip)

            lifecycleScope.launch {
                Snippetk.PonerFoto(mBind.imageView,vm.parsef_image)
            }





        })

        if (intent.hasExtra(EXTRA_OBJ_ID)) { mObjId_toEdit=intent.getStringExtra(EXTRA_OBJ_ID) }

        if (mObjId_toEdit.isNullOrEmpty()){mBind.included.toolbar.title = resources.getString(R.string.titleCequipamiento)}
        else{

            vm.CargarObjeto()
            mBind.included.toolbar.title = resources.getString(R.string.titleEequipamiento)
        }




    }

    fun AcpetarClick(view: View) {


            with(mBind) {

                if (vm.CamposEstanMal(editusuarioNombre,editusuarioUso,editusuarioDescripcion)) {
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
                    vm.CrearEquipamiento(mBind.editusuarioNombre.text.toString(),mBind.editusuarioUso.text.toString(),mBind.editusuarioDescripcion.text.toString(), mFoto,
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



            }else //Editar
            {
                Log.e("Editar", "Editar")
                vm.EditarEquipamiento(
                    mObjId_toEdit!!,mBind.editusuarioNombre.text.toString(),mBind.editusuarioUso.text.toString(),mBind.editusuarioDescripcion.text.toString(),mFoto,
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
    fun LoadFoto(view: View) {

        //todo sacar de la camara tambien
        val intent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.INTERNAL_CONTENT_URI
        ).setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION and Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        intent.type = "image/*"
        startActivityForResult(intent, BIN.REQUEST_SELECT_IMAGE)
    }
    override  fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {

        Log.d("OnResultSeleccionar", "las fotos")
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == BIN.REQUEST_SELECT_IMAGE) {
                val uri: Uri? = data?.data
                try {
                    mFoto = Snippets.ROTATEIMAGE.handleSamplingAndRotationBitmap(this, uri)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                if (mFoto != null) {
                    mBind.icAddMedia.visibility=View.GONE
                    Log.d("Imagen no null", "de la galeria")

                } else {
                    Log.e("Error", "al tomar imagen")
                }
            } else if (requestCode == BIN.REQUEST_TAKE_PHOTO) {
                Log.d("Tomada", " de la camara")
                mFoto = data?.extras!!["data"] as Bitmap
            }
        }


        Snippetk.PonerFoto(mFoto, mBind.imageView)
        super.onActivityResult(requestCode, resultCode, data)
    }

    fun CancelarClick(view: View) {
        finish();
    }





}