package com.thebrodyaga.christianradio.di

import android.app.Application
import com.google.gson.Gson
import com.thebrodyaga.christianradio.di.modules.AppModule
import com.thebrodyaga.christianradio.tools.SettingManager
import com.thebrodyaga.christianradio.app.AppActivity
import com.thebrodyaga.christianradio.app.SplashActivity
import com.thebrodyaga.christianradio.di.modules.NavigationModule
import com.thebrodyaga.christianradio.navigation.RouterTransition
import com.thebrodyaga.christianradio.screen.fragments.main.MainFragment
import com.thebrodyaga.christianradio.screen.fragments.radio.list.RadioListFragment
import com.thebrodyaga.christianradio.screen.fragments.settings.all.SettingsFragment
import com.thebrodyaga.christianradio.service.PlayerService
import dagger.BindsInstance
import dagger.Component
import ru.terrakok.cicerone.NavigatorHolder
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NavigationModule::class])
interface AppComponent {
    fun getRouter(): RouterTransition
    fun getNavigatorHolder(): NavigatorHolder
    fun getGson(): Gson
    fun inject(activity: AppActivity)
    fun inject(activity: SplashActivity)
    fun getSettingManager(): SettingManager
    fun inject(fragment: RadioListFragment)
    fun inject(settingsFragment: SettingsFragment)
    fun inject(service: PlayerService)
    fun inject(fragment: MainFragment)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}