package com.example.diary.ui.birthdays

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diary.Birthdays
import com.example.diary.Events
import com.example.diary.R
import com.example.diary.adapters.BirthdaysCustomRecyclerAdapter
import com.example.diary.adapters.EventsCustomRecyclerAdapter
import com.example.diary.ui.events.EventsViewModel
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where

class BirthdaysFragment : Fragment() {
    private lateinit var bitrhdaysViewModel: BirthdaysViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bitrhdaysViewModel =
            ViewModelProvider(this).get(BirthdaysViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_birthdays, container, false)

        val eventRecyclerView: RecyclerView = root.findViewById(R.id.recycleViewBirthdays)
        val imageView: ImageView = root.findViewById(R.id.imageViewBirthdays)

        eventRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        Realm.getDefaultInstance().executeTransaction{ it ->
            try {
                it.createObject<Birthdays>()
            } catch (e: Exception) {
            }

            val list = it.where<Birthdays>().findAll()
                .filter { it.namePerson != null }
                .toList()

            if (list.count() > 0) {
                eventRecyclerView.adapter = BirthdaysCustomRecyclerAdapter(list)
                imageView.visibility = View.GONE
            }
            else{
                imageView.visibility = View.VISIBLE
                eventRecyclerView.visibility = View.GONE

                imageView.setImageResource(R.drawable.ic_baseline_inbox_24)
            }
        }
        return root
    }
}