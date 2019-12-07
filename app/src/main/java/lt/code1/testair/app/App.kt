package lt.code1.testair.app

import android.content.Context
import androidx.multidex.MultiDex
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import lt.code1.testair.logging.Logger
import javax.inject.Inject

open class App : DaggerApplication() {

    @Inject
    lateinit var logger: Logger

    override fun onCreate() {
        super.onCreate()
        logger.init()
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val appComponent = initializeComponent()
        appComponent.inject(this)
        return appComponent
    }

    open fun initializeComponent(): ApplicationComponent {
        return DaggerApplicationComponent.builder()
            .application(this)
            .build()
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}