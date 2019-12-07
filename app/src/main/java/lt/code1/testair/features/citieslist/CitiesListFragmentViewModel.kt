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

    val observer = Observer<Resource<List<City>>> { resource ->
        var tmp = resource
    }

    init {
        getCity("London,LTU")
    }


    fun getCity(cityAndCountryName: String) {
        viewLiveData.addSingleResourceSource(
            fetchCityInteractor.getSingle(cityAndCountryName),
            { it -> manageFetchCityDataState(it) },
            { manageFetchCityLoadingState() },
            { _, _ -> manageFetchCityErrorState() }
        )
        viewLiveData.notifyLiveDataObservers()
        fetchCityInteractor.getSingle(cityAndCountryName).observeForever(observer)
    }


    private fun manageFetchCityLoadingState() {
    }

    private fun manageFetchCityDataState(citiesList: List<City>) {
        var tmp = citiesList
    }

    private fun manageFetchCityErrorState() {
    }


}