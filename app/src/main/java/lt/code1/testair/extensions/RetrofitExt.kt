@file:Suppress("unused")

package lt.code1.testair.extensions

import retrofit2.HttpException
import retrofit2.Response

fun <T> Response<T>.unwrap(): T {
    if (this.isSuccessful) {
        return this.body()!!
    } else {
        throw HttpException(this)
    }
}