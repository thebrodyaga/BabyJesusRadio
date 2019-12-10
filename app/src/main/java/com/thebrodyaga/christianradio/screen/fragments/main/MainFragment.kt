package com.thebrodyaga.christianradio.screen.fragments.main


import android.os.Bundle
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.thebrodyaga.christianradio.R
import com.thebrodyaga.christianradio.app.App
import com.thebrodyaga.christianradio.navigation.LocalCiceroneHolder
import com.thebrodyaga.christianradio.navigation.Screens
import com.thebrodyaga.christianradio.screen.base.FlowFragment
import kotlinx.android.synthetic.main.bottom_sheet_player.*
import javax.inject.Inject

class MainFragment : FlowFragment() {

    @Inject
    lateinit var cicHolder: LocalCiceroneHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun getContainerId(): Int = R.id.fragment_container

    override fun getContainerName(): String = Screens.MainScreen.screenKey

    override fun getCiceroneHolder(): LocalCiceroneHolder = cicHolder

    override fun getLayoutId(): Int = R.layout.fragment_main

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (currentFragment == null)
            localRouter.newRootScreen(Screens.RadioListScreen)
        val bottomSheet = BottomSheetBehavior.from(bottom_sheet_player)
    }

}
