package lt.code1.testair.datalayer

import androidx.lifecycle.MutableLiveData
import org.mockito.Mockito

class TestEntityMapper : Function1<TestResponse, TestEntity> {
    override fun invoke(testResponse: TestResponse) = TestEntity(testResponse.id!!)
}

class TestEntityListMapper : Function1<List<TestResponse>, List<TestEntity>> {
    override fun invoke(testResponseList: List<TestResponse>): List<TestEntity> {
        return testResponseList
            .map { TestEntity(it.id!!) }
    }
}

open class FakeDataSource<T>(
    private val mutableLiveData: MutableLiveData<T> = MutableLiveData()
) {

    fun query() = mutableLiveData
    open fun replace(value: T) = mutableLiveData.postValue(value)
}

fun <T> FakeDataSource<T>.spy(): FakeDataSource<T> = Mockito.spy(this)

fun MutableLiveData<TestEntity>.withInitialTestEntities(initialValue: TestEntity) =
    this.apply { value = initialValue }