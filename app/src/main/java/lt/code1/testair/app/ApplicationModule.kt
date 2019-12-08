package lt.code1.testair.app

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module
import lt.code1.testair.utils.stringsprovider.StringsProviderModule

@Module(includes = [StringsProviderModule::class])
abstract class ApplicationModule {

    @Suppress("unused")
    @Binds
    abstract fun provideContext(application: Application): Context
}