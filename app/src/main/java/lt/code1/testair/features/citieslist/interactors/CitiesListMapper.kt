package lt.code1.testair.features.citieslist.interactors

import lt.code1.testair.datalayer.cities.entities.CitiesListEntity
import lt.code1.testair.features.citieslist.data.City
import javax.inject.Inject

class CitiesListMapper @Inject constructor() :
    @JvmSuppressWildcards Function1<@JvmSuppressWildcards List<CitiesListEntity>, @JvmSuppressWildcards List<City>> {

    override fun invoke(citiesList: List<CitiesListEntity>): List<City> {

        val citiesListMapped = arrayListOf<City>()
        citiesList.forEach { citiesListItem ->
            citiesListMapped.add(
                City(
                    id = citiesListItem.id,
                    name = citiesListItem.name,
                    dt = citiesListItem.dt,
                    temp = citiesListItem.temp,
                    temp_min = citiesListItem.temp_min,
                    temp_max = citiesListItem.temp_max,
                    icon = citiesListItem.icon,
                    description = citiesListItem.description
                )
            )
        }

        return citiesListMapped
    }
}