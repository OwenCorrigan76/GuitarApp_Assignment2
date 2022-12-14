package ie.wit.guitarApp.main

import android.app.Application
import ie.wit.guitarApp.models.GuitarStore
import timber.log.Timber

class MainApp : Application() {

    lateinit var guitarStore: GuitarStore

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
       // guitarStore = GuitarManager()
        Timber.i("Guitar Application Started")
    }
}