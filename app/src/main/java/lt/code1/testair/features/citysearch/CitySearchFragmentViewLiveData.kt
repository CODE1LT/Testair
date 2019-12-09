package lt.code1.testair.features.citysearch

import lt.code1.testair.archextensions.ViewData

data class CitySearchFragmentViewLiveData(
    var searchText: String = "",
    var historyButtonTitle: String = ""
) : ViewData