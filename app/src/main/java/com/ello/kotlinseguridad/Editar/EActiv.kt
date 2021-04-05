package com.ello.kotlinseguridad.Editar

import android.app.Activity
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ello.kotlinseguridad.Adapter.CheckAdapter
import com.ello.kotlinseguridad.R
import com.ello.kotlinseguridad.databinding.ActivityEActBinding
import com.ello.kotlinseguridad.drawer1
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class EActiv : AppCompatActivity() {

    private lateinit var mBind: ActivityEActBinding
    private lateinit var vm: EActivVM
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter:CheckAdapter

    var mObjId_toEdit:String? = ""

    companion object{
        var EXTRA_OBJ_ID="extra_editar"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        Init()
        InitRecycler()
        CargarUsuariosQueRealizanLaActividad();


    }

    private fun Init() {
        mBind= ActivityEActBinding.inflate(layoutInflater)
        vm= EActivVM(this)

        setContentView(mBind.root)
        if (intent.hasExtra(EXTRA_OBJ_ID)) { mObjId_toEdit=intent.getStringExtra(EXTRA_OBJ_ID) }
        if (mObjId_toEdit.isNullOrEmpty()){mBind.included.toolbar.title = resources.getString(R.string.titleCactiv)}
        else{ mBind.included.toolbar.title = resources.getString(R.string.titleEactiv) }

    }

    private fun CargarUsuariosQueRealizanLaActividad() {
        vm.CargarTodosUsuariosLocal({
            mAdapter.setListado(it)
        },{});
    }


    fun AcpetarClick(view: View) {


            with(mBind){



                if (mObjId_toEdit.isNullOrEmpty())//Crear
                {
                    vm.CrearActividad(mAdapter.checked_list,editactivNombre.text.toString(),mBind.editactivDescrip.text.toString(),Calendar.getInstance().time.time,
                        {Toast.makeText(getThis(),it,Toast.LENGTH_SHORT).show();},
                        {Toast.makeText(getThis(),resources.getString(R.string.crearActOk),Toast.LENGTH_SHORT).show();setResult(drawer1.RES_OK_CREAR_ACTIVIDAD);finish()},
                        {Toast.makeText(getThis(),resources.getString(R.string.editarActNotOk),Toast.LENGTH_SHORT).show()}

                        )
                    Log.e("Crear","Crear")

                }else //Editar
                {
                    Log.e("Editar","Editar")
                    vm.EditarActividad(mObjId_toEdit!!,mAdapter.checked_list,editactivNombre.text.toString(),mBind.editactivDescrip.text.toString(),Calendar.getInstance().time.time,
                        {Toast.makeText(getThis(),it,Toast.LENGTH_SHORT).show();},
                        {Toast.makeText(getThis(),resources.getString(R.string.editarActOk),Toast.LENGTH_SHORT).show();setResult(drawer1.RES_OK_CREAR_ACTIVIDAD);finish()},
                        {Toast.makeText(getThis(),resources.getString(R.string.editarActNotOk),Toast.LENGTH_SHORT).show()}

                    )
                }

            }

    }


    fun getThis(): Context {return this}

    private fun InitRecycler() {
        mRecyclerView=mBind.recyclerChecks
        val llm = LinearLayoutManager(this);
        llm.orientation = LinearLayoutManager.VERTICAL;
        mRecyclerView.layoutManager = llm;
        mAdapter= CheckAdapter(this,{}) ;
        mRecyclerView.adapter=mAdapter;

    }

    fun MostrarDatePicker(view: View) {

        val newFragment = DatePickerFragment()
        newFragment.textView=mBind.datePick
        newFragment.vm=vm
        newFragment.show(supportFragmentManager, "datePicker")

    }


    class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {
        lateinit var textView: TextView;
        lateinit var vm: EActivVM

        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            // Use the current date as the default date in the picker
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)


            // Create a new instance of DatePickerDialog and return it
            Log.e("E","onCreateDialog Date")
            return DatePickerDialog(requireActivity(), this, year, month, day)
        }

        override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
            // Do something with the date chosen by the user
            Log.e("E","onDateSet Date")
            textView.text=day.toString()+"/"+(month+1).toString()+"/"+year.toString()
            vm.year=year
            vm.month=month
            vm.day=day



        }
    }



















}