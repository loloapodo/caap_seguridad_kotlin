package com.ello.kotlinseguridad

import android.app.TaskStackBuilder
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
import androidx.navigation.NavController
import com.ello.kotlinseguridad.Activ.Login.Login
import com.ello.kotlinseguridad.BIN.BIN
import com.ello.kotlinseguridad.Editar.*
import com.ello.kotlinseguridad.ui.adm_act.AdmActFrag
import com.ello.kotlinseguridad.ui.adm_equip.AdmEquipFrag
import com.ello.kotlinseguridad.ui.adm_equip.AdmRolesFrag
import com.ello.kotlinseguridad.ui.adm_form.AdmFormFrag
import com.ello.kotlinseguridad.ui.adm_us.AdmUsFrag

class drawer1 : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    private var REQ_CREAR_EDITAR=22;
    companion object{
        val RES_OK_CREAR_ACTIVIDAD=10
        val RES_OK_CREAR_FORMULARIO=11
        val RES_OK_CREAR_USUARIO=12
        val RES_OK_CREAR_EQUIP=13
        val RES_OK_CREAR_ROL=14

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drawer1)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)



        val fab: FloatingActionButton = findViewById(R.id.fab)
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







        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        NombrarNavHeader(navView.getHeaderView(0))
         navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_empleados,R.id.nav_equipamentos,R.id.nav_formularios, R.id.nav_actividads,R.id.nav_usuarios,R.id.nav_roles
            ), drawerLayout
        )








        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)



    }

    private fun NombrarNavHeader(v: View) {
        val u= BIN.CARGAR_USUARIO_LOGED()
        var temp:TextView=v.findViewById(R.id.nav_header_textv_nombre)
        temp.text=u?.nom_apell
        temp=v.findViewById(R.id.nav_header_textv_usuario)
        temp.text=u?.usuario
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


    fun DeslogearseClick(item: MenuItem) {

        BIN.BORRAR_USUARIO_LOGED()
        startActivity(Intent(this, Login::class.java))
        finish()

    }



    fun SalirClick(item: MenuItem) {

    finish()
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        Log.e("onActivityResult:","Floatin butt ${resultCode.toString()}")

        val navHostFragment= supportFragmentManager.findFragmentById(R.id.nav_host_fragment);


        if (resultCode==RES_OK_CREAR_ACTIVIDAD) {
            val    I=navHostFragment?.childFragmentManager?.fragments?.get(0) as AdmActFrag
            I.Cargar()
                        }
        if (resultCode== RES_OK_CREAR_FORMULARIO) {

            val    I=navHostFragment?.childFragmentManager?.fragments?.get(0) as AdmFormFrag
            I.Cargar()
        }
        if (resultCode== RES_OK_CREAR_USUARIO) {

            val    I=navHostFragment?.childFragmentManager?.fragments?.get(0) as AdmUsFrag
            I.Cargar()
        }
        if (resultCode== RES_OK_CREAR_EQUIP) {

            val    I=navHostFragment?.childFragmentManager?.fragments?.get(0) as AdmEquipFrag
            I.Cargar()
        }
        if (resultCode== RES_OK_CREAR_ROL) {

            val    I=navHostFragment?.childFragmentManager?.fragments?.get(0) as AdmRolesFrag
            I.Cargar()
        }







        super.onActivityResult(requestCode, resultCode, data)
    }


    //fun ClickItemUsuario(item: MenuItem) {mFragVisible=0}
    //fun ClickItemActividad(item: MenuItem) {mFragVisible=1}
    //fun ClickItemFormulario(item: MenuItem) {mFragVisible=2}
}