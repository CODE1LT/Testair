package lt.code1.testair.datalayer

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import lt.code1.testair.app.ApplicationScope
import lt.code1.testair.datalayer.cities.CitiesRepositoryModule

@Module(
    includes = [
        CitiesRepositoryModule::class
    ]
)
class RepositoriesModule {

    @Provides
    @ApplicationScope
    fun provideDatabase(
        context: Context
    ) = Room
        .databaseBuilder(
            context,
            Database::class.java,
            Database::class.java.simpleName
        )
        .fallbackToDestructiveMigration()
        .build()
}