package lt.code1.testair.datalayer.cities.mapper

import lt.code1.testair.datalayer.cities.entities.CitiesListEntity
import lt.code1.testair.network.services.cities.pojos.GetCitiesListResponse
import javax.inject.Inject

class CitiesListResponseMapper @Inject constructor() :
    Function1<GetCitiesListResponse, @JvmSuppressWildcards List<CitiesListEntity>> {

    override fun invoke(getCitiesListResponse: GetCitiesListResponse): @JvmSuppressWildcards List<CitiesListEntity> {
        val citiesList = mutableListOf<CitiesListEntity>()
        getCitiesListResponse.list?.forEach {
            citiesList.add(
                CitiesListEntity(
                    id = it.id,
                    name = it.name,
                    dt = it.dt,
                    temp = it.main?.temp,
                    temp_min = it.main?.temp_min,
                    temp_max = it.main?.temp_max,
                    icon = it.weather?.get(0)?.icon,
                    description = it.weather)
            )
        }
        return citiesList
    }
}
