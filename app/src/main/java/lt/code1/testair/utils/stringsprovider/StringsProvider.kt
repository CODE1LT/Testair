package lt.code1.testair.utils.stringsprovider

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

abstract class StringsProvider {

    abstract fun getString(stringId: StringId): LiveData<String>

    fun <T> addToLiveDataSource(
        viewLiveData: MediatorLiveData<T>,
        strindId: StringId,
        textGot: (String) -> Unit
    ) {
        viewLiveData.addSource(getString(strindId)) {
            textGot(it)
            viewLiveData.postValue(viewLiveData.value)
        }
    }
}