@file:Suppress("unused")

package lt.code1.testair.extensions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.map
import com.snakydesign.livedataextensions.filter
import lt.code1.testair.archextensions.SingleResourceLiveData
import lt.code1.testair.datalayer.core.Resource

fun <T1, T2> MediatorLiveData<T1>.addSingleSource(
    singleResourceLiveData: SingleResourceLiveData<T2>,
    manageState: (T2) -> Unit
) {
    this.addSource(singleResourceLiveData) {
        if (it is Resource.Data<*> || it is Resource.Error<*>) {
            this.removeSource(singleResourceLiveData)
        }
        manageState(it)
    }
}

fun <T1, T2> MediatorLiveData<T1>.replaceSource( //TODO test
    oldSource: LiveData<T2>?,
    newSource: LiveData<T2>
): LiveData<T2> {
    oldSource?.let { removeSource(it) }
    return newSource
}

fun <T> LiveData<T?>.filterNonNull(): LiveData<T> =
    this.filter { it != null }.map { it!! }