package com.ello.kotlinseguridad.simple

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ello.kotlinseguridad.*
import com.ello.kotlinseguridad.adapter.XLS_Adapter
import com.ello.kotlinseguridad.databinding.RespxfechaBinding

class RespuestasPorFecha : AppCompatActivity() {

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: XLS_Adapter
    private lateinit var mBind: RespxfechaBinding
    private lateinit var vm:RespuestasPorFechaVM
     private var desde:Long=0L;
     private var hasta:Long=0L;
    private var filtro:String? = null;




    companion object{
        val STARTDATE="startdate"
        val ENDDATE="enddate"
        val FILTRO_BUSQ="filtro"
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        Init()
        InitRecycler()
        CreateMyOptionMenu()

        //la validacion fue echa antes de entrar a esta clase
        vm.CargarRespuestas(desde,hasta,{
            mAdapter.addResp(it)
            mAdapter.notifyDataSetChanged()

        },{

            Toast.makeText(getThis(),it,Toast.LENGTH_SHORT).show()
            finish()
        })


    }

    private fun Init() {
       vm= RespuestasPorFechaVM(filesDir,resources,this)

        Log.e("getLongExtra desde",intent.getLongExtra(STARTDATE, 0L).toString())
       desde= intent.getLongExtra(STARTDATE, 0L)
        hasta=intent.getLongExtra(ENDDATE,0L)
        filtro=intent.getStringExtra(FILTRO_BUSQ)

        Log.e("desde obtenido",desde.toString())


        mBind= RespxfechaBinding.inflate(layoutInflater)
        mBind.included.toolbar.title = resources.getString(R.string.informes)
        setContentView(mBind.root)

    }

    private fun InitRecycler() {

        mRecyclerView=mBind.recycler
        val llm = LinearLayoutManager(this);
        llm.orientation = LinearLayoutManager.VERTICAL;
        mRecyclerView.layoutManager = llm;
        mAdapter= XLS_Adapter(this) { i ->

            Log.e("EL id respuesta es ", i)

            Toast.makeText(getThis(),"Descargando datos ... ",Toast.LENGTH_SHORT).show()
            vm.BuscaroLLenarPlantilla(i,
                {Toast.makeText(getThis(),"Guardado en Descargas",Toast.LENGTH_SHORT).show()},
                {Toast.makeText(this,it,Toast.LENGTH_SHORT).show()})




        };
        mRecyclerView.adapter=mAdapter;

    }

    private fun CreateMyOptionMenu() {


    }



    private fun getThis(): Context {
        return this;
    }

    fun AtrasClick(view: View) {
        finish()
    }


}




