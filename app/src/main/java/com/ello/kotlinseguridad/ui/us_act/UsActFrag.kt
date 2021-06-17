package com.ello.kotlinseguridad.ui.us_act

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
import com.ello.kotlinseguridad.Simple.SActivPlus

class UsActFrag : Fragment() {

    private lateinit var vm: UsActVM
    private lateinit var mAdapter: ActividadAdapter
    private lateinit var mRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vm =
            ViewModelProvider(requireActivity()).get(UsActVM::class.java)
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

    private fun InitRecycler(root:View) {
        mRecyclerView=root.findViewById(R.id.recycler)
        val llm = LinearLayoutManager(root.context);
        llm.orientation = LinearLayoutManager.VERTICAL;
        mRecyclerView.layoutManager = llm;


        mAdapter= ActividadAdapter(root.context){activity?.startActivity(Intent(activity,
            SActiv::class.java).putExtra("id",it))}
        mRecyclerView.adapter=mAdapter;


        if (!BIN.ES_ADMIN()){
            BIN.PUEDE_ACTIVIDADES {
                mAdapter.iClick={activity?.startActivity(Intent(activity, SActivPlus::class.java).putExtra("id",it))}
                Log.e("Actividades","MODO ADMIN PARA LAS ACTIVIDADES")
            }
        }





    }






    }
