package lt.code1.testair.network.services.cities.external

import lt.code1.testair.network.APPID
import lt.code1.testair.network.services.cities.pojos.GetCitiesListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

const val CITIES_GROUP_PATH = "data/2.5/group"
private const val UNITS = "metric"

interface GetCitiesListService {

    @GET(CITIES_GROUP_PATH)
    suspend fun getCitiesList(
        @Query("id") citiesCodesList: String,
        @Query("units") units: String = UNITS,
        @Query("appid") apiKey: String = APPID
    ): Response<GetCitiesListResponse>
}