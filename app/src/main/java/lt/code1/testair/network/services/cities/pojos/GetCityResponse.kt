package lt.code1.testair.network.services.cities.pojos

import com.squareup.moshi.Json

data class GetCityResponse(
    @Json(name = "id") val id: Long?,
    @Json(name = "name") val name: String?,
    @Json(name = "dt") val dt: Long?,
    @Json(name = "main") val main: Main?,
    @Json(name = "weather") val weather: List<Weather>
)

data class Main (
    @Json(name = "temp") val temp: Float?,
    @Json(name = "temp_min") val temp_min: Float?,
    @Json(name = "temp_max") val temp_max: Float?
)

data class Weather (
    @Json(name = "description") val description: String?,
    @Json(name = "icon") val icon: String?
)
