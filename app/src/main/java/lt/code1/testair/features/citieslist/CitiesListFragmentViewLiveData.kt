package lt.code1.testair.features.citieslist

import lt.code1.testair.archextensions.ViewData

data class CitiesListFragmentViewLiveData(
    var citiesList: List<Any>? = arrayListOf(),
    var dataIsLoading: Boolean = false
) : ViewData