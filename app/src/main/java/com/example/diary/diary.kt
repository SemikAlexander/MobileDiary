package com.example.diary

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class Events: RealmObject() {
    var title: String? = null
    var description: String? = null
    var date: Date? = null
}

open class Birthdays: RealmObject() {
    var namePerson: String? = null
    var date: Date? = null
}

open class Holidays: RealmObject() {
    var title: String? = null
    var description: String? = null
    var date: Date? = null
}