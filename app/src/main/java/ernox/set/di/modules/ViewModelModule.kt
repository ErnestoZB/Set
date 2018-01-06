package ernox.set.di.modules

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ernox.set.di.ViewModelFactory
import ernox.set.di.ViewModelKey
import ernox.set.viewModels.GameViewModel
import ernox.set.viewModels.HighScoresViewModel

/**
 * Created by Ernesto on 05/01/2018.
 */
@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(GameViewModel::class)
    abstract fun bindGameViewModel(gameViewModel: GameViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HighScoresViewModel::class)
    abstract fun bindHighScoresViewModel(highScoresViewModel: HighScoresViewModel) : ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}