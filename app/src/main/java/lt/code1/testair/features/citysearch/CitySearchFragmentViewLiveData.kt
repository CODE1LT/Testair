package lt.code1.testair.features.citysearch

import lt.code1.testair.archextensions.ViewData

data class CitySearchFragmentViewLiveData(
    var searchHint: String = "",
    var historyButtonTitle: String = "",
    var searchText: String = ""
) : ViewData