package com.example.diary

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.diary.databinding.ActivitySettingsBinding
import com.example.diary.ui.events.EventsFragment
import java.util.*

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        val pref = getSharedPreferences("setting", Context.MODE_PRIVATE)
        if (pref.getString("mode", null).toString() == "dark")
            setTheme(R.style.Theme_DiaryNight)
        else
            setTheme(R.style.Theme_Diary)

        val editor = pref.edit()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val map = mapOf("Español" to "es", "English" to "en", "Русский" to "ru", "Français" to "fr", "Deutsch" to "de")
        val values = map.values.toList()
        val keys = map.keys

        binding.apply {
            languageSpinner.adapter = ArrayAdapter(this@SettingsActivity, R.layout.spinner_item, keys.toMutableList())
            languageSpinner.setSelection(values.indexOf(pref.getString("language", null)))

            if (pref.getString("mode", null).toString() == "dark") {
                darkMode.setImageResource(R.drawable.ic_baseline_brightness_5_24)
                darkMode.tag = "dark theme"
            }
            else{
                darkMode.setImageResource(R.drawable.ic_baseline_brightness_4_24)
                darkMode.tag = "light theme"
            }

            languageSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {}

                override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                ) {
                    editor.putString("language", values[position])
                }
            }

            darkMode.setOnClickListener {
                if (darkMode.tag as String == "dark theme") {
                    darkMode.setImageResource(R.drawable.ic_baseline_brightness_4_24)
                    darkMode.tag = "light theme"
                    editor.putString("mode", "day")
                }
                else{
                    darkMode.setImageResource(R.drawable.ic_baseline_brightness_5_24)
                    darkMode.tag = "dark theme"
                    editor.putString("mode", "dark")
                }

                editor.apply()
                recreate()
            }

            saveButton.setOnClickListener {
                editor.apply()
                pref.getString("language", null)?.apply {

                    setLocale(this@SettingsActivity, this)
                }
                startActivity<SettingsActivity>()
                finish()
            }
        }
    }

    private fun setLocale(context: Context, locale: String) {
        context.resources.configuration.locale = Locale(locale)
        context.resources.updateConfiguration(
                context.resources.configuration,
                context.resources.displayMetrics
        )
    }

    override fun onBackPressed() {
        finish()
    }
}