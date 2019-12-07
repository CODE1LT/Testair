package lt.code1.testair.app

import dagger.Module
import dagger.android.ContributesAndroidInjector
import lt.code1.testair.MainActivity
import lt.code1.testair.MainActivityModule
import lt.code1.testair.features.CitiesFragmentsBuilderModule

@Module
abstract class ActivityBuilderModule {

    @ContributesAndroidInjector(modules = [MainActivityModule::class, CitiesFragmentsBuilderModule::class])
    @ActivityScope
    abstract fun bindMainActivity(): MainActivity
}
