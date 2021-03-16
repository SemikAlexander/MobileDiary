package com.example.diary.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.diary.Birthdays
import com.example.diary.Events
import com.example.diary.R
import java.text.SimpleDateFormat
import java.util.*

class BirthdaysCustomRecyclerAdapter(private val values: List<Birthdays>) :
    RecyclerView.Adapter<BirthdaysCustomRecyclerAdapter.MyViewHolder>() {

    override fun getItemCount() = values.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recycleview_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.largeTextView?.text = values[position].namePerson

        val sdf = SimpleDateFormat("dd.MM.yyyy", Locale.US)
        holder.smallTextView?.text = sdf.format(values[position].date)
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var largeTextView: TextView? = null
        var smallTextView: TextView? = null

        init {
            largeTextView = itemView.findViewById(R.id.textViewLarge)
            smallTextView = itemView.findViewById(R.id.textViewSmall)
        }
    }
}