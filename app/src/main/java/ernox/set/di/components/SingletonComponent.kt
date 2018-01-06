package ernox.set.di.components

import dagger.Component
import ernox.set.di.modules.AppModule
import ernox.set.di.modules.DatabaseModule
import ernox.set.di.modules.ViewModelModule
import ernox.set.views.GameActivity
import ernox.set.views.HighScoresActivity
import javax.inject.Singleton

/**
 * Created by Ernesto on 05/01/2018.
 */
@Singleton
@Component(modules = arrayOf(AppModule::class,
                             DatabaseModule::class,
                             ViewModelModule::class))
interface SingletonComponent {
    fun inject(activity: GameActivity)
    fun inject(activity: HighScoresActivity)
}