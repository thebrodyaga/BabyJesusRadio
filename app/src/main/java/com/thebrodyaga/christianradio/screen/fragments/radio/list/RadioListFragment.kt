package com.thebrodyaga.christianradio.screen.fragments.radio.list


import com.thebrodyaga.christianradio.R
import com.thebrodyaga.christianradio.screen.base.BaseFragment

class RadioListFragment : BaseFragment() {
    override fun getLayoutId(): Int = R.layout.fragment_radio_list


    companion object {
        fun newInstance() = RadioListFragment()
    }
}
