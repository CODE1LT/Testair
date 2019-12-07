package lt.code1.testair.features.citieslist

import lt.code1.testair.archextensions.ViewData

data class CitiesListFragmentViewLiveData(
    var searchHint: String = "",
    var historyButtonTitle: String = "",
    var searchText: String = ""
) : ViewData