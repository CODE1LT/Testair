package lt.code1.testair.app

import dagger.Module
import dagger.android.ContributesAndroidInjector
import lt.code1.testair.MainActivity
import lt.code1.testair.MainActivityModule

@Module
abstract class ActivityBuilderModule {

    @ContributesAndroidInjector(modules = [MainActivityModule::class, FragmentsBuilderModule::class])
    @ActivityScope
    abstract fun bindMainActivity(): MainActivity
}
