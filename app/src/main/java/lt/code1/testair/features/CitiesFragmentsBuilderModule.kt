package lt.code1.testair.features

import dagger.Module
import dagger.android.ContributesAndroidInjector
import lt.code1.testair.app.FragmentScope
import lt.code1.testair.features.citysearch.CitySearchFragment
import lt.code1.testair.features.citysearch.CitySearchFragmentModule


@Module
abstract class CitiesFragmentsBuilderModule {
    @ContributesAndroidInjector(modules = [CitySearchFragmentModule::class])
    @FragmentScope
    internal abstract fun bindUsersListFragment(): CitySearchFragment

    @ContributesAndroidInjector(modules = [CitiesListFragmentModule::class])
    @FragmentScope
    internal abstract fun bindUserDetailsFragment(): CitiesListFragment
}