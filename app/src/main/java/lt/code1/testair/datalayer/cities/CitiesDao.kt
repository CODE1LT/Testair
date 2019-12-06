package lt.code1.testair.datalayer.cities

import androidx.room.*
import lt.code1.testair.datalayer.cities.entities.CityEntity

@Dao
abstract class CitiesDao {

    @Query("SELECT * FROM cities")
    abstract fun queryCities(): CityEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract fun addCity(city: CityEntity)

    @Query("DELETE FROM cities WHERE rowId NOT IN (SELECT rowId FROM cities ORDER BY rowId DESC LIMIT 5)")
    abstract fun deleteUser(id: Int)

}