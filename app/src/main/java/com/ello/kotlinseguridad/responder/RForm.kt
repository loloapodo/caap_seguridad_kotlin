package com.ello.kotlinseguridad.responder

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ello.kotlinseguridad.Adapter.RespuestaAdapter
import com.ello.kotlinseguridad.BIN.BIN
import com.ello.kotlinseguridad.BIN.Snippetk
import com.ello.kotlinseguridad.BIN.Snippets
import com.ello.kotlinseguridad.Estado
import com.ello.kotlinseguridad.ParseObj.Pregunta
import com.ello.kotlinseguridad.R
import com.ello.kotlinseguridad.databinding.ActRFormBinding
import java.io.IOException


class RForm : AppCompatActivity() {


    private var mPosition: Int = 0
    private lateinit var mOneImageAdapt: ImageView

    private lateinit var mBind: ActRFormBinding
    private lateinit var vm: RFormVM
    private lateinit var mAdapter: RespuestaAdapter
    private lateinit var mRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Init()
        InitRecycler(mBind.root)


        vm.Cargar()
        mBind.unaFormularioNombre.text = intent.getStringExtra(BIN.EXTRA_NOMBRE)

        vm._listado.observe(this, Observer {

            if (it.isNotEmpty()) {
                mAdapter.SetPreguntas(it as ArrayList<Pregunta>)
            }
            mAdapter.notifyDataSetChanged()
        })





    }



    fun EnviarRespuestaClick(view: View) {

        if (vm.isNotIdle()){Toast.makeText(view.context,resources.getString(R.string.en_progreso),Toast.LENGTH_SHORT).show();return}

        if (BIN.TengoPermisoLocalizacion(this)) {
            EnviarRespuesta()
        } else {
            BIN.PedirLocationAppPermission(this)
        }
    }

    private fun EnviarRespuesta() {

        vm.EnviarRespuesta( this,mAdapter.listResp, mAdapter.listPreg, mAdapter.listFotos, {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        },{
            Toast.makeText(this, resources.getString(R.string.respuesta_ok), Toast.LENGTH_SHORT).show()
            setResult(Activity.RESULT_OK)
            finish()
        }, {
            Toast.makeText(this, resources.getString(R.string.respuesta_ko), Toast.LENGTH_SHORT).show()
            setResult(Activity.RESULT_CANCELED)
            finish()
        })
    }



   override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>, grantResults: IntArray) {
        if (requestCode == BIN.REQUEST_MY_PERMISSIONS_LOCATION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                EnviarRespuesta()
            } else {
                Toast.makeText(getThis(), resources.getString(R.string.location_denied), Toast.LENGTH_SHORT).show()
            }
        }
    }






    fun LoadFoto(i:ImageView,p:Int) {

        mOneImageAdapt=i
        mPosition=p

        //todo sacar de la camara tambien
        val intent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.INTERNAL_CONTENT_URI
        ).setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION and Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        intent.type = "image/*"
        startActivityForResult(intent, BIN.REQUEST_SELECT_IMAGE)
    }

    override  fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

          var mFoto: Bitmap?=null
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
                if (mFoto != null) {
                    Log.d("Imagen no null", "de la camara")

                } else {
                    Log.e("Error", " camara ")
                }
            }
        }



        if (mAdapter.listFotos.size>mPosition){
            mAdapter.listFotos[mPosition]=mFoto
            Snippetk.PonerFoto(mFoto, mOneImageAdapt)
        }else{Log.e("Error","00334 MImageposicion=${mPosition.toString()}")}

        super.onActivityResult(requestCode, resultCode, data)
    }






















    private fun Init() {
        mBind = ActRFormBinding.inflate(layoutInflater)
        mBind.included.toolbar.title = resources.getString(R.string.titleformulario)
        setContentView(mBind.root)
        vm = RFormVM()


        vm.id = intent.getStringExtra(BIN.EXTRA_ID)!!


    }

    private fun InitRecycler(root: View) {

        mRecyclerView = root.findViewById(R.id.recycler)
        val llm = LinearLayoutManager(root.context);
        llm.orientation = LinearLayoutManager.VERTICAL;
        mRecyclerView.layoutManager = llm;
        mAdapter = RespuestaAdapter(root.context) { foto, pos -> LoadFoto(foto, pos) }
        mRecyclerView.adapter = mAdapter;
    }

    private fun getThis(): Context { return this }

}