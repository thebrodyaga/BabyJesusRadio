package com.thebrodyaga.christianradio.di.modules;


import com.thebrodyaga.christianradio.navigation.LocalCiceroneHolder;
import com.thebrodyaga.christianradio.navigation.RouterTransition;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;

@Module
public class
NavigationModule {
    private Cicerone<RouterTransition> cicerone;

    public NavigationModule() {
        cicerone = Cicerone.create(new RouterTransition());
    }

    @Provides
    @Singleton
    RouterTransition provideTransitionRouter() {
        return cicerone.getRouter();
    }

    @Provides
    @Singleton
    Router provideRouter() {
        return cicerone.getRouter();
    }

    @Provides
    @Singleton
    NavigatorHolder provideNavigatorHolder() {
        return cicerone.getNavigatorHolder();
    }

    @Provides
    @Singleton
    LocalCiceroneHolder provideLocalNavigationHolder() {
        return new LocalCiceroneHolder();
    }
}
