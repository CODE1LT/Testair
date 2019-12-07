package lt.code1.testair.features.citysearch

import androidx.lifecycle.ViewModel
import lt.code1.testair.archextensions.SingleLiveEvent
import lt.code1.testair.archextensions.ViewLiveData
import lt.code1.testair.utils.stringsprovider.StringId
import lt.code1.testair.utils.stringsprovider.StringsProvider
import javax.inject.Inject

class CitySearchFragmentViewModel @Inject constructor(
    private val stringsProvider: StringsProvider
) : ViewModel() {

    val viewLiveData = ViewLiveData(CitySearchFragmentViewLiveData())
    private val viewLiveDataValue = viewLiveData.value
    val cityNameSubmitedEvent = SingleLiveEvent<String>()

    init {
        setHistoryButtonText()
    }
    
    private fun setHistoryButtonText() {
        stringsProvider.addToLiveDataSource(viewLiveData, StringId.HISTORY_BUTTON_TEXT) {
            viewLiveDataValue.historyButtonTitle = it
        }
    }

    fun onSearchButtonClicked() {
        cityNameSubmitedEvent.postValue(viewLiveDataValue.searchText)
    }
}