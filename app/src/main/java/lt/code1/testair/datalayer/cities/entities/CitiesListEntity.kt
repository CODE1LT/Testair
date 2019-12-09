package lt.code1.testair.datalayer.cities.entities

import lt.code1.testair.network.services.cities.pojos.Weather

data class CitiesListEntity(
    val id: Long?,
    val name: String?,
    val dt: Long?,
    val temp: Float?,
    val temp_min: Float?,
    val temp_max: Float?,
    val icon: String?,
    val description: List<Weather>?
)