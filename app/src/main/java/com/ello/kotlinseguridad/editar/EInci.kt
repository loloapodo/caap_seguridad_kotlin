package com.ello.kotlinseguridad.editar

import android.app.Activity
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.text.format.DateFormat
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ceylonlabs.imageviewpopup.ImagePopup
import com.ello.kotlinseguridad.adapter.CheckAdapter
import com.ello.kotlinseguridad.bin.BIN
import com.ello.kotlinseguridad.R
import com.ello.kotlinseguridad.adapter.EvidenciaAdapter
import com.ello.kotlinseguridad.databinding.ActivityEActBinding
import com.ello.kotlinseguridad.databinding.ActivityEIncidenteBinding
import com.ello.kotlinseguridad.databinding.ActivityERolBinding
import com.ello.kotlinseguridad.drawer1
import com.parse.ParseGeoPoint
import java.io.File
import java.util.*
import kotlin.collections.ArrayList


class EInci : AppCompatActivity() {


    private lateinit var vm: EInciVM
    private lateinit var mBind: ActivityEIncidenteBinding
    private lateinit var mAdapterEvidencia: EvidenciaAdapter
    private lateinit var mRecyclerViewEvidencias: RecyclerView
    var mObjId_toEdit:String? = ""
    lateinit var imagePath: String
    var PICK_IMAGE_MULTIPLE = 1
    companion object{
        var EXTRA_OBJ_ID="extra_editar"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Init()






    }

    private fun Init() {
        vm= EInciVM()
        mBind= ActivityEIncidenteBinding.inflate(layoutInflater)
        setContentView(mBind.root)

        mRecyclerViewEvidencias = mBind.root.findViewById(R.id.recycler_evidencias)
        val llm2 = LinearLayoutManager(mBind.root.context);
        //val gl = GridLayoutManager(getThis(), 2)
        llm2.orientation = LinearLayoutManager.HORIZONTAL;

        mRecyclerViewEvidencias.layoutManager = llm2;
        mAdapterEvidencia = EvidenciaAdapter(mBind.root.context) { imageView, pos->

            val imagePopup: ImagePopup = ImagePopup(imageView.context);
            imagePopup.backgroundColor = Color.TRANSPARENT;  // Optional
            imagePopup.isFullScreen = true; // Optional
            //imagePopup.setHideCloseIcon(true);  // Optional
            imagePopup.isImageOnClickClose = true;  // Optional
            imagePopup.initiatePopup(imageView.drawable);
            imagePopup.viewPopup();

        }
        mRecyclerViewEvidencias.adapter = mAdapterEvidencia;


    }







    fun AcpetarClick(view: View) {

        if (vm.CamposEstanMal(mBind.editincidenteNombre,mBind.editincidentDescripcion)){
            Toast.makeText(
            getThis(),
            resources.getString(R.string.toast_campos_mal),
            Toast.LENGTH_SHORT
        ).show();
            return}

        if(mAdapterEvidencia.itemCount==0){
            Toast.makeText(this,getString(R.string.sin_evidencias),Toast.LENGTH_LONG).show()
        }else{
            EnviarIncidencia()
        }


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

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
       if (requestCode ==BIN.REQUEST_MY_PERMISSIONS_READ){
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                AgregarEvidencia()
            }
        }
    }

    private fun EnviarIncidencia() {
        if (BIN.TengoInternet(this)) {




            vm.EnviarIncidencia(
                mBind.editincidenteNombre.text.toString(),
                mBind.editincidentDescripcion.text.toString(),
                mAdapterEvidencia.get_Bitmaps(),
                {
                    Toast.makeText(
                        this,
                        resources.getString(R.string.incidencia_enviada),
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

    private fun InitRecycler() {

    }




    fun CancelarClick(view: View) {finish()}

    private fun p(str: String) {
        //Toast.makeText(this,str,Toast.LENGTH_LONG).show()
    }
}