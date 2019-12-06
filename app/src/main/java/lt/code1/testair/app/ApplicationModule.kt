package lt.code1.testair.app

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module

@Module(includes = [StringsProviderModule::class])
abstract class ApplicationModule {

    @Binds
    abstract fun provideContext(application: Application): Context
}