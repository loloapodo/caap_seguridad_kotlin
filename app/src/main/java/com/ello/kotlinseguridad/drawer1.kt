package com.ello.kotlinseguridad

import android.content.Intent
import android.os.Bundle
import android.view.Menu
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
import com.ello.kotlinseguridad.Editar.EActiv
import com.ello.kotlinseguridad.Editar.EForm
import com.ello.kotlinseguridad.Editar.EUsuario

class drawer1 : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private  var mFragVisible:Int = 0//0 Usuario, 1 Formularios, 2 Actividad
    private var REQ_EDITAR_USUARIO=22;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drawer1)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener {

            val id = navController.currentDestination?.id
            val intent=
             when(id)
             {
            R.id.nav_usuarios -> Intent(this, EUsuario::class.java)
            R.id.nav_actividads-> Intent(this, EActiv::class.java)
            R.id.nav_formularios-> Intent(this, EForm::class.java)//todo
                 else->Intent(this, EUsuario::class.java)//todo

                 }
            startActivityForResult(intent,REQ_EDITAR_USUARIO)
        }







        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
         navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_usuarios, R.id.nav_formularios, R.id.nav_actividads
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)



    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.drawer1, menu)
        return true
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    //fun ClickItemUsuario(item: MenuItem) {mFragVisible=0}
    //fun ClickItemActividad(item: MenuItem) {mFragVisible=1}
    //fun ClickItemFormulario(item: MenuItem) {mFragVisible=2}
}