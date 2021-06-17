package com.ello.kotlinseguridad

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import com.ello.kotlinseguridad.Activ.Login.Login
import com.ello.kotlinseguridad.BIN.BIN
import com.ello.kotlinseguridad.BIN.CRUD
import com.ello.kotlinseguridad.Editar.*
import kotlinx.coroutines.*

class drawer2 : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private  var mFragVisible:Int = 0//0 Usuario, 1 Formularios, 2 Actividad


    private var REQ_CREAR_EDITAR=22;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drawer2)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)



        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.visibility=View.GONE

        fab.setOnClickListener {



            val intent=
                    when(navController.currentDestination?.id)
                    {
                        R.id.nav_empleados-> Intent(this, EUsuario::class.java)
                        R.id.nav_usuarios -> Intent(this, EUsuario::class.java)
                        R.id.nav_actividads-> Intent(this, EActiv::class.java)
                        R.id.nav_formularios-> Intent(this, EForm::class.java)
                        R.id.nav_equipamentos-> Intent(this, EEquip::class.java)
                        R.id.nav_roles-> Intent(this, ERol::class.java)

                        else->Intent(this, EUsuario::class.java)

                    }
            startActivityForResult(intent,REQ_CREAR_EDITAR)
        }





        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout2)
        val navView: NavigationView = findViewById(R.id.nav_view2)



        val nav_Menu=navView.menu
        if (!BIN.ES_ADMIN()){

            nav_Menu.findItem(R.id.nav_formularios).isVisible = false
            nav_Menu.findItem(R.id.nav_empleados).isVisible = false
            nav_Menu.findItem(R.id.nav_actividads).isVisible = false
            nav_Menu.findItem(R.id.nav_equipamentos).isVisible = false


            lifecycleScope.launch {

    while (!BIN.PUEDE_PEDIR_LISTO()){delay(900L)}
                Log.e("YA PUEDE PEDIR","FINISH DELAY")

    BIN.PUEDE_FORMULARIOS   {nav_Menu.findItem(R.id.nav_formularios).isVisible = true}
    BIN.PUEDE_EMPLEADOS     {nav_Menu.findItem(R.id.nav_empleados).isVisible = true}
    BIN.PUEDE_ACTIVIDADES   {nav_Menu.findItem(R.id.nav_actividads).isVisible = true}
    BIN.PUEDE_EQUIPAMIENTOS {nav_Menu.findItem(R.id.nav_equipamentos).isVisible = true}
            }


        }




        NombrarNavHeader(navView.getHeaderView(0))
         navController = findNavController(R.id.nav_host_fragment2)

        navController.addOnDestinationChangedListener(NavController.OnDestinationChangedListener { controller, destination, arguments ->
        if (destination.id== R.id.nav_empleados||
                destination.id== R.id.nav_equipamentos||
                destination.id== R.id.nav_formularios||
                destination.id== R.id.nav_actividads){
            fab.visibility=View.VISIBLE
        }
        else{fab.visibility=View.GONE}

        })
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav2_act, R.id.nav2_form_done, R.id.nav2_form,
                R.id.nav_empleados,R.id.nav_equipamentos,R.id.nav_formularios, R.id.nav_actividads
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)



    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.drawer2, menu)
        return true
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment2)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun DeslogearseClick(item: MenuItem) {

        BIN.BORRAR_USUARIO_LOGED()
        startActivity(Intent(this, Login::class.java))
        finish()

    }
    fun SalirClick(item: MenuItem) {

        finish()

    }


    private fun NombrarNavHeader(v:View) {
        val u= BIN.CARGAR_USUARIO_LOGED()
        var temp: TextView =v.findViewById(R.id.nav_header_textv_nombre)
        temp.text=u?.nom_apell
        temp=v.findViewById(R.id.nav_header_textv_usuario)
        temp.text=u?.usuario
    }

    //fun ClickItemUsuario(item: MenuItem) {mFragVisible=0}
    //fun ClickItemActividad(item: MenuItem) {mFragVisible=1}
    //fun ClickItemFormulario(item: MenuItem) {mFragVisible=2}
}