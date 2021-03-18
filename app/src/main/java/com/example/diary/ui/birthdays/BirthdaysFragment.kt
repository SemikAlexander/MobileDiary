package com.example.diary.ui.birthdays

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diary.DB.ActionDB
import com.example.diary.DB.Birthdays
import com.example.diary.DB.Holidays
import com.example.diary.R
import com.example.diary.SettingsActivity
import com.example.diary.adapters.BirthdaysCustomRecyclerAdapter
import com.example.diary.startActivity
import com.example.diary.toast
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where

class BirthdaysFragment : Fragment(), BirthdaysCustomRecyclerAdapter.OnItemClickListener {
    private lateinit var bitrhdaysViewModel: BirthdaysViewModel

    lateinit var list: List<Birthdays>

    val action = ActionDB()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)

        bitrhdaysViewModel =
            ViewModelProvider(this).get(BirthdaysViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_birthdays, container, false)

        val eventRecyclerView: RecyclerView = root.findViewById(R.id.recycleViewBirthdays)
        val imageView: ImageView = root.findViewById(R.id.imageViewBirthdays)

        eventRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        list = action.getAllBirthdays()

        if (list.count() > 0) {
            eventRecyclerView.adapter = BirthdaysCustomRecyclerAdapter(list, this)
            imageView.visibility = View.GONE
        }
        else{
            imageView.visibility = View.VISIBLE
            eventRecyclerView.visibility = View.GONE

            imageView.setImageResource(R.drawable.ic_baseline_inbox_24)
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
        toast("${list.get(position).namePerson}")
    }
}