package lt.code1.testair.features.citieslist

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import lt.code1.testair.archextensions.ViewModelKey
import javax.inject.Named

@Module
abstract class CitiesListFragmentModule {

    @Module
    companion object {
        private const val CITIES_LIST_FRAGMENT_VIEW_MODEL = "CITIES_LIST_FRAGMENT_VIEW_MODEL"

        @Provides
        @JvmStatic
        @Named(CITIES_LIST_FRAGMENT_VIEW_MODEL)
        fun provideFragmentViewModel(citiesListFragmentViewModel: CitiesListFragmentViewModel)
                : CitiesListFragmentViewModel {
            return citiesListFragmentViewModel
        }
    }

    @Binds
    @IntoMap
    @ViewModelKey(CitiesListFragmentViewModel::class)
    abstract fun provideViewModel(@Named(CITIES_LIST_FRAGMENT_VIEW_MODEL) citiesListFragmentViewModel: CitiesListFragmentViewModel): ViewModel
    
}