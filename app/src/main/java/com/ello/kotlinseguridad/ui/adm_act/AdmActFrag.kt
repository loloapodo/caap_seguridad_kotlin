package com.ello.kotlinseguridad.ui.adm_act

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ello.kotlinseguridad.Adapter.ActividadAdapter
import com.ello.kotlinseguridad.BIN.BIN
import com.ello.kotlinseguridad.R
import com.ello.kotlinseguridad.Simple.SActiv

class AdmActFrag : Fragment() {

    private lateinit var vm: AdmActVM
    private lateinit var mAdapter: ActividadAdapter
    private lateinit var mRecyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        vm = ViewModelProvider(requireActivity()).get(AdmActVM::class.java)
        val root = inflater.inflate(R.layout.frag_adm_act, container, false)


        InitRecycler(root)

        vm._listado.observe(viewLifecycleOwner, Observer {
                Log.e("observer done","observerd done");
                mAdapter.list=it
                mAdapter.notifyDataSetChanged()
            })
        vm.Cargar()

        return root
    }

    public fun Cargar(){ vm.Cargar()}


    private fun InitRecycler(root:View) {
        mRecyclerView=root.findViewById(R.id.recycler)
        val llm = LinearLayoutManager(root.context);
        llm.orientation = LinearLayoutManager.VERTICAL;
        mRecyclerView.layoutManager = llm;
        mAdapter= ActividadAdapter(root.context){startActivity(Intent(activity,
                SActiv::class.java).putExtra("id",it))}
        mRecyclerView.adapter=mAdapter;
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode==Activity.RESULT_OK){
            vm.CargarDelServidor()
        }
        super.onActivityResult(requestCode, resultCode, data)


    }


}