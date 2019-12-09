@file:Suppress("unused")
package lt.code1.testair.utils.stringsprovider

import dagger.Binds
import dagger.Module

@Module
abstract class StringsProviderModule {

    @Binds
    abstract fun provideStringsProvider(localStringsProvider: LocalStringsProvider): StringsProvider
}