@file:Suppress("unused")

package lt.code1.testair.datalayer.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.map

sealed class Resource<T>(open val data: T?) {
    data class Data<T>(override val data: T) : Resource<T>(data)
    data class Loading<T>(override val data: T? = null) : Resource<T>(data)
    data class Error<T>(val throwable: Throwable = Exception(), override val data: T? = null) :
        Resource<T>(data)
}

fun <T1, T2> LiveData<Resource<T1>>.mapResource(map: (T1) -> T2) = map { it.map(map) }

fun <T1, T2> Resource<T1>.flatMap(map: (T1?) -> Resource<T2>) = map(data)

fun <T1, T2> LiveData<Resource<T1>>.flatMapResource(map: (T1?) -> Resource<T2>) =
    map { it.flatMap(map) }

fun <T1, T2> Resource<T1>.map(map: (T1) -> T2): Resource<T2> =
    try {
        when (this) {
            is Resource.Data<T1> -> {
                Resource.Data(map(data))
            }
            is Resource.Loading<T1> -> {
                Resource.Loading(data?.let { map(it) })
            }
            is Resource.Error<T1> -> {
                Resource.Error(this.throwable, data?.let { map(it) })
            }
        }
    } catch (e: Exception) {
        Resource.Error(MapException(e.message), null)
    }

fun <T1, T2> Resource<T1>.match(
    data: (T1) -> T2,
    loading: (T1?) -> T2,
    error: (Throwable, T1?) -> T2
): T2 {
    return when (this) {
        is Resource.Data<T1> -> data(this.data)
        is Resource.Loading<T1> -> loading(this.data)
        is Resource.Error<T1> -> error(this.throwable, this.data)
    }
}