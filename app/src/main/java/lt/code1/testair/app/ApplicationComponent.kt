package lt.code1.testair.app

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjectionModule
import lt.code1.testair.datalayer.RepositoriesModule
import lt.code1.testair.network.NetworkModule

@ApplicationScope
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ApplicationModule::class,
        LoggerModule::class,
        CoroutineScopeModule::class,
        ActivityBuilderModule::class,
        RepositoriesModule::class,
        NetworkModule::class
    ]
)
interface ApplicationComponent : AndroidInjector<DaggerApplication> {

    fun inject(app: App)

    override fun inject(instance: DaggerApplication)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }
}