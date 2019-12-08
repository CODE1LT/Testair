package lt.code1.testair.features.citieslist.interactors

import lt.code1.testair.datalayer.cities.entities.CitiesListEntity
import lt.code1.testair.features.citieslist.data.City
import lt.code1.testair.network.services.cities.pojos.Weather
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
                    temp = citiesListItem.temp?.toInt().toString(),
                    temp_min = citiesListItem.temp_min,
                    temp_max = citiesListItem.temp_max,
                    icon = citiesListItem.icon,
                    description = getDescription(citiesListItem.description)
                )
            )
        }

        return citiesListMapped
    }

    private fun getDescription(weatherList: List<Weather>?):String {
        val descriptionsList = weatherList?.map { it.description } as ArrayList
        return descriptionsList.joinToString()
    }

}