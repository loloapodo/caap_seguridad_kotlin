package com.ello.kotlinseguridad.responder

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ceylonlabs.imageviewpopup.ImagePopup
import com.ello.kotlinseguridad.adapter.EquipaCheckAdapter
import com.ello.kotlinseguridad.adapter.EvidenciaAdapter
import com.ello.kotlinseguridad.adapter.RespuestaAdapter
import com.ello.kotlinseguridad.bin.BIN
import com.ello.kotlinseguridad.Estado
import com.ello.kotlinseguridad.parseobj.Pregunta
import com.ello.kotlinseguridad.R
import com.ello.kotlinseguridad.databinding.ActivityRFormBinding
import java.io.File
import android.graphics.Bitmap





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
    lateinit var blinking: Animation







    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Init()
        InitRecycler(mBind.root)


        vm.Cargar()
        mBind.unaFormularioNombre.text = intent.getStringExtra(BIN.EXTRA_NOMBRE)



        vm._listado.observe(this, Observer {

            if (it.isNotEmpty()) {
                val arrayList = ArrayList<Pregunta>()
                for (i in it) {
                    arrayList.add(i)
                }
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


        vm.estado.observe(this, Observer {
            if (it == Estado.Idle) {
                mBind.enviadorespLoadingbar.visibility = View.INVISIBLE
            } else {
                mBind.enviadorespLoadingbar.visibility = View.VISIBLE
            }
        })





    }



    fun EnviarRespuestaClick(view: View) {

        if (vm.isNotIdle()){Toast.makeText(
            view.context,
            resources.getString(R.string.en_progreso),
            Toast.LENGTH_SHORT
        ).show();return}

        if (BIN.TengoPermisoLocalizacion(this)) {
            if(mAdapterEvidencia.itemCount==0){
                Toast.makeText(this,getString(R.string.sin_evidencias),Toast.LENGTH_LONG).show()
            }else{
                EnviarRespuesta()
            }
        } else {
            BIN.PedirLocationAppPermission(this)
        }




    }

    private fun EnviarRespuesta() {


            if (BIN.TengoInternet(this)) {




                    vm.EnviarRespuesta(
                        this,
                        mAdapter.listResp,
                        mAdapter.listPreg,
                        mAdapterEvidencia.get_Bitmaps(),
                        mAdapterEquip.get_only_chequed(),
                        {
                            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                        },
                        {
                            Toast.makeText(
                                this,
                                resources.getString(R.string.respuesta_ok),
                                Toast.LENGTH_SHORT
                            ).show()
                            setResult(Activity.RESULT_OK)
                            finish()
                        },
                        {
                            Toast.makeText(
                                this,
                                resources.getString(R.string.respuesta_ko),
                                Toast.LENGTH_SHORT
                            ).show()
                            setResult(Activity.RESULT_CANCELED)
                            finish()
                        })


            }

        else{
                Toast.makeText(
                    this,
                    resources.getString(R.string.sin_conexion_intern),
                    Toast.LENGTH_SHORT
                ).show()

        }




    }



   override fun onRequestPermissionsResult(
       requestCode: Int,
       permissions: Array<String?>,
       grantResults: IntArray
   ) {
        if (requestCode == BIN.REQUEST_MY_PERMISSIONS_LOCATION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                EnviarRespuesta()
            } else {
                Toast.makeText(
                    getThis(),
                    resources.getString(R.string.location_denied),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }else if (requestCode ==BIN.REQUEST_MY_PERMISSIONS_READ){
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                AgregarEvidencia()
            }
        }
    }





    private fun Init() {
        mBind = ActivityRFormBinding.inflate(layoutInflater)
        mBind.included.toolbar.title = resources.getString(R.string.titlerespondiendoformulario)
        setContentView(mBind.root)
        vm = RFormVM()
        vm.id = intent.getStringExtra(BIN.EXTRA_ID)!!

        blinking = AlphaAnimation(0.0f, 1.0f)
        blinking.duration = 900 //You can manage the blinking time with this parameter
        blinking.startOffset = 20
        blinking.repeatMode = Animation.REVERSE
        blinking.repeatCount = Animation.INFINITE
        mBind.buSubirEvidencia.startAnimation(blinking)

    }

    private fun InitRecycler(root: View) {

        mRecyclerView = root.findViewById(R.id.recycler)
        val llm = LinearLayoutManager(root.context);
        llm.orientation = LinearLayoutManager.VERTICAL;
        mRecyclerView.layoutManager = llm;
        mAdapter = RespuestaAdapter(root.context) { foto, pos -> /*LoadFoto(foto, pos)  LA FUNCION LoadFoto está comentada */ }
        mRecyclerView.adapter = mAdapter;


        mRecyclerViewEvidencias = root.findViewById(R.id.recycler_evidencias)
        val llm2 = LinearLayoutManager(root.context);
        //val gl = GridLayoutManager(getThis(), 2)
        llm2.orientation = LinearLayoutManager.HORIZONTAL;

        mRecyclerViewEvidencias.layoutManager = llm2;
        mAdapterEvidencia = EvidenciaAdapter(root.context) { imageView, pos->

            val imagePopup: ImagePopup = ImagePopup(imageView.context);
            imagePopup.backgroundColor = Color.TRANSPARENT;  // Optional
            imagePopup.isFullScreen = true; // Optional
            //imagePopup.setHideCloseIcon(true);  // Optional
            imagePopup.isImageOnClickClose = true;  // Optional
            imagePopup.initiatePopup(imageView.drawable);
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
        AgregarEvidencia()
    }
    fun AgregarEvidencia() {

            val intent = Intent()
            intent.type = "image/*"
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.action = Intent.ACTION_GET_CONTENT;
            startActivityForResult(Intent.createChooser(intent, "Seleccionar"), PICK_IMAGE_MULTIPLE);


    }




    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        // When an Image is picked

        if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == Activity.RESULT_OK
                && null != intent
        ) {
            if (intent.clipData != null) {
                val count = intent.clipData!!.itemCount
                for (i in 0 until count) {
                    getPathFromURI(intent.clipData!!.getItemAt(i).uri)
                }
            } else if (intent.data != null) {
                getPathFromURI(intent.data!!)
            }
            else{p("ERROR DATA and CLIPDATA NULL")}
        }else if(requestCode == PICK_IMAGE_MULTIPLE && resultCode != Activity.RESULT_OK){p("RESULT = $resultCode (NO OK)" )}
        else if(requestCode == PICK_IMAGE_MULTIPLE && intent ==null){p("DATA = NULL")}






        //AQUI SE PROBARA OTRA FUNCION SI FUE MAL
        Log.e("Metodo2","Metodo2")
        if(mAdapterEvidencia.itemCount==0 &&requestCode == PICK_IMAGE_MULTIPLE && resultCode == Activity.RESULT_OK && null != intent)
        {

            if (intent.clipData != null) {
                val count = intent.clipData!!.itemCount
                for (i in 0 until count) {
                    mAdapterEvidencia.addImages(MediaStore.Images.Media.getBitmap(this.contentResolver, intent.clipData!!.getItemAt(i).uri),true)
                }
            } else if (intent.data != null) {
                mAdapterEvidencia.addImages(MediaStore.Images.Media.getBitmap(this.contentResolver, intent.data),true)
            }

        }else if (mAdapterEvidencia.itemCount!=0){Log.e("Metodo2","No necesita ejecutarse")}




        mAdapterEvidencia.notifyDataSetChanged()
        if (mAdapterEvidencia.itemCount==0){mBind.buSubirEvidencia.startAnimation(blinking)}
        else{mBind.buSubirEvidencia.animation=null}


    }

    private fun p(str: String) {
        Toast.makeText(this,str,Toast.LENGTH_LONG).show()
    }

    private fun getPathFromURI(uri: Uri) {

        var log_path=0
        val path: String = uri.path!! // uri = any content Uri
        Log.e("uri.path",path)

        val databaseUri: Uri
        val selection: String?
        val selectionArgs: Array<String>?
        if (path.contains("/document/image:")) { // files selected from "Documents"
            log_path=1
            databaseUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            selection = "_id=?"
            selectionArgs = arrayOf(DocumentsContract.getDocumentId(uri).split(":")[1])
        } else { // files selected from all other sources, especially on Samsung devices
            log_path=2
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


                val imgFile = File(imagePath)
                if (imgFile.exists()) {
                    val myBitmap = BitmapFactory.decodeFile(imgFile.absolutePath)
                    if (myBitmap!=null){ mAdapterEvidencia.addImages(myBitmap, true)}
                    else{p("Decoded Bitmap Null")}
                }else {p("Archivo no Existe")}


            }else{
                p("$log_path .Cursor don't have first")
            }
            cursor.close()
        } catch (e: Exception) {
            p("$log_path .Catch ${e.message}")
            Log.e("image", e.message, e)
        }
    }



    fun QuitarEvidenciaClick(view: View) {
        mAdapterEvidencia.clearImages();
        mAdapterEvidencia.notifyDataSetChanged();
        mBind.buSubirEvidencia.animation=blinking
    }
    fun CancelarClick(view: View) {finish()}





    fun LoadFoto(i: ImageView, p: Int) {//  Actualmente no usado
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


}