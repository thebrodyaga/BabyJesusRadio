package com.thebrodyaga.christianradio.screen.adapters

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.vectordrawable.graphics.drawable.Animatable2Compat
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.bumptech.glide.Glide
import com.thebrodyaga.christianradio.R
import com.thebrodyaga.christianradio.domine.entities.data.RadioDto
import com.thebrodyaga.christianradio.screen.isInvisible
import com.thebrodyaga.christianradio.tools.RadioPlayer
import kotlinx.android.synthetic.main.item_radio.view.*

class RadioListAdapter constructor(
    private val onRadioClick: (radioDto: RadioDto) -> Unit,
    private val radioPlayer: RadioPlayer
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

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        (holder as? ViewHolder)?.stopAnimation(false)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(list[position])
    }

    private inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var item: RadioDto? = null
        private var avd: AnimatedVectorDrawableCompat? =
            AnimatedVectorDrawableCompat.create(view.context, R.drawable.avd_playing)
        private val animCallback = LoopVectorCallback()

        init {
            itemView.apply {
                radio_image.clipToOutline = true
                root_view.setOnClickListener { item?.also { onRadioClick.invoke(it) } }
            }
            initAnimation()
        }

        private fun initAnimation() = with(itemView) {
            radio_playing_layout.foreground = avd
                ?: ContextCompat.getDrawable(context, R.drawable.ic_play_56dp)

            radioPlayer.addPlayingListeners { currentRadio, isPlaying ->
                when {
                    item == null || currentRadio == null -> stopAnimation(false)
                    currentRadio.radioDto == item ->
                        if (isPlaying) startAnimation() else stopAnimation(true)
                    else -> stopAnimation(false)
                }

            }
        }

        //false совсем убрать
        fun stopAnimation(isPause: Boolean) = with(itemView) {
            radio_playing_layout.isInvisible(!isPause)
            avd?.unregisterAnimationCallback(animCallback)
            avd?.stop()
        }

        fun startAnimation() = with(itemView) {
            radio_playing_layout.isInvisible(false)
            avd?.registerAnimationCallback(animCallback)
            avd?.start()
        }

        fun bind(radioDto: RadioDto) = with(itemView) {
            item = radioDto
            radio_name.text = radioDto.radioName
            Glide.with(context)
                .load(radioDto.radioImage)
                .into(radio_image)
        }


        private inner class LoopVectorCallback : Animatable2Compat.AnimationCallback() {
            override fun onAnimationEnd(drawable: Drawable?) {
                itemView.post { avd?.start() }
            }
        }
    }
}