package lt.code1.testair.datalayer.cities

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import lt.code1.testair.archextensions.*
import lt.code1.testair.datalayer.cities.entities.CitiesListEntity
import lt.code1.testair.datalayer.cities.entities.CityEntity
import lt.code1.testair.datalayer.core.*
import lt.code1.testair.extensions.unwrap
import lt.code1.testair.network.services.cities.external.GetCitiesListService
import lt.code1.testair.network.services.cities.external.GetCityService
import lt.code1.testair.network.services.cities.pojos.GetCitiesListResponse
import lt.code1.testair.network.services.cities.pojos.GetCityResponse
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

private const val COUNTRY_CODE = ",LTU"

interface CitiesRepository {

    fun fetchCity(city: String): SingleResourceLiveData<Resource<@JvmSuppressWildcards List<CitiesListEntity>>>

    fun getCitiesList(): SingleResourceLiveData<Resource<@JvmSuppressWildcards List<CitiesListEntity>>>

}

class CitiesRepositoryImpl @Inject constructor(
    private val dao: CitiesDao,
    private val getCityService: GetCityService,
    private val getCitiesListService: GetCitiesListService,
    private val fetchCityRequestMediator: RequestMediator<GetCityResponse>,
    private val fetchCitiesListRequestMediator: RequestMediator<GetCitiesListResponse>,
    private val cityEntityMapper: @JvmSuppressWildcards Function1<GetCityResponse, @JvmSuppressWildcards List<CitiesListEntity>>,
    private val citiesListResponseMapper: @JvmSuppressWildcards Function1<GetCitiesListResponse, @JvmSuppressWildcards List<CitiesListEntity>>,
    private val coroutineContext: CoroutineContext
) : CitiesRepository {

    override fun fetchCity(city: String) = fetchCityRequestMediator
        .request {
            getCityService.getCity(city + COUNTRY_CODE)
                .unwrap()
        }
        .mapSingleResource(cityEntityMapper)
        .mapSingleResource {
            saveCityId(it)
            it
        }

    private fun saveCityId(citiesList: List<CitiesListEntity>) {
        if (citiesList[0].id != null) {
            CoroutineScope(coroutineContext).launch {
                dao.addCity(
                    CityEntity(
                        rowId = System.currentTimeMillis() / 1000L,
                        name = citiesList[0].name,
                        cityId = citiesList[0].id
                    )
                )

                dao.cleanupSearchHistory()
            }
        }
    }

    override fun getCitiesList() = fetchCitiesListRequestMediator
        .request {
            val citiesIds =
                dao.queryCities()?.map { it.cityId }?.joinToString()?.replace("\\s".toRegex(), "")
            citiesIds?.let { getCitiesListService.getCitiesList(it).unwrap() }
                ?: GetCitiesListResponse(listOf())
        }
        .mapSingleResource(citiesListResponseMapper)

}