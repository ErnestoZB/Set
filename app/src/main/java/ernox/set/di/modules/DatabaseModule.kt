package ernox.set.di.modules

import dagger.Module
import dagger.Provides
import ernox.set.database.AppDatabase
import javax.inject.Singleton

/**
 * Created by Ernesto on 05/01/2018.
 */
@Module
class DatabaseModule  {
    @Provides
    @Singleton
    fun providesHighScoreDao() = AppDatabase.getHighScoreDao()
}