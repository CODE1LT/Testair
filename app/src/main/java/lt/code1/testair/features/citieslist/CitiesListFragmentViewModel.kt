package lt.code1.testair.features.citieslist

import androidx.lifecycle.ViewModel
import lt.code1.testair.archextensions.SingleLiveEvent
import lt.code1.testair.archextensions.ViewLiveData
import lt.code1.testair.utils.stringsprovider.StringId
import lt.code1.testair.utils.stringsprovider.StringsProvider
import javax.inject.Inject

class CitiesListFragmentViewModel @Inject constructor(
    private val stringsProvider: StringsProvider
) : ViewModel() {

    val viewLiveData = ViewLiveData(CitiesListFragmentViewLiveData())
    private val viewLiveDataValue = viewLiveData.value




}