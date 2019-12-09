@file:Suppress("unused")

package lt.code1.testair.domain

import androidx.lifecycle.LiveData
import lt.code1.testair.archextensions.SingleResourceLiveData

interface RetrieveStreamInteractor<RETURN> {
    fun getStream(): LiveData<RETURN>
}

interface RetrieveStreamInteractorWithParams<PARAMS, RETURN> {
    fun getStream(params: PARAMS): LiveData<RETURN>
}

interface RetrieveSingleInteractor<RETURN> {
    fun getSingle(): SingleResourceLiveData<RETURN>
}

interface RetrieveSingleInteractorWithParams<PARAMS, RETURN> {
    fun getSingle(params: PARAMS): SingleResourceLiveData<RETURN>
}
