package ernox.set.di

import dagger.MapKey
import kotlin.reflect.KClass
import android.arch.lifecycle.ViewModel

/**
 * Created by Ernesto on 05/01/2018.
 */
@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
internal annotation class ViewModelKey(val value: KClass<out ViewModel>)