package lt.code1.testair.features.citysearch

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import lt.code1.testair.archextensions.ViewModelKey
import javax.inject.Named

@Module
abstract class CitySearchFragmentModule {

    @Module
    companion object {
        private const val CITY_SEARCH_FRAGMENT_VIEW_MODEL = "CITY_SEARCH_FRAGMENT_VIEW_MODEL"

        @Provides
        @JvmStatic
        @Named(CITY_SEARCH_FRAGMENT_VIEW_MODEL)
        fun provideFragmentViewModel(citySearchFragmentViewModel: CitySearchFragmentViewModel)
                : CitySearchFragmentViewModel {
            return citySearchFragmentViewModel
        }
    }

    @Binds
    @IntoMap
    @ViewModelKey(CitySearchFragmentViewModel::class)
    abstract fun provideViewModel(@Named(CITY_SEARCH_FRAGMENT_VIEW_MODEL) citySearchFragmentViewModel: CitySearchFragmentViewModel): ViewModel
    
}