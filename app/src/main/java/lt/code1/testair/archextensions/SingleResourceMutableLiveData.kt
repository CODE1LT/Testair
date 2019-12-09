@file:Suppress("unused")

package lt.code1.testair.archextensions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class SingleResourceMutableLiveData<T>(
    liveData: LiveData<T> = MutableLiveData()
) : SingleResourceLiveData<T>(liveData) {

    private val mutableLiveData = liveData as MutableLiveData

    @Deprecated("Shouldn't call setValue directly. Will cause crash. Use setSingleValue instead")
    @Suppress("DEPRECATION", "DeprecatedCallableAddReplaceWith")
    override fun setValue(value: T?) {
        super.setValue(value)
    }

    @Deprecated("Shouldn't call postValue directly. Will cause crash. Use postSingleValue instead")
    @Suppress("DEPRECATION", "DeprecatedCallableAddReplaceWith")
    override fun postValue(value: T) {
        super.postValue(value)
    }

    fun setSingleValue(value: T?) {
        mutableLiveData.value = value
    }

    fun postSingleValue(value: T) {
        mutableLiveData.postValue(value)
    }
}