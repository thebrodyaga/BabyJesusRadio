package com.thebrodyaga.christianradio.navigation

import androidx.fragment.app.Fragment
import com.thebrodyaga.christianradio.screen.fragments.radio.list.RadioListFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

object Screens {

    object RadioListScreen : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return RadioListFragment.newInstance()
        }
    }
    /*object MainScreen : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return MainFragment()
        }
    }

    object SoundsListScreen : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return SoundsListFragment()
        }
    }

    data class SoundsDetailsScreen constructor(val transcription: String) : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return SoundFragment.newInstance(transcription)
        }
    }

    object SoundsTrainingScreen : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return SoundsTrainingFragment()
        }
    }

    object SettingsScreen : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return SettingsFragment()
        }
    }*/
}