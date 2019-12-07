package lt.code1.testair.datalayer.cities.entities

data class CitiesListEntity(
    val id: Long?,
    val name: String?,
    val dt: Long?,
    val temp: Float?,
    val temp_min: Float?,
    val temp_max: Float?,
    val icon: String?,
    val description: String?
)