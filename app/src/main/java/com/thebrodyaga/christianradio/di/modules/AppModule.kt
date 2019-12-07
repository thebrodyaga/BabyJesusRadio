package com.thebrodyaga.christianradio.di.modules

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.thebrodyaga.christianradio.tools.AudioPlayer
import com.thebrodyaga.christianradio.tools.RecordVoice
import com.thebrodyaga.christianradio.tools.SettingManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideContext(application: Application): Context = application.applicationContext

    @Provides
    @Singleton
    fun provideRecordVoice(
        audioPlayer: AudioPlayer,
        context: Context
    ): RecordVoice = RecordVoice(audioPlayer, context)

    @Provides
    @Singleton
    fun provideAudioPlayer(context: Context): AudioPlayer = AudioPlayer(context)

    @Provides
    @Singleton
    fun provideGson(): Gson =
        GsonBuilder().create()

    @Provides
    @Singleton
    fun provideAppSharedPreferences(context: Context): SharedPreferences =
        context.getSharedPreferences("AppSharedPreferences", Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun provideSetting(sharedPreferences: SharedPreferences): SettingManager =
        SettingManager(sharedPreferences)

}