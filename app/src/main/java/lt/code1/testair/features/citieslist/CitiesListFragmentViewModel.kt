package lt.code1.testair.features.citieslist

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import lt.code1.testair.archextensions.ViewLiveData
import lt.code1.testair.datalayer.core.Resource
import lt.code1.testair.domain.RetrieveSingleInteractorWithParams
import lt.code1.testair.features.citieslist.data.City
import lt.code1.testair.utils.stringsprovider.StringsProvider
import javax.inject.Inject

class CitiesListFragmentViewModel @Inject constructor(
    private val fetchCityInteractor: RetrieveSingleInteractorWithParams<String, Resource<@JvmSuppressWildcards List<City>>>,
    private val stringsProvider: StringsProvider
) : ViewModel() {

    val viewLiveData = ViewLiveData(CitiesListFragmentViewLiveData())
    private val viewLiveDataValue = viewLiveData.value

    //TODO remove, for testing only
    val observer = Observer<CitiesListFragmentViewLiveData> {}

    fun getCity(cityAndCountryName: String) {
        viewLiveData.addSingleResourceSource(
            fetchCityInteractor.getSingle(cityAndCountryName),
            { it -> manageFetchCityDataState(it) },
            { manageFetchCityLoadingState() },
            { error, _ -> manageFetchCityErrorState(error) }
        )
        viewLiveData.notifyLiveDataObservers()
        viewLiveData.observeForever(observer)
    }


    private fun manageFetchCityLoadingState() {
        var tmp = 10
    }

    private fun manageFetchCityDataState(citiesList: List<City>) {
        var tmp = citiesList
    }

    private fun manageFetchCityErrorState(error: Throwable) {
        var tmp = error.message
    }


}