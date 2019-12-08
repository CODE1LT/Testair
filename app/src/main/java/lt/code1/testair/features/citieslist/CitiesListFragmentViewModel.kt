package lt.code1.testair.features.citieslist

import androidx.lifecycle.ViewModel
import lt.code1.testair.FragmentsListener
import lt.code1.testair.R
import lt.code1.testair.archextensions.SingleLiveEvent
import lt.code1.testair.archextensions.ViewLiveData
import lt.code1.testair.datalayer.core.Resource
import lt.code1.testair.domain.RetrieveSingleInteractor
import lt.code1.testair.domain.RetrieveSingleInteractorWithParams
import lt.code1.testair.features.citieslist.data.City
import lt.code1.testair.features.shared.Notification
import javax.inject.Inject

class CitiesListFragmentViewModel @Inject constructor(
    private val fragmentsListener: FragmentsListener,
    private val fetchCityInteractor: RetrieveSingleInteractorWithParams<String, Resource<@JvmSuppressWildcards List<City>>>,
    private val getCitiesListInteractor: RetrieveSingleInteractor<Resource<@JvmSuppressWildcards List<City>>>
) : ViewModel() {

    val viewLiveData = ViewLiveData(CitiesListFragmentViewLiveData())
    private val viewLiveDataValue = viewLiveData.value
    val notificationEvent = SingleLiveEvent<Notification>()

    fun getCity(cityAndCountryName: String) {
        viewLiveData.addSingleResourceSource(
            fetchCityInteractor.getSingle(cityAndCountryName),
            { manageFetchCityDataState(it) },
            { manageFetchCityLoadingState() },
            { error, _ -> manageFetchCityErrorState(error) }
        )
        viewLiveData.notifyLiveDataObservers()
    }

    fun getSearchHistory() {
        viewLiveData.addSingleResourceSource(
            getCitiesListInteractor.getSingle(),
            { manageFetchCityDataState(it) },
            { manageFetchCityLoadingState() },
            { error, _ -> manageFetchCityErrorState(error) }
        )
        viewLiveData.notifyLiveDataObservers()
    }

    private fun manageFetchCityLoadingState() {
        viewLiveDataValue.dataIsLoading = true
        handleLoading()
    }

    private fun manageFetchCityDataState(citiesList: List<City>) {
        viewLiveDataValue.dataIsLoading = false
        handleLoading()
        citiesList
            .let { viewLiveDataValue.citiesList = it }
        viewLiveData.notifyLiveDataObservers()
    }

    private fun manageFetchCityErrorState(error: Throwable) {
        viewLiveDataValue.dataIsLoading = false
        handleLoading()
        showError(error.message.toString())
    }


    private fun showError(errorMessage: String) {
        notificationEvent.postValue(
            Notification(
                false,
                R.string.f_cities_list_tst_completed_successfully_text,
                errorMessage
            )
        )
    }

    private fun handleLoading() {
        if (viewLiveDataValue.dataIsLoading) {
            fragmentsListener.showLoading()
        } else {
            fragmentsListener.hideLoading()
        }
    }

}