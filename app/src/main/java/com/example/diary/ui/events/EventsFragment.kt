package com.example.diary.ui.events

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diary.Events
import com.example.diary.R
import com.example.diary.adapters.EventsCustomRecyclerAdapter
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where

class EventsFragment : Fragment() {
    private lateinit var eventsViewModel: EventsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        eventsViewModel =
            ViewModelProvider(this).get(EventsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_events, container, false)

        val eventRecyclerView: RecyclerView = root.findViewById(R.id.recycleViewEvents)
        val imageView: ImageView = root.findViewById(R.id.imageView1)

        eventRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        Realm.getDefaultInstance().executeTransaction{ it ->
            try {
                it.createObject<Events>()
            } catch (e: Exception) {
            }

            val list = it.where<Events>().findAll()
                .filter { it.title != null }
                .toList()

            if (list.count() > 0) {
                eventRecyclerView.adapter = EventsCustomRecyclerAdapter(list)
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