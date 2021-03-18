package com.example.diary.DB

import android.view.View
import com.example.diary.R
import com.example.diary.adapters.BirthdaysCustomRecyclerAdapter
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import java.util.*

class ActionDB {
    fun addEvent(inputTitle: String,
                         inputDescription: String,
                         inputDate: Date){
        Realm.getDefaultInstance().executeTransaction {
            try {
                it.createObject<Events>()
            } catch (e: Exception) {
            }

            it.insertOrUpdate(Events().apply {
                title = inputTitle
                description = inputDescription
                date = inputDate
            })
        }
    }

    fun getAllEvents(): List<Events> {
        lateinit var list: List<Events>

        Realm.getDefaultInstance().executeTransaction{
            try {
                it.createObject<Events>()
            } catch (e: Exception) {
            }

            list = it.where<Events>().findAll()
                    .filter { it.title != null }
                    .toList()
        }
        return list
    }

    fun addBirthdays(inputName: String,
                        inputDate: Date){
        Realm.getDefaultInstance().executeTransaction {
            try {
                it.createObject<Birthdays>()
            } catch (e: Exception) {
            }

            it.insertOrUpdate(Birthdays().apply {
                namePerson = inputName
                date = inputDate
            })
        }
    }

    fun getAllBirthdays(): List<Birthdays> {
        lateinit var list: List<Birthdays>

        Realm.getDefaultInstance().executeTransaction{
            try {
                it.createObject<Birthdays>()
            } catch (e: Exception) {
            }

            list = it.where<Birthdays>().findAll()
                    .filter { it.namePerson != null }
                    .toList()
        }
        return list
    }

    fun addHoliday(inputTitle: String,
                        inputDescription: String,
                        inputDate: Date){
        Realm.getDefaultInstance().executeTransaction {
            try {
                it.createObject<Holidays>()
            } catch (e: Exception) {
            }

            it.insertOrUpdate(Holidays().apply {
                title = inputTitle
                description = inputDescription
                date = inputDate
            })
        }
    }

    fun getAllHolidays(): List<Holidays> {
        lateinit var list: List<Holidays>

        Realm.getDefaultInstance().executeTransaction{
            try {
                it.createObject<Holidays>()
            } catch (e: Exception) {
            }

            list = it.where<Holidays>().findAll()
                    .filter { it.title != null }
                    .toList()
        }
        return list
    }
}