package com.thebrodyaga.christianradio.screen.fragments.radio.list

import com.thebrodyaga.christianradio.domine.entities.data.RadioDto
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

interface RadioListView : MvpView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setData(list: List<RadioDto>)
}