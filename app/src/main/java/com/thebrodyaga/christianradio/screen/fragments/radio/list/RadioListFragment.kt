package com.thebrodyaga.christianradio.screen.fragments.radio.list


import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.thebrodyaga.christianradio.R
import com.thebrodyaga.christianradio.app.App
import com.thebrodyaga.christianradio.domine.entities.data.RadioDto
import com.thebrodyaga.christianradio.screen.adapters.RadioListAdapter
import com.thebrodyaga.christianradio.screen.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_radio_list.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

class RadioListFragment : BaseFragment(), RadioListView {

    private val adapter = RadioListAdapter()

    @Inject
    @InjectPresenter
    lateinit var presenter: RadioListPresenter

    @ProvidePresenter
    fun providePresenter() = presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }
    override fun getLayoutId(): Int = R.layout.fragment_radio_list

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@RadioListFragment.adapter
        }
    }

    override fun setData(list: List<RadioDto>) {
        adapter.setData(list)
    }

    companion object {
        fun newInstance() = RadioListFragment()
    }
}
