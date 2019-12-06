package lt.code1.testair.app

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {

    @ContributesAndroidInjector(modules = [MainActivityModule::class, FragmentsBuilderModule::class])
    @ActivityScope
    abstract fun bindMainActivity(): MainActivity
}
