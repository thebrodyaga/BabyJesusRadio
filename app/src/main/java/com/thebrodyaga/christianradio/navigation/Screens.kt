package com.thebrodyaga.christianradio.navigation

import androidx.fragment.app.Fragment
import com.thebrodyaga.christianradio.screen.fragments.main.MainFragment
import com.thebrodyaga.christianradio.screen.fragments.radio.list.RadioListFragment
import com.thebrodyaga.christianradio.screen.fragments.settings.all.SettingsFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

object Screens {

    object MainScreen:SupportAppScreen(){
        override fun getFragment(): Fragment {
            return MainFragment()
        }
    }

    object RadioListScreen : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return RadioListFragment.newInstance()
        }
    }

    object SettingsScreen : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return SettingsFragment()
        }
    }
}