package com.thebrodyaga.christianradio.screen.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.thebrodyaga.christianradio.R
import com.thebrodyaga.christianradio.domine.entities.data.RadioDto
import kotlinx.android.synthetic.main.item_radio.view.*

class RadioListAdapter constructor(
    private val onRadioClick: (radioDto: RadioDto) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val list = mutableListOf<RadioDto>()

    fun setData(data: List<RadioDto>) {
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_radio,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(list[position])
    }

    private inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var item: RadioDto? = null

        init {
            itemView.root_view.setOnClickListener {
                item?.also { onRadioClick.invoke(it) }
            }
        }

        fun bind(radioDto: RadioDto) = with(itemView) {
            item = radioDto
            radio_name.text = radioDto.radioName
            Glide.with(context)
                .load(radioDto.radioImage)
                .into(radio_image)
        }
    }
}