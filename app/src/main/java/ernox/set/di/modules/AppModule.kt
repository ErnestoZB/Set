package ernox.set.di.modules

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Ernesto on 05/01/2018.
 */
@Module
class AppModule(private val app: Application) {
    @Provides
    @Singleton
    fun providesContext(): Context = app.applicationContext
}