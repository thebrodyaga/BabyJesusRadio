package com.thebrodyaga.christianradio.screen.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import moxy.MvpAppCompatFragment

abstract class BaseFragment : MvpAppCompatFragment(), GetRouter {
    override val fragment: Fragment
        get() = this

    abstract fun getLayoutId(): Int

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    protected fun unSubscribeOnDestroy(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayoutId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*//todo com.google.android.exoplayer:extension-mediasession
        val mediaSessionConnector = MediaSessionConnector(mediaSession)
        mediaSessionConnector.setPlayer(player)*/
        /*ViewCompat.requestApplyInsets(view)
        if (isNeedApplyTopInsertInRoot())
            view.setOnApplyWindowInsetsListener { v, insets ->
                v.setPadding(
                    v.paddingLeft,
                    insets.systemWindowInsetTop,
                    v.paddingRight,
                    view.paddingBottom
                )
                insets.replaceSystemWindowInsets(
                    insets.systemWindowInsetLeft,
                    0,
                    insets.systemWindowInsetRight,
                    insets.systemWindowInsetBottom
                )
            }*/
    }

    open fun isNeedApplyTopInsertInRoot() = true

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.clear()
    }

    /**
     * закрыть фрагмент
     */
    open fun onBackPressed() {
        getAnyRouter().exit()
    }
}