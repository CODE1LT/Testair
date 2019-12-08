package lt.code1.testair.network.services.cities.pojos

import com.squareup.moshi.Json

data class GetCitiesListResponse(
    @Json(name = "list") val list: List<GetCityResponse>?
)
