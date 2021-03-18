package com.example.diary

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.diary.DB.ActionDB
import com.example.diary.databinding.ActivityAddBinding
import java.text.SimpleDateFormat
import java.util.*

class AddActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddBinding

    private var dateAndTime = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        val pref = getSharedPreferences("setting", Context.MODE_PRIVATE)
        if (pref.getString("mode", null).toString() == "dark")
            setTheme(R.style.Theme_DiaryNight)
        else
            setTheme(R.style.Theme_Diary)

        pref.edit()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        pref.getString("language", null)?.apply {
            setLocale(this@AddActivity, this)
        }

        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val map = mapOf(getString(R.string.item_events) to "event",
                getString(R.string.item_holidays) to "holiday",
                getString(R.string.item_birthdays) to "birthday")

        val values = map.values.toList()
        val keys = map.keys

        var eventType = resources.getStringArray(R.array.name_type_events)

        binding.apply {
            eventSpinner.adapter = ArrayAdapter(this@AddActivity, R.layout.spinner_item, keys.toMutableList())
            eventSpinner.setSelection(0)

            eventSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {}

                override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                ) {
                    when {
                        values[position] == "birthday" -> {
                            descriptionEditText.visibility = View.GONE
                            titleEditText.hint = getString(R.string.name_person_hint)
                        }
                        else -> {
                            descriptionEditText.visibility = View.VISIBLE
                            titleEditText.hint = getString(R.string.title_hint)
                        }
                    }
                }
            }

            val sdf = SimpleDateFormat("dd.MM.yyyy", Locale.US)

            dateTextView.text = sdf.format(Date())

            val d = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                dateAndTime[Calendar.YEAR] = year
                dateAndTime[Calendar.MONTH] = monthOfYear
                dateAndTime[Calendar.DAY_OF_MONTH] = dayOfMonth

                dateTextView.text = sdf.format(dateAndTime.time)
            }

            dateTextView.setOnClickListener {
                DatePickerDialog(
                    this@AddActivity, d,
                    dateAndTime.get(Calendar.YEAR),
                    dateAndTime.get(Calendar.MONTH),
                    dateAndTime.get(Calendar.DAY_OF_MONTH)
                ).show()
            }

            saveButton.setOnClickListener {
                val format = SimpleDateFormat("dd.MM.yyyy")

                val action = ActionDB()

                when (eventSpinner.selectedItemPosition) {
                    0 -> {
                        action.addEvent(
                                titleEditText.text.toString(),
                                descriptionEditText.text.toString(),
                                format.parse(dateTextView.text.toString()))
                    }
                    1 -> {
                        action.addHoliday(
                                titleEditText.text.toString(),
                                descriptionEditText.text.toString(),
                                format.parse(dateTextView.text.toString()))
                    }
                    2 -> {
                        action.addBirthdays(titleEditText.text.toString(),
                                format.parse(dateTextView.text.toString()))
                    }
                }

                toast(getString(R.string.record_added))
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
}