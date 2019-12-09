package lt.code1.testair

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import timber.log.Timber
import javax.inject.Inject

class MainActivityViewModel @Inject constructor() : ViewModel(), FragmentsListener {

    val viewLiveData = MutableLiveData<MainActivityViewLiveData>().apply {
        value = MainActivityViewLiveData()
    }

    override fun showLoading() {
        Timber.d("showLoading()")
        viewLiveData.value?.loading = true
        viewLiveData.postValue(viewLiveData.value)
    }

    override fun hideLoading() {
        Timber.d("hideLoading()")
        viewLiveData.value?.loading = false
        viewLiveData.postValue(viewLiveData.value)
    }
}