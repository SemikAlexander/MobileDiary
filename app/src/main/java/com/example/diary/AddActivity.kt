package com.example.diary

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.diary.databinding.ActivityAddBinding
import io.realm.Realm
import io.realm.kotlin.where
import io.realm.kotlin.createObject
import java.text.SimpleDateFormat
import java.util.*

class AddActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddBinding

    private var dateAndTime = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {

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
                Realm.getDefaultInstance()
                    .executeTransaction { it ->
                        try {
                            it.createObject<Events>()
                        } catch (e: Exception) {
                        }


                        it.insertOrUpdate(Events().apply {
                            title = titleEditText.text.toString()
                            description = descriptionEditText.text.toString()
                            val format = SimpleDateFormat("dd.MM.yyyy")
                            date = format.parse(dateTextView.text.toString())
                        })

                        it.where<Events>().findAll()
                            .filter { it.title != null }
                            .toList()
                            .forEach {
                                Log.e("test", it.title ?: "null")
                            }
                    }
                toast(getString(R.string.record_added))
            }
        }
    }
}