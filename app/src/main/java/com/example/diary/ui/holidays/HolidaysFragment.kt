package com.example.diary.ui.holidays

import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diary.DB.Events
import com.example.diary.DB.Holidays
import com.example.diary.R
import com.example.diary.SettingsActivity
import com.example.diary.adapters.HolidaysCustomRecycleAdapter
import com.example.diary.startActivity
import com.example.diary.toast
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where

class HolidaysFragment  : Fragment(), HolidaysCustomRecycleAdapter.OnItemClickListener {
    private lateinit var holidaysViewModel: HolidaysViewModel

    lateinit var list: List<Holidays>

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)

        holidaysViewModel =
                ViewModelProvider(this).get(HolidaysViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_holidays, container, false)

        val birthdaysRecyclerView: RecyclerView = root.findViewById(R.id.recycleViewHolidays)
        val imageView: ImageView = root.findViewById(R.id.imageViewHolidays)

        birthdaysRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        Realm.getDefaultInstance().executeTransaction{ it ->
            try {
                it.createObject<Holidays>()
            } catch (e: Exception) {
            }

            list = it.where<Holidays>().findAll()
                    .filter { it.title != null }
                    .toList()

            if (list.count() > 0) {
                birthdaysRecyclerView.adapter = HolidaysCustomRecycleAdapter(list, this)
                imageView.visibility = View.GONE
            }
            else{
                imageView.visibility = View.VISIBLE
                birthdaysRecyclerView.visibility = View.GONE

                imageView.setImageResource(R.drawable.ic_baseline_inbox_24)
            }
        }
        return root
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.action_settings -> {
                startActivity<SettingsActivity>()
                return true
            }
        }
        return super.onContextItemSelected(item)
    }

    override fun onItemClick(position: Int) {
        toast("${list[position].description}")
    }
}