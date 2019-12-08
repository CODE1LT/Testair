package lt.code1.testair.network.services.cities.mock

import android.content.Context
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import lt.code1.testair.network.services.cities.external.GetCityService
import lt.code1.testair.network.services.cities.pojos.GetCityResponse
import retrofit2.Response
import java.lang.reflect.ParameterizedType
import javax.inject.Inject

class GetCityServiceMock @Inject constructor(
    val context: Context,
    val moshi: Moshi
) : GetCityService, MockService<GetCityResponse>(context) {

    override val fileName = "api-response/response_get_city.json"
    override val type: ParameterizedType = Types.newParameterizedType(
        GetCityResponse::class.java
    )

    override suspend fun getCity(
        cityAndCountry: String,
        units: String,
        apiKey: String
    ): Response<GetCityResponse> = parse()
}