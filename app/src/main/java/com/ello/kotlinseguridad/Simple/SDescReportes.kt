package com.ello.kotlinseguridad.Simple


import android.content.Context
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.graphics.pdf.PdfDocument.PageInfo
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.ello.kotlinseguridad.BIN.BIN
import com.ello.kotlinseguridad.databinding.ActivitySReportesBinding
import java.io.File
import java.io.FileOutputStream
import java.io.FileWriter
import java.io.IOException


class SDescReportes : AppCompatActivity() {


    private lateinit var mBind: ActivitySReportesBinding
    private var vm: SDescReportesVM = SDescReportesVM()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Init()



        vm.Cargar({}, {})


    }

    private fun Init() {

        mBind = ActivitySReportesBinding.inflate(layoutInflater)
        mBind.included.toolbar.title ="Reporte de Tareas"
        setContentView(mBind.root)

        actionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayHomeAsUpEnabled(true);


    }




    private fun getThis(): Context {
        return this;
    }

    fun CancelarClick(view: View) {
        finish()
    }

    fun DescargarClick(view: View) {

        if (BIN.TengoPermisoREAD(this))
        {
            Toast.makeText(this, "Descargando", Toast.LENGTH_SHORT).show()
            strringtopdf2("El candado de las 7")
        }
        else
        {
            BIN.PedirREADPermission(this)
        }



    }

    fun MostrarDatePickerdesde(view: View) {}
    fun MostrarDatePickerhasta(view: View) {}














    fun strringtopdf2(str: String){

        try {
            val root = File(Environment.getExternalStorageDirectory(), "Seguridad")
            if (!root.exists()) {
                root.mkdirs()
            }
            val gpxfile = File(root, "test.txt")
            val writer = FileWriter(gpxfile)


            writer.append("")









            writer.flush()
            writer.close()

        } catch (e: IOException) {
            e.printStackTrace()
        }




    }


    fun stringtopdf(data: String?) {

        val extstoragedir: String = Environment.getExternalStorageDirectory().toString()
        Log.e("download path", extstoragedir)
        val fol = File(extstoragedir)

        val path = File(fol, "Seguridad")
        if (!path.exists()) {
            val bool = path.mkdirs()
            if (bool){Log.e("download path", "creado") }else{Log.e("download path", "no creado")}
        }
        try {
            val file=File(path.toString() + "/jorgito.pdf")


            Log.e("download path", file.absolutePath)
            file.createNewFile()
            val fOut = FileOutputStream(file)
            val document = PdfDocument()
            val pageInfo = PageInfo.Builder(100, 100, 1).create()
            val page = document.startPage(pageInfo)
            val canvas = page.canvas
            val paint = Paint()
            canvas.drawText(data!!, 10F, 10F, paint)
            document.finishPage(page)
            document.writeTo(fOut)
            document.close()
        } catch (e: IOException) {
            Log.e("error", e.localizedMessage)
        }

    }









}








