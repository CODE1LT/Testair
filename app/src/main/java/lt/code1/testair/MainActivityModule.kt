@file:Suppress("unused")
package lt.code1.testair

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import lt.code1.testair.app.ActivityScope
import lt.code1.testair.archextensions.LifecycleViewModelModule
import lt.code1.testair.archextensions.ViewModelKey
import javax.inject.Named

@Module(includes = [LifecycleViewModelModule::class])
abstract class MainActivityModule {

    @Module
    companion object {
        private const val MAIN_ACTIVITY_MODEL = "MAIN_ACTIVITY_MODEL"

        @Provides
        @JvmStatic
        @ActivityScope
        @Named(MAIN_ACTIVITY_MODEL)
        fun provideMainActivityViewModel(mainActivityViewModel: MainActivityViewModel)
                : MainActivityViewModel {
            return mainActivityViewModel
        }
    }

    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    abstract fun provideViewModel(@Named(MAIN_ACTIVITY_MODEL) mainActivityViewModel: MainActivityViewModel): ViewModel

    @Binds
    abstract fun provideListener(
        @Named(MAIN_ACTIVITY_MODEL) mainActivityViewModel: MainActivityViewModel
    ): FragmentsListener
}