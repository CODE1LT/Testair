package lt.code1.testair.extensions

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.distinctUntilChanged
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

@Suppress("UNCHECKED_CAST")
class LiveDataExtKtTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private val testString = "testString"
    private val testString2 = "testString2"
    private val testObject1 = TestObject1(testString)
    private val testObject12 = TestObject1(testString2)
    private val testObject2 = TestObject2(testString)

    @Test
    fun `LiveData_getDistinct emits only distinct values when values are the same`() {
        val observer = Mockito.mock(Observer::class.java) as Observer<TestObject1>
        val liveData = MutableLiveData<TestObject1>()
            .apply { value = testObject1 }
        val liveDataDistinct = liveData.distinctUntilChanged()

        liveDataDistinct.observeForever(observer)
        liveData.postValue(testObject1)

        Mockito.verify(observer)
            .onChanged(testObject1)
    }

    @Test
    fun `LiveData_getDistinct emits only distinct values when values differ`() {
        val observer = Mockito.mock(Observer::class.java) as Observer<TestObject1>
        val liveData = MutableLiveData<TestObject1>()
            .apply { value = testObject1 }
        val liveDataDistinct = liveData.distinctUntilChanged()

        liveDataDistinct.observeForever(observer)
        liveData.postValue(testObject12)

        val inOrder = Mockito.inOrder(observer)
        inOrder.verify(observer)
            .onChanged(testObject1)
        inOrder.verify(observer)
            .onChanged(testObject12)
    }
}