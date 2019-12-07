package com.thebrodyaga.christianradio.repository

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.thebrodyaga.christianradio.domine.entities.data.RadioDto
import com.thebrodyaga.christianradio.tools.SettingManager
import io.reactivex.Observable
import java.io.BufferedReader
import java.io.InputStreamReader


class RadioRepository constructor(
    private val context: Context,
    private val gson: Gson,
    private val settingManager: SettingManager
) {
    private val radiosCash = mutableListOf<RadioDto>()

    fun getAllRadios(): Observable<List<RadioDto>> {
        return when {
            radiosCash.isEmpty() -> radiosFromAssets()
            else -> Observable.fromCallable { radiosCash }
        }
    }

    private fun radiosFromAssets(): Observable<List<RadioDto>> {
        val am = context.assets
        val reader = BufferedReader(InputStreamReader(am.open("radios")))

        val radioList: List<RadioDto> =
            gson.fromJson(reader, object : TypeToken<List<RadioDto>>() {}.type)
        radiosCash.clear()
        radiosCash.addAll(radioList)
        return Observable.fromCallable { radiosCash }
    }
}