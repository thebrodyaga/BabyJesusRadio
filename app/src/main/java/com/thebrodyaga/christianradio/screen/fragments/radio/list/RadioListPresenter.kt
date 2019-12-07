package com.thebrodyaga.christianradio.screen.fragments.radio.list

import com.thebrodyaga.christianradio.repository.RadioRepository
import com.thebrodyaga.christianradio.screen.base.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class RadioListPresenter @Inject constructor(
    private val radioRepository: RadioRepository
) : BasePresenter<RadioListView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        unSubscribeOnDestroy(
            radioRepository.getAllRadios()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {viewState.setData(it)},
                    { it.printStackTrace() })
        )
    }
}