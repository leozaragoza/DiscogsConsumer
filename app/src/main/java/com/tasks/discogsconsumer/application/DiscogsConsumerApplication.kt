package com.tasks.discogsconsumer.application

import android.app.Application
import com.tasks.discogsconsumer.di.AppComponent
import com.tasks.discogsconsumer.di.AppModule
import com.tasks.discogsconsumer.di.DaggerAppComponent

class DiscogsConsumerApplication: Application() {
    companion object {
        lateinit var discogsConsumerComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        discogsConsumerComponent = initDagger(this)
    }

    private fun initDagger(app: DiscogsConsumerApplication): AppComponent =
        DaggerAppComponent.builder()
            .appModule(AppModule(app))
            .build()
}