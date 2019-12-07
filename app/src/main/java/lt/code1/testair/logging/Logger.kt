package lt.code1.testair.logging

import android.util.Log
import dagger.Module
import dagger.Provides
import lt.code1.testair.BuildConfig
import lt.code1.testair.app.ConfigurationDimension
import timber.log.Timber
import javax.inject.Inject

@Module
class LoggerModule {

    @Provides
    fun provideLogger(): Logger {
        return if (BuildConfig.CONFIGURATION_TYPE == ConfigurationDimension.DEV) {
            DebugLogger()
        } else {
            ExceptionLogger()
        }
    }
}

interface Logger {
    fun init()
}

abstract class TimberLogger : Timber.DebugTree(), Logger {
    override fun init() {
        Timber.plant(this)
    }
}

class DebugLogger @Inject constructor() : TimberLogger() {
    override fun log(priority: Int, tag: String?, message: String, throwable: Throwable?) {
        super.log(priority, tag, message, throwable)
        logExceptionToCrashlyticsIfAny(priority, throwable)
    }
}

class ExceptionLogger @Inject constructor() : TimberLogger() {
    override fun log(priority: Int, tag: String?, message: String, throwable: Throwable?) {
        logExceptionToCrashlyticsIfAny(priority, throwable)
    }
}

fun logExceptionToCrashlyticsIfAny(priority: Int, throwable: Throwable?) {
    if (priority == Log.ERROR && throwable != null) {
        //TODO implement if needed
        //Crashlytics.logException(throwable)
    }
}