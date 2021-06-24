package com.ello.kotlinseguridad.responder

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ceylonlabs.imageviewpopup.ImagePopup
import com.ello.kotlinseguridad.Adapter.EquipaCheckAdapter
import com.ello.kotlinseguridad.Adapter.EvidenciaAdapter
import com.ello.kotlinseguridad.Adapter.RespuestaAdapter
import com.ello.kotlinseguridad.BIN.BIN
import com.ello.kotlinseguridad.ParseObj.Pregunta
import com.ello.kotlinseguridad.R
import com.ello.kotlinseguridad.databinding.ActivityRFormBinding
import com.ello.kotlinseguridad.databinding.ActivityRDoneBinding


class RForm : AppCompatActivity() {


    private val REQ_STORAGE=565
    private var mPosition: Int = 0
    private lateinit var mOneImageAdapt: ImageView

    private lateinit var mBind: ActivityRFormBinding
    private lateinit var vm: RFormVM
    private lateinit var mAdapter: RespuestaAdapter
    private lateinit var mAdapterEvidencia: EvidenciaAdapter
    private lateinit var mAdapterEquip: EquipaCheckAdapter
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mRecyclerViewEvidencias: RecyclerView
    private lateinit var mRecyclerViewEquipacheq: RecyclerView



    private var context: Context? = null
    var PICK_IMAGE_MULTIPLE = 1
    lateinit var imagePath: String
    var imagesPathList: MutableList<String> = arrayListOf()







    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Init()
        InitRecycler(mBind.root)


        vm.Cargar()
        mBind.unaFormularioNombre.text = intent.getStringExtra(BIN.EXTRA_NOMBRE)



        vm._listado.observe(this, Observer {

            if (it.isNotEmpty()) {
                val arrayList =ArrayList<Pregunta>()
                for(i in it){arrayList.add(i)}
                mAdapter.SetPreguntas(arrayList)
                mAdapter.notifyDataSetChanged()

            }

        })


        vm._equipamietos.observe(this, Observer {

            Log.e("observed", "_equipamietos")

            for (i in it) {
                mAdapterEquip.addEq(i.nombre!!)
            }
            mAdapterEquip.notifyDataSetChanged()

        })





    }



    fun EnviarRespuestaClick(view: View) {

        if (vm.isNotIdle()){Toast.makeText(view.context, resources.getString(R.string.en_progreso), Toast.LENGTH_SHORT).show();return}

        if (BIN.TengoPermisoLocalizacion(this)) {
            EnviarRespuesta()
        } else {
            BIN.PedirLocationAppPermission(this)
        }
    }

    private fun EnviarRespuesta() {


            if (BIN.TengoInternet(this)){

            vm.EnviarRespuesta(this, mAdapter.listResp, mAdapter.listPreg, mAdapterEvidencia.get_Bitmaps(), mAdapterEquip.get_only_chequed(), {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }, {
                Toast.makeText(this, resources.getString(R.string.respuesta_ok), Toast.LENGTH_SHORT).show()
                setResult(Activity.RESULT_OK)
                finish()
            }, {
                Toast.makeText(this, resources.getString(R.string.respuesta_ko), Toast.LENGTH_SHORT).show()
                setResult(Activity.RESULT_CANCELED)
                finish()
            })

            }
        else{

                Toast.makeText(this, resources.getString(R.string.sin_conexion_intern), Toast.LENGTH_SHORT).show()

        }




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





    //Actualmente no usado
    fun LoadFoto(i: ImageView, p: Int) {

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
/*
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
        }else{Log.e("Error", "00334 MImageposicion=${mPosition.toString()}")}

        super.onActivityResult(requestCode, resultCode, data)
    }
*/





    private fun Init() {
        mBind = ActivityRFormBinding.inflate(layoutInflater)
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


        mRecyclerViewEvidencias = root.findViewById(R.id.recycler_evidencias)
        val llm2 = LinearLayoutManager(root.context);
        //val gl = GridLayoutManager(getThis(), 2)
        llm2.orientation = LinearLayoutManager.HORIZONTAL;

        mRecyclerViewEvidencias.layoutManager = llm2;
        mAdapterEvidencia = EvidenciaAdapter(root.context) {imageView , pos->

            val imagePopup: ImagePopup
            imagePopup= ImagePopup(imageView.getContext());
            imagePopup.setBackgroundColor(Color.TRANSPARENT);  // Optional
            imagePopup.setFullScreen(true); // Optional
            //imagePopup.setHideCloseIcon(true);  // Optional
            imagePopup.setImageOnClickClose(true);  // Optional
            imagePopup.initiatePopup(imageView.getDrawable());
            imagePopup.viewPopup();

        }
        mRecyclerViewEvidencias.adapter = mAdapterEvidencia;



        mRecyclerViewEquipacheq = root.findViewById(R.id.recyclercheckequip)
        val llm3 = LinearLayoutManager(root.context);
        llm3.orientation = LinearLayoutManager.HORIZONTAL;
        mRecyclerViewEquipacheq.layoutManager =llm3// GridLayoutManager(root.context, 2)
        mAdapterEquip = EquipaCheckAdapter(root.context, false) { foto, pos-> }
        mRecyclerViewEquipacheq.adapter = mAdapterEquip;







    }

    private fun getThis(): Context { return this }

    fun AgregarEvidenciaClick(view: View) {


        if (!BIN.TengoPermisoREAD(this)){BIN.PedirREADPermission(this);return}

        if (Build.VERSION.SDK_INT < 19) {
            var intent = Intent()
            intent.type = "image/*"
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(
                    Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_MULTIPLE
            )
        } else {
            var intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "image/*"
            startActivityForResult(intent, PICK_IMAGE_MULTIPLE);
        }

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)
        // When an Image is picked
        if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == Activity.RESULT_OK
                && null != data
        ) {
            if (data.getClipData() != null) {
                var count = data.clipData!!.itemCount
                for (i in 0..count - 1) {
                    var imageUri: Uri = data!!.clipData!!.getItemAt(i).uri
                    getPathFromURI(imageUri)
                }
            } else if (data.getData() != null) {
                var imageU: Uri = data.data!!
                getPathFromURI(imageU)
            }
        }
        mAdapterEvidencia.notifyDataSetChanged()
    }

    private fun getPathFromURI(uri: Uri) {
        var path: String = uri.path!! // uri = any content Uri

        val databaseUri: Uri
        val selection: String?
        val selectionArgs: Array<String>?
        if (path.contains("/document/image:")) { // files selected from "Documents"
            databaseUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            selection = "_id=?"
            selectionArgs = arrayOf(DocumentsContract.getDocumentId(uri).split(":")[1])
        } else { // files selected from all other sources, especially on Samsung devices
            databaseUri = uri
            selection = null
            selectionArgs = null
        }
        try {
            val projection = arrayOf(
                    MediaStore.Images.Media.DATA,
                    MediaStore.Images.Media._ID,
                    MediaStore.Images.Media.ORIENTATION,
                    MediaStore.Images.Media.DATE_TAKEN
            ) // some example data you can query
            val cursor = contentResolver.query(
                    databaseUri,
                    projection, selection, selectionArgs, null
            )
            if (cursor!!.moveToFirst()) {
                val columnIndex = cursor.getColumnIndex(projection[0])
                imagePath = cursor.getString(columnIndex)
                imagesPathList.add(imagePath)
                mAdapterEvidencia.addImages(imagePath, true)
            }
            cursor.close()
        } catch (e: Exception) {
            Log.e("image", e.message, e)
        }
    }

    fun QuitarEvidenciaClick(view: View) {mAdapterEvidencia.clearImages();mAdapterEvidencia.notifyDataSetChanged()}
    fun CancelarClick(view: View) {finish()}


}