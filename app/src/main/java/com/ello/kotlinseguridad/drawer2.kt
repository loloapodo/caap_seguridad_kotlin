package com.ello.kotlinseguridad

import android.content.Intent
import android.os.Bundle
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
import androidx.navigation.NavController
import com.ello.kotlinseguridad.Activ.Login.Login
import com.ello.kotlinseguridad.BIN.BIN
import com.ello.kotlinseguridad.Editar.EActiv
import com.ello.kotlinseguridad.Editar.EForm
import com.ello.kotlinseguridad.Editar.EUsuario

class drawer2 : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private  var mFragVisible:Int = 0//0 Usuario, 1 Formularios, 2 Actividad
    private var REQ_TODO=22;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drawer2)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)



        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.visibility=View.GONE
        /*
        fab.setOnClickListener {//todo

            val id = navController.currentDestination?.id
            val intent=
             when(id)
             {
            R.id.nav2_act -> Intent(this, EUsuario::class.java)
            R.id.nav2_form_done-> Intent(this, EActiv::class.java)
            R.id.nav2_form_done-> Intent(this, EForm::class.java)
                 else->Intent(this, EUsuario::class.java)

                 }
            startActivityForResult(intent,REQ_TODO)
        }
*/






        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout2)
        val navView: NavigationView = findViewById(R.id.nav_view2)
        NombrarNavHeader(navView.getHeaderView(0))
         navController = findNavController(R.id.nav_host_fragment2)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav2_act, R.id.nav2_form_done, R.id.nav2_form
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

    fun Deslogearse(item: MenuItem) {

        BIN.BORRAR_USUARIO_LOGED()
        startActivity(Intent(this, Login::class.java))
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