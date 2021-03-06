package com.ello.kotlinseguridad.simple

import android.R
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.*
import android.graphics.pdf.PdfDocument
import android.graphics.pdf.PdfDocument.PageInfo
import android.util.Log
import androidx.core.content.FileProvider
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ello.kotlinseguridad.Estado
import com.ello.kotlinseguridad.bin.*
import com.ello.kotlinseguridad.parseobj.Actividad
import com.ello.kotlinseguridad.parseobj.Pregunta
import com.ello.kotlinseguridad.parseobj.Respuesta
import com.ello.kotlinseguridad.parseobj.Usuario
import com.ello.twelveseconds.Formulario
import com.parse.ParseObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.apache.poi.ss.formula.Formula
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.ParseException
import java.util.*
import kotlin.collections.ArrayList


class SDescReportesVM(val context: Context) : ViewModel() {

     var fecha_desde:Long?=null
    var fecha_hasta:Long?=null
     lateinit var maker: PDF_Maker
    var estado = MutableLiveData<Estado>()

    init {
        estado.value=Estado.Idle
    }


    fun Cargar() {

    }

/*
     fun Llenar(n: String, fg_ruta_descarga: (s:String) -> Unit,fb: (s:String) -> Unit){

         if(fecha_desde==null||fecha_hasta==null|| fecha_desde!! >fecha_hasta!!){

             fb("Rectifique las fechas")
             return
         }

         if(!BIN.TengoInternet(context)){
             fb("Necesita Internet")
             return
         }
         estado.value= Estado.CompilingPDF
         val plantilla= PlantillaReporte("idcualquiera")

         viewModelScope.launch (Dispatchers.IO){
             val tempU=ParseObject.createWithoutData(Usuario.class_name,"h6dN0O1Zro") as Usuario
             val tempF=ParseObject.createWithoutData(Formulario.class_name,"QBplsdoBML") as Formulario
             val tempA=ParseObject.createWithoutData(Actividad.class_name,"vpuZHngpxv") as Actividad


             CRUD.CargarTodasRespuestas(tempU,tempF,tempA,{
                     respuestas->

                 Log.e("Respuestas","Se cargaron las respuestas ${respuestas.size}" )


                 for (r in respuestas){


                     //A??adir preguntas respuestas
                     val pregunta:Pregunta= r.ref_pregunta as Pregunta
                     var num=1
                     pregunta.numero?.let { num=it+1 }
                     val str_preg= num.toString() + "- " + pregunta.nombre
                     r.respuesta?.let { plantilla.addPregResp(str_preg, it,r.checked) }
                     //A??adir preguntas respuestas


                     if (r.firs_of_list){

                    val esta_act= r.ref_actividad?.fetch() as Actividad
                    val esta_form=r.ref_formulario?.fetch() as Formulario
                    val esta_usu=r.ref_usuario?.fetch() as Usuario





                     plantilla.setDocId(r.objectId)
                     plantilla.setUpdt(Snippetk.LeerFechaR(r.fecha))


                         esta_act.nombre?.let { plantilla.setActName(it) }
                         esta_act.desc?.let { plantilla.setActDesc(it) }
                       plantilla.setActDate(Snippetk.LeerFechaR(esta_act.fecha))
                         esta_form.nombre?.let { plantilla.setFormName(it) }
                         esta_form.tipo?.let { plantilla.setFormType(it) }

                         esta_act.sitio?.let { plantilla.setActSitio(it) }
                         esta_act.ubicacion?.let {

                             if(it.length>34){
                                 val pos1=it.indexOf("Lat:",0,true)
                                 val pos2=it.indexOf("Lon:",5,true)
                                 plantilla.setActUbicac(it.substring(pos1+4,pos1+4+7)+"; "+it.substring(pos2+4,pos2+4+7))
                                 Log.e("pos1 y pos2","$pos1 y $pos2")
                                 Log.e("substr1 y substr2","${it.substring(pos1,pos1+13)} y ${it.substring(pos2,pos2+13)}")
                             }
                             else
                             {
                                 plantilla.setActUbicac(it)
                             }




                         }
                     plantilla.setFormCantPreg(respuestas.size.toString())

                         if(esta_usu.nom_apell!=null&&esta_usu.apell!=null){plantilla.setPersName(esta_usu.nom_apell+" "+esta_usu.apell)}
                        else{esta_usu.nom_apell?.let { plantilla.setPersName(it) }}

                         esta_usu.cedula?.let { plantilla.setPersCedul(it) }
                         esta_usu.direccion?.let { plantilla.setPersDirecc(it) }
                         esta_usu.telefono?.let { plantilla.setPersTelef(it) }

                         r.equipos?.let {
                             var str=it.replace("*",", ")
                             if (str[str.length-2] ==','){
                                 Log.e("Equipos","Al final existe una coma");
                                 str=str.substring(0,str.length-2)
                             }
                             plantilla.setEquipami(str)}

                         plantilla.addImage(r.e0)
                         plantilla.addImage(r.e1)
                         plantilla.addImage(r.e2)
                         plantilla.addImage(r.e3)
                         plantilla.addImage(r.e4)
                         plantilla.addImage(r.e5)
                         plantilla.addImage(r.e6)
                         plantilla.addImage(r.e7)
                         plantilla.addImage(r.e8)


                     }
                 }

                 plantilla.PoblarArchivo(context.filesDir,context.resources)
                 val ruta=plantilla.ObtenerRutaPlantillaSalida()

                 if (ruta==null){
                     fb("Ha ocurrido un error")
                 }else
                 {
                     plantilla.AbrirXLS(context)
                     fg_ruta_descarga(ruta)
                 }





             },{})//Dandole usuario formulario y activida


         }



     }
*/
    /*
     fun Exportar(n: String, fg_ruta_descarga: (s:String) -> Unit,fb: (s:String) -> Unit) {


        if (n.isNullOrEmpty()||n.length>14){
            fb("Falta nombre del archivo")
            return
        }

        if(fecha_desde==null||fecha_hasta==null|| fecha_desde!! >fecha_hasta!!){

            fb("Rectifique las fechas")
            return
        }

        if(!BIN.TengoInternet(context)){
            fb("Necesita Internet")
            return
        }

         estado.value= Estado.CompilingPDF
         var nombre=n
         if (nombre.endsWith(".pdf")){nombre=n.substring(0,n.length-4)}


        maker= PDF_Maker(context,nombre)



         try {

        viewModelScope.launch (Dispatchers.IO){
            CRUD.CargarTodasRespuestasRangeFechas(fecha_desde!!,fecha_hasta!!,{respuestas->


                Log.e("RespuestasRangeFechas","Total de respuestas:${respuestas.size}")

                if (respuestas.isNullOrEmpty()) {
                    estado.value=Estado.Idle
                    fb("No se encontraron reportes para esas fechas")
                }
                else{

                    var code=""//Codigo formado IDusuario + IDactividad
                    val respuestas_un_informe=ArrayList<Respuesta>()

                    for (r in respuestas){
                        val co=r.ref_usuario!!.objectId+r.ref_actividad!!.objectId
                        if (co!=code){
                            maker.ImprimirPagina(respuestas_un_informe)
                            respuestas_un_informe.clear()
                            respuestas_un_informe.add(r)
                            code=co
                        }else
                        {
                            respuestas_un_informe.add(r)
                        }

                    }

                    maker.ImprimirPagina(respuestas_un_informe)
                    maker.SalvarPDF()
                    maker.AbrirPDF()
                    fg_ruta_descarga(maker.path_name)
                    estado.value=Estado.Idle




                }




            },{})
        }
         }catch (e:ParseException){fb(e.toString())}




    }
*/


/*
    fun createpdf(path_name: String, titulo: String, subtitulo: String, ciudad: String, lugar: String) {
        val bounds = Rect()
        val pageWidth = 300;
        val pageheight = 470;
        val pathHeight = 2F
        val myPdfDocument = PdfDocument()
        val paint = Paint()
        val paint2 = Paint()
        val path = Path()
        val myPageInfo = PageInfo.Builder(pageWidth, pageheight, 1).create()
        val documentPage = myPdfDocument.startPage(myPageInfo)
        val canvas: Canvas = documentPage.canvas
        var y = 25F // x = 10,
        var x = 10F


        y=EscribirCentro(titulo,paint,bounds,y,canvas)
        y=EscribirCentro(subtitulo,paint,bounds,y,canvas)

        y=BajarLinea(x,y,paint,canvas)
        LineaHorizontal(path,pageWidth,pathHeight,paint2,x,y,canvas)
        y=BajarLinea(x,y,paint,canvas)
        y=Escribir(lugar,y,paint,canvas)
        y=Escribir(ciudad,y,paint,canvas)
        y=BajarLinea(x,y,paint,canvas)
        LineaHorizontal(path,pageWidth,pathHeight,paint2,x,y,canvas)
        y=BajarLinea(x,y,paint,canvas)

        y=ImprimirFoto(R.drawable.ic_menu_camera,context,100,50,canvas,x,y,paint)

        myPdfDocument.finishPage(documentPage)
        val file = File(path_name)
        try {
            myPdfDocument.writeTo(FileOutputStream(file))
        } catch (e: IOException) {
            e.printStackTrace()
        }
        myPdfDocument.close()
        viewPdfFile(file)

    }

    private fun ImprimirFoto(resourseId: Int, c: Context, ancho: Int, largo: Int, canvas: Canvas, x: Float,y: Float,paint: Paint): Float {
        val res: Resources = c.resources
        val bitmap = BitmapFactory.decodeResource(res, R.drawable.ic_menu_camera)
        val b = Bitmap.createScaledBitmap(bitmap, ancho, largo, false)
        canvas.drawBitmap(b, x, y, paint)
        val distancia =y+ 25
        //canvas.drawText("Nombre de App", 120F, y, paint)
        return distancia
    }

    private fun Escribir(str: String, y: Float, paint: Paint, canvas: Canvas):Float {
        val distancia=y+ (paint.descent() - paint.ascent()).toInt()
        val x = 10F
        canvas.drawText(str, x, y, paint)
        return distancia

    }

    private fun EscribirCentro(str: String, paint: Paint, bounds:Rect, y:Float, canvas: Canvas):Float {
        paint.getTextBounds(str, 0, str.length, bounds)
        val x = (canvas.width / 2 - bounds.width() / 2).toFloat()
        val distancia = y+ (paint.descent() - paint.ascent()).toInt()
        canvas.drawText(str, x, y, paint)
        return distancia
    }

    private fun BajarLinea(x: Float,y: Float,paint: Paint,canvas: Canvas):Float {
        val distanciaY = y+(paint.descent() - paint.ascent()).toInt()
        canvas.drawText("", x, y, paint)
        return distanciaY;
    }

    private fun LineaHorizontal(path: Path,pageWidth:Int,pathHeight:Float,paint2: Paint,x:Float,y:Float,canvas: Canvas) {
        path.lineTo(pageWidth.toFloat(), pathHeight)
        paint2.color = Color.GRAY
        paint2.style = Paint.Style.STROKE
        path.moveTo(x, y)
        canvas.drawLine(0F, y, pageWidth.toFloat(), y, paint2)
    }



*/
fun viewPdfFile(file: File) {

    val intent = Intent(Intent.ACTION_VIEW)
    val u= FileProvider.getUriForFile(context, context.applicationContext.packageName.toString() + ".provider", file)
    intent.setDataAndType(u, "application/pdf")
    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
    context.startActivity(intent)
}




    fun PonerDesde(day: Int, month: Int, year: Int) {

        val c= Calendar.getInstance()
        c.set(Calendar.HOUR_OF_DAY,0)
        c.set(Calendar.MINUTE,0)

        c.set(Calendar.YEAR,year!!)
        c.set(Calendar.MONTH,month!!)
        c.set(Calendar.DAY_OF_MONTH,day!!)
        fecha_desde=c.timeInMillis
    }

    fun PonerHasta(day: Int, month: Int, year: Int) {
        val c= Calendar.getInstance()
        c.set(Calendar.HOUR_OF_DAY,23)
        c.set(Calendar.MINUTE,59)

        c.set(Calendar.YEAR,year!!)
        c.set(Calendar.MONTH,month!!)
        c.set(Calendar.DAY_OF_MONTH,day!!)
        fecha_hasta=c.timeInMillis
    }
}