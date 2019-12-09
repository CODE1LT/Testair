package lt.code1.testair.app

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

@Module
abstract class CoroutineScopeModule {

    @Module
    companion object {

        @Provides
        @JvmStatic
        fun provideCoroutineScope() = Dispatchers.Default + Job()
    }
}