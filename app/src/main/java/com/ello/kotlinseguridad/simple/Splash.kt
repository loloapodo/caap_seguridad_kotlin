package com.ello.kotlinseguridad.simple

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.ello.kotlinseguridad.bin.BIN.Companion.REQUEST_SELECT_IMAGE
import com.ello.kotlinseguridad.bin.BIN.Companion.REQUEST_TAKE_PHOTO
import com.ello.kotlinseguridad.bin.CRUD
import com.ello.kotlinseguridad.bin.Snippets
import com.ello.kotlinseguridad.R
import com.ello.kotlinseguridad.databinding.ActivitySplashBinding
import kotlinx.coroutines.launch
import java.io.IOException


class Splash : AppCompatActivity() {

    private lateinit var model: CRUD
    private lateinit var mBind:ActivitySplashBinding
    private  var mFoto:Bitmap? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        //model= Vmodel(this)
        mBind=ActivitySplashBinding.inflate(layoutInflater)
        setContentView(mBind.root)
    }

    fun LoadFoto(view: View) {

        val intent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.INTERNAL_CONTENT_URI
        ).setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION and Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_SELECT_IMAGE)
    }




    fun TESTCLICK(view: View) {

        lifecycleScope.launch{
          /*  model.EditarUsuario("Pp66moEwum","Modificado","ji","ji",mFoto,
                    { Toast.makeText(getThis(),"GOOD",Toast.LENGTH_SHORT).show() },
                    { Toast.makeText(getThis(),"BAD",Toast.LENGTH_SHORT).show() })*/

            /*
            model.BorrarUsuario("Pp66moEwum",{ Toast.makeText(getThis(),"GOOD",Toast.LENGTH_SHORT).show() },
                    { Toast.makeText(getThis(),"BAD",Toast.LENGTH_SHORT).show() })
                    */

            /*
            model.CargarTodosUsuario(
                    {listado->MDebug.VerUsuarios(listado)},
                    { Toast.makeText(getThis(),"BAD",Toast.LENGTH_SHORT).show() })
                    */

/*
          val fecha= Calendar.getInstance().timeInMillis.toString()
          val fecha_limite= Calendar.getInstance().timeInMillis.toString()
            val preguntas= listOf("negro","azul","rojo","rojizo")
            model.CrearFormulario("Formulario4",fecha,fecha_limite,preguntas,
                    { Toast.makeText(getThis(),"GOOD",Toast.LENGTH_SHORT).show() },
                    { Toast.makeText(getThis(),"BAD",Toast.LENGTH_SHORT).show() })
*/

/*
            val fecha= Calendar.getInstance().timeInMillis.toString()
            val fecha_limite= Calendar.getInstance().timeInMillis.toString()
            val preguntas= listOf("Pais1","Pais2","Pais3","Pais4")
            model.EditarFormulario("vJKxrYBqXn","Paises",fecha,fecha_limite,preguntas,
                    { Toast.makeText(getThis(),"GOOD",Toast.LENGTH_SHORT).show() },
                    { Toast.makeText(getThis(),"BAD",Toast.LENGTH_SHORT).show() })
*/
            /*
            model.BorrarFormulario("Dye5OA0I1l",
                    { Toast.makeText(getThis(),"GOOD",Toast.LENGTH_SHORT).show() },
                    { Toast.makeText(getThis(),"BAD",Toast.LENGTH_SHORT).show() })

                    */


        /*
            model.CargarTodosFormularios(
                    {MDebug.VerFormularios(it); Toast.makeText(getThis(),"GOOD",Toast.LENGTH_SHORT).show() },
                    { Toast.makeText(getThis(),"BAD",Toast.LENGTH_SHORT).show() })

*/


            /*
            var f= Formulario()
            f.objectId="jMN3WJrprk"

            model.CargarTodasPreguntasDelFormulario(f,
                    {MDebug.VerPreguntas(it); Toast.makeText(getThis(),"GOOD",Toast.LENGTH_SHORT).show() },
                    { Toast.makeText(getThis(),"BAD",Toast.LENGTH_SHORT).show() })
*/




/*

            model.CargarTodosUsuario(
                    {listado->MDebug.VerUsuarios(listado)
                    lifecycleScope.launch {

                        model.CrearActividad(listado.subList(2,4),"Echar fuego","",
                                {Toast.makeText(getThis(),"Actividad creaada",Toast.LENGTH_SHORT).show()},{})


                    }
                    },
                    { Toast.makeText(getThis(),"BAD",Toast.LENGTH_SHORT).show() })
*/






/*
            model.CargarTodasActividades(
                    {
                        Log.e("Mostrar","CargarTodasActividades")
                        MDebug.VerActividades(it)

                    },{ Toast.makeText(getThis(),"BAD",Toast.LENGTH_SHORT).show() })
*/


/*

            delay(5000)
            model.CargarTodosUsuariosdeActividad("HZFomqZm05",
                    {act,list->
                        Log.e("Mostrar","CargarTodasActividadesConUsuarios")
                        MDebug.VerActivyUsuarios(act,list)
                    },{ Toast.makeText(getThis(),"BAD",Toast.LENGTH_SHORT).show() })

*/


/*
                    delay(5000)
                    model.CargarTodasActividadesDelUsuario("YzPcmwen3T",
                            {lista->
                                Log.e("Mostrar","CargarTodasActividadesDelUsuario")
                                MDebug.VerActividades(lista)
                            },{ Toast.makeText(getThis(),"BAD CargarTodasActividadesDelUsuario ",Toast.LENGTH_SHORT).show() })

*/

/*

            model.BorrarActividad("9hEYFg0FIN",{
                Toast.makeText(getThis(),"Good Borrar actividad ",Toast.LENGTH_SHORT).show()},{
                Toast.makeText(getThis(),"BAD Borrar actividad ",Toast.LENGTH_SHORT).show()
            })

*/







        }
    }

    private fun getThis(): Context {
return  this
    }


    override  fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {

        Log.d("OnResultSeleccionar", "las fotos")
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_SELECT_IMAGE) {
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
                } //todo
            } else if (requestCode == REQUEST_TAKE_PHOTO) {
                Log.d("Tomada", " de la camara")
                mFoto = data?.extras!!["data"] as Bitmap
                if (mFoto != null) {
                    mBind.imageView.setImageBitmap(mFoto)
                } else {
                    Log.e("Error", "al tomar imagen")
                } //todo
            }
        }


        //model.PonerFoto(mFoto,mBind.imageView)
        super.onActivityResult(requestCode, resultCode, data)
    }


}