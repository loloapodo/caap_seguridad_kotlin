package com.ello.kotlinseguridad.editar

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ello.kotlinseguridad.adapter.CheckAdapter
import com.ello.kotlinseguridad.bin.BIN
import com.ello.kotlinseguridad.R
import com.ello.kotlinseguridad.databinding.ActivityEActBinding
import com.ello.kotlinseguridad.drawer1
import com.parse.ParseGeoPoint
import java.util.*
import kotlin.collections.ArrayList


class EActiv : AppCompatActivity() {

    private lateinit var mBind: ActivityEActBinding
    private lateinit var vm: EActivVM
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter:CheckAdapter
    private lateinit var mUbic:ParseGeoPoint



    var mObjId_toEdit:String? = ""

    companion object{
        var EXTRA_OBJ_ID="extra_editar"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        Init()
        InitSpinner()
        InitRecycler()
        CargarUsuariosQueRealizanLaActividad();
        vm.CargarFormulariosdelSpinner()



        vm.formularioList.observe(this, androidx.lifecycle.Observer {

            val nombres = ArrayList<String>()
            nombres.add(BIN.EMPTY_SPINNER_ANEXAR_FORMULARIO)

            Log.e("3333", "333")
            if (it != null) {
                Log.e("3331", "333")
                for (i in it) {
                    nombres.add(i.nombre!!);Log.e("3335", "333")
                }


            }


            val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item,
                nombres
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            mBind.spinnerAnexarFormulario.adapter = adapter


        })


        mBind.spinnerAnexarFormulario.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View,
                position: Int,
                id: Long
            ) {
                Log.e("onItemSelected", "$position")
                if (position>0){
                    mBind.textviewTipoFormulario.text = vm.formularioList.value?.get(position - 1)!!.tipo
                    vm.ref_formulario_position=position-1
                }
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // your code here
            }
        }






    }

    private fun InitSpinner() {

        val arr1= ArrayList<String>()
        arr1.add(BIN.EMPTY_SPINNER_ANEXAR_FORMULARIO)

        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_item,
            arr1
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        mBind.spinnerAnexarFormulario.adapter = adapter




    }

    private fun Init() {
        mBind= ActivityEActBinding.inflate(layoutInflater)
        vm= EActivVM(this)
        setContentView(mBind.root)





        if (intent.hasExtra(EXTRA_OBJ_ID)) { mObjId_toEdit=intent.getStringExtra(EXTRA_OBJ_ID) }
        if (mObjId_toEdit.isNullOrEmpty()){mBind.included.toolbar.title = resources.getString(R.string.titleCactiv)}
        else{ mBind.included.toolbar.title = resources.getString(R.string.titleEactiv) }



        if (BIN.TengoPermisoLocalizacion(this)) {CargarYMostrarUbicacion()}
        else{ BIN.PedirLocationAppPermission(this) }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        if (requestCode == BIN.REQUEST_MY_PERMISSIONS_LOCATION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                CargarYMostrarUbicacion()
            } else {
                Toast.makeText(
                    getThis(),
                    resources.getString(R.string.location_denied),
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    private fun CargarYMostrarUbicacion() {
       val t= Toast.makeText(getThis(), "Buscando ubicación...", Toast.LENGTH_LONG)
        t.show()

        BIN.getMyParseGeoLocation(this){ g: ParseGeoPoint ->
         t.cancel()
         Toast.makeText(
             getThis(),
             "Ubicación de la actividad    Lat:${g.latitude} Lon:${g.longitude}",
             Toast.LENGTH_SHORT
         ).show()
         mBind.edittextUbicacion.setText("Lat:${g.latitude} Lon:${g.longitude}")


            mUbic=g
            //mBind.buscarenmapabutom.visibility=View.VISIBLE

     }


    }


    private fun CargarUsuariosQueRealizanLaActividad() {
        vm.CargarTodosUsuariosLocal({
            mAdapter.setListado(it)
            mBind.eActProgressbar1.visibility = View.GONE
        }, {});
    }


    fun AcpetarClick(view: View) {


            with(mBind){

            if (mBind.spinnerAnexarFormulario.selectedItemPosition==0){Toast.makeText(
                getThis(),
                "Falta anexar Formulario",
                Toast.LENGTH_SHORT
            ).show();setResult(drawer1.RES_OK_CREAR_ACTIVIDAD);return}

                if (mObjId_toEdit.isNullOrEmpty())//Crear
                {
                    vm.CrearActividad(mAdapter.checked_list,
                        editactivNombre.text.toString(),
                        editactivDescrip.text.toString(),
                        edittextSitio.text.toString(),
                        edittextUbicacion.text.toString(),
                        textviewTipoFormulario.toString(),
                        Calendar.getInstance().time.time,
                        { Toast.makeText(getThis(), it, Toast.LENGTH_SHORT).show(); },
                        {
                            Toast.makeText(
                                getThis(),
                                resources.getString(R.string.crearActOk),
                                Toast.LENGTH_SHORT
                            ).show();setResult(drawer1.RES_OK_CREAR_ACTIVIDAD);finish()
                        },
                        {
                            Toast.makeText(
                                getThis(),
                                resources.getString(R.string.editarActNotOk),
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    )
                    Log.e("Crear", "Crear")

                }else //Editar
                {
                    Log.e("Editar", "Editar")
                    vm.EditarActividad(mObjId_toEdit!!,
                        mAdapter.checked_list,
                        editactivNombre.text.toString(),
                        editactivDescrip.text.toString(),
                        edittextSitio.text.toString(),
                        edittextUbicacion.text.toString(),
                        textviewTipoFormulario.toString(),
                        Calendar.getInstance().time.time,
                        { Toast.makeText(getThis(), it, Toast.LENGTH_SHORT).show(); },
                        {
                            Toast.makeText(
                                getThis(),
                                resources.getString(R.string.editarActOk),
                                Toast.LENGTH_SHORT
                            ).show();setResult(drawer1.RES_OK_CREAR_ACTIVIDAD);finish()
                        },
                        {
                            Toast.makeText(
                                getThis(),
                                resources.getString(R.string.editarActNotOk),
                                Toast.LENGTH_SHORT
                            ).show()
                        }

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
        mAdapter= CheckAdapter(this, {}) ;
        mRecyclerView.adapter=mAdapter;

    }

    fun MostrarDatePicker(view: View) {

        val newFragment = DatePickerFragment()
        newFragment.textView=mBind.datePick
        newFragment.vm=vm
        newFragment.show(supportFragmentManager, "datePicker")

    }

    fun MostrarTimePicker(view: View) {

        val t= DatePickerFragment.TimePickerFragment()
        t.textView=mBind.timePick
        t.vm=vm
        t.show(supportFragmentManager, "timePicker")

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
            Log.e("E", "onCreateDialog Date")
            return DatePickerDialog(requireActivity(), this, year, month, day)
        }

        override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
            // Do something with the date chosen by the user
            Log.e("E", "onDateSet Date")
            textView.text=day.toString()+"/"+(month+1).toString()+"/"+year.toString()
            vm.year=year
            vm.month=month
            vm.day=day



        }
        class  TimePickerFragment : DialogFragment(), TimePickerDialog.OnTimeSetListener {

            lateinit var textView: TextView;
            lateinit var vm: EActivVM



            override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
                // Use the current time as the default values for the picker
                val c = Calendar.getInstance()
                val hour = c.get(Calendar.HOUR_OF_DAY)
                val minute = c.get(Calendar.MINUTE)


                Log.e("E", "onCreateDialog Time")
                // Create a new instance of TimePickerDialog and return it
                return TimePickerDialog(
                    activity, this, hour, minute, DateFormat.is24HourFormat(
                        activity
                    )
                )
            }



            override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
                Log.e("E", "onTimeSet Date")
                textView.text=String.format("%02d:%02d", hourOfDay, minute)
                vm.hour=hourOfDay
                vm.minutes=minute
                // Do something with the time chosen by the user
            }
        }
    }

    fun CancelarClick(view: View) {finish()}
    fun BuscarenMapaClick(view: View) {

        val uri = java.lang.String.format(Locale.ENGLISH, "geo:%f,%f", mUbic.latitude, mUbic.longitude)
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        startActivity(intent)

    }


}