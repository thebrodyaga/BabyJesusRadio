package com.thebrodyaga.christianradio.screen.fragments.radio.list


import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.thebrodyaga.christianradio.R
import com.thebrodyaga.christianradio.app.App
import com.thebrodyaga.christianradio.domine.entities.data.PlayingRadio
import com.thebrodyaga.christianradio.domine.entities.data.RadioDto
import com.thebrodyaga.christianradio.navigation.Screens
import com.thebrodyaga.christianradio.screen.adapters.RadioListAdapter
import com.thebrodyaga.christianradio.screen.base.BaseFragment
import com.thebrodyaga.christianradio.tools.RadioPlayer
import kotlinx.android.synthetic.main.fragment_radio_list.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

class RadioListFragment : BaseFragment(), RadioListView, Toolbar.OnMenuItemClickListener {

    private lateinit var adapter: RadioListAdapter

    @Inject
    @InjectPresenter
    lateinit var presenter: RadioListPresenter

    @Inject
    lateinit var radioPlayer: RadioPlayer

    @ProvidePresenter
    fun providePresenter() = presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(this)
        adapter = RadioListAdapter(
            { radioPlayer.playRadio(PlayingRadio(it, false)) },
            radioPlayer
        )
        super.onCreate(savedInstanceState)
    }

    override fun getLayoutId(): Int = R.layout.fragment_radio_list

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@RadioListFragment.adapter
        }
        toolbar.setOnMenuItemClickListener(this)
    }

    override fun setData(list: List<RadioDto>) {
        adapter.setData(list)
    }

    override fun onMenuItemClick(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.settings -> {
                getGlobalRouter().navigateTo(Screens.SettingsScreen)
                true
            }
            else -> false
        }
    }

    companion object {
        fun newInstance() = RadioListFragment()
    }
}
