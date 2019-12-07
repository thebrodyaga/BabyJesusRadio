package com.thebrodyaga.christianradio.app

import android.content.Intent
import android.os.Bundle
import com.thebrodyaga.christianradio.screen.base.BasePresenter
import com.thebrodyaga.christianradio.app.App
import moxy.InjectViewState
import moxy.MvpView
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType
import javax.inject.Inject

class SplashActivity : BaseActivity(), SplashView {
    override fun forward() {
        val intent = Intent(this, AppActivity::class.java)
        startActivity(intent)
        overridePendingTransition(0, 0)
        finish()
    }

    override fun error() {
        finish()
    }

    @Inject
    @InjectPresenter
    lateinit var presenter: SplashPresenter

    @ProvidePresenter
    fun providePresenter() = presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }
}

@InjectViewState
class SplashPresenter @Inject constructor(
) : BasePresenter<SplashView>() {
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.forward()
    }
}

interface SplashView : MvpView {
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun forward()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun error()
}
