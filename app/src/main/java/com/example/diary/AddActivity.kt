package com.example.diary

import android.app.DatePickerDialog
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
    private var typeEvent = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var eventType = resources.getStringArray(R.array.name_type_events)

        binding.apply {
            eventSpinner.adapter = ArrayAdapter.createFromResource(this@AddActivity,
                    R.array.name_type_events, R.layout.spinner_item)
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
                        eventType[position] == "Birthday" -> {
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

                when (eventSpinner.selectedItem.toString()) {
                    "Event" -> {
                        action.addEvent(
                                titleEditText.text.toString(),
                                descriptionEditText.text.toString(),
                                format.parse(dateTextView.text.toString()))
                    }
                    "Holiday" -> {
                        action.addHoliday(
                                titleEditText.text.toString(),
                                descriptionEditText.text.toString(),
                                format.parse(dateTextView.text.toString()))
                    }
                    "Birthday" -> {
                        action.addBirthdays(titleEditText.text.toString(),
                                format.parse(dateTextView.text.toString()))
                    }
                }

                toast(getString(R.string.record_added))
            }
        }
    }
}