package lt.code1.testair.extensions

import com.google.common.truth.Truth.assertThat
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.fail
import org.junit.Test
import retrofit2.Response

class RetrofitExtTest {

    @Test
    fun `Response_unwrap unwraps response when response is successful`() {
        val expectedResponse = TestResponse("1")
        val successResponse = Response.success(expectedResponse)
        val actualResponse = successResponse.unwrap()
        assertThat(actualResponse).isEqualTo(expectedResponse)
    }

    @Test
    fun `Response_unwrap throws exception when response is unsuccessful`() {
        val errorResponse = Response.error<TestResponse>(401, "".toResponseBody(null))
        try {
            errorResponse.unwrap()
            fail("Mapper should have thrown exception")
        } catch (e: Exception) {
            //success
        }
    }
}