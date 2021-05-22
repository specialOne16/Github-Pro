package com.jundapp.githubpro

import android.app.Application
import com.jundapp.githubpro.core.di.CoreModule.databaseModule
import com.jundapp.githubpro.core.di.CoreModule.networkModule
import com.jundapp.githubpro.core.di.CoreModule.repositoryModule
import com.jundapp.githubpro.di.AppModule.useCaseModule
import com.jundapp.githubpro.di.AppModule.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

@Suppress("unused")
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}