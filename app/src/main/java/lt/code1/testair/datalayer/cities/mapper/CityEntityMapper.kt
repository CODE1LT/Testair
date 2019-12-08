package lt.code1.testair.datalayer.cities.mapper

import lt.code1.testair.datalayer.cities.entities.CitiesListEntity
import lt.code1.testair.network.services.cities.pojos.GetCityResponse
import javax.inject.Inject

class CityEntityMapper @Inject constructor() :
    Function1<GetCityResponse, @JvmSuppressWildcards List<CitiesListEntity>> {

    override fun invoke(cityResponse: GetCityResponse): @JvmSuppressWildcards List<CitiesListEntity> {
        return listOf(
            CitiesListEntity(
                id = cityResponse.id,
                name = cityResponse.name,
                dt = cityResponse.dt,
                temp = cityResponse.main?.temp,
                temp_min = cityResponse.main?.temp_min,
                temp_max = cityResponse.main?.temp_max,
                icon = cityResponse.weather?.get(0)?.icon,
                description = cityResponse.weather
            )
        )
    }
}