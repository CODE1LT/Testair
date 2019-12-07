package lt.code1.testair.datalayer.cities

import lt.code1.testair.archextensions.*
import lt.code1.testair.datalayer.cities.entities.CitiesListEntity
import lt.code1.testair.datalayer.core.*
import lt.code1.testair.extensions.unwrap
import lt.code1.testair.network.services.cities.external.GetCityService
import lt.code1.testair.network.services.cities.pojos.GetCityResponse
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

interface CitiesRepository {

    fun fetchCity(cityAndCountry: String): SingleResourceLiveData<Resource<@JvmSuppressWildcards List<CitiesListEntity>>>

}

class CitiesRepositoryImpl @Inject constructor(
    private val getCityService: GetCityService,
    private val fetchCityRequestMediator: RequestMediator<GetCityResponse>,
    private val cityEntityMapper: @JvmSuppressWildcards Function1<GetCityResponse, @JvmSuppressWildcards List<CitiesListEntity>>,
    private val coroutineContext: CoroutineContext
) : CitiesRepository {

    override fun fetchCity(cityAndCountry: String) = fetchCityRequestMediator
        .request {
            getCityService.getCity(cityAndCountry)
                .unwrap()
        }
        .mapSingleResource(cityEntityMapper)
//        .mapSingleResource {
//            clearUsersAndAddDataInfoWithUsers(it)
//        }

//    private fun clearUsersAndAddDataInfoWithUsers(dataInfoWithUsersEntity: DataInfoWithUsersEntity) {
//        CoroutineScope(coroutineContext).launch {
//            dao.clearUsersAndAddDataInfoWithUsers(dataInfoWithUsersEntity)
//        }
//    }
}