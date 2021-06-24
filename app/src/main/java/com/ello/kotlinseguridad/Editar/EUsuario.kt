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
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.core.view.size
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.ello.kotlinseguridad.*
import com.ello.kotlinseguridad.BIN.BIN
import com.ello.kotlinseguridad.BIN.Snippetk
import com.ello.kotlinseguridad.BIN.Snippets
import com.ello.kotlinseguridad.databinding.ActivityEUsBinding
import kotlinx.coroutines.launch
import java.io.IOException


class EUsuario : AppCompatActivity() {



    private lateinit var mBind: ActivityEUsBinding
    private lateinit var vm: EUsuarioVM
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
        vm= EUsuarioVM(this)
        mBind= ActivityEUsBinding.inflate(layoutInflater)

        setContentView(mBind.root)

        vm.estado.observe(this, Observer {

            mBind.editusuarioNombre.setText(vm.nombre)
            mBind.editusuarioApellido.setText(vm.apellido)
            mBind.editusuarioCedula.setText(vm.cedula)
            mBind.editusuarioDireccion.setText(vm.direccion)
            mBind.editusuarioTelefono.setText(vm.telefono)
            mBind.editusuarioUsuario.setText(vm.usuario)
            mBind.editusuarioContrase.setText(vm.contrasena)




//if (t6Spinner.selectedItem.toString().contains("Asignar")){return true}

            lifecycleScope.launch {Snippetk.PonerFoto(mBind.imageView,vm.parseFileFoto)}
        })

        if (intent.hasExtra(EXTRA_OBJ_ID)) { mObjId_toEdit=intent.getStringExtra(EXTRA_OBJ_ID) }

        if (mObjId_toEdit.isNullOrEmpty()){mBind.included.toolbar.title = resources.getString(R.string.titleCusuario)}
        else{
            mBind.included.toolbar.title = resources.getString(R.string.titleEusuario)
            vm.CargarDatosUsuario()

        }



        vm._roles.observe(this, Observer {

            val adapter: ArrayAdapter<String> = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, it)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            mBind.editarusuarioSpinnerRoles.adapter = adapter
        })





        vm.CargarTodosRoles()





    }

    fun AcpetarClick(view: View) {


            with(mBind){

               if (vm.CamposEstanMal(
                       editusuarioUsuario,
                       editusuarioContrase,
                       editusuarioNombre,
                       editusuarioApellido,
                       editusuarioCedula,
                               editusuarioDireccion,

                               editusuarioTelefono,

                       editarusuarioSpinnerRoles
                   ))
               {
                   Toast.makeText(
                       getThis(),
                       resources.getString(R.string.toast_campos_mal),
                       Toast.LENGTH_SHORT
                   ).show();
                   return
               }


                if (mObjId_toEdit.isNullOrEmpty())//Crear
                {
                Log.e("Crear", "Crear")
                    vm.CrearUsuario(
                        editusuarioUsuario.text.toString(),
                        editusuarioContrase.text.toString(),
                        editusuarioNombre.text.toString(),
                        editusuarioApellido.text.toString(),
                        editusuarioCedula.text.toString(),
                            editusuarioDireccion.text.toString(),
                            editusuarioTelefono.text.toString(),
                            editarusuarioAdmin.isChecked,

                        mFoto,
                        {
                            Toast.makeText(
                                getThis(),
                                resources.getString(R.string.guardado_editar_usuario),
                                Toast.LENGTH_SHORT
                            ).show();setResult(drawer1.RES_OK_CREAR_USUARIO);finish()
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
                vm.EditarUsuario(
                    mObjId_toEdit!!,
                    editusuarioUsuarioL.editText?.text.toString(),
                    editusuarioContraseL.editText?.text.toString(),
                    editusuarioNombreL.editText?.text.toString(),
                    editusuarioApellidoL.editText?.text.toString(),
                    editusuarioCedulaL.editText?.text.toString(),
                        editusuarioDireccionL.editText?.text.toString(),
                        editusuarioTelefonoL.editText?.text.toString(),
                        editarusuarioAdmin.isChecked,
                    mFoto,
                    {
                        Toast.makeText(
                            getThis(),
                            resources.getString(R.string.guardado_editar_usuario),
                            Toast.LENGTH_SHORT
                        ).show();setResult(drawer1.RES_OK_CREAR_USUARIO);finish()
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