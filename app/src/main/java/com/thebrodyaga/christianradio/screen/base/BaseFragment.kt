package com.thebrodyaga.christianradio.screen.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
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
//        ViewCompat.requestApplyInsets(view)
    }

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