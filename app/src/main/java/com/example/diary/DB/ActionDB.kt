package com.example.diary.DB

import io.realm.Realm
import io.realm.kotlin.createObject
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
}