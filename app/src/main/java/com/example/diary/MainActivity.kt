package com.example.diary

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.edit
import io.realm.Realm
import io.realm.RealmConfiguration
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val pref = getSharedPreferences("setting", Context.MODE_PRIVATE)
        if (pref.getString("mode", null).toString() == "dark")
            setTheme(R.style.Theme_DiaryNight)
        else
            setTheme(R.style.Theme_Diary)

        val edit = pref.edit()

        pref.getString("language", null)?.let {
            setLocale(this, it)
        }

        setContentView(R.layout.activity_main)

        Realm.init(this)    /*Initialize Realm*/

        val config = RealmConfiguration.Builder().allowWritesOnUiThread(true).build()
        Realm.setDefaultConfiguration(config)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            startActivity<EventsActivity>(){
                putExtra("type_activity", "add")
            }
        }

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(R.id.nav_home, R.id.nav_events,
                R.id.nav_birthdays, R.id.nav_holidays), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onRestart() {
        super.onRestart()
        val pref = getSharedPreferences("setting", Context.MODE_PRIVATE)
        val isNeedRecreate = pref.getBoolean("is_need_recreate", false)

        if (isNeedRecreate) {
            pref.edit { putBoolean("is_need_recreate", false) }
            recreate()
        }
    }

    private fun setLocale(context: Context, locale: String) {
        context.resources.configuration.locale = Locale(locale)
        context.resources.updateConfiguration(
            context.resources.configuration,
            context.resources.displayMetrics
        )
    }
}