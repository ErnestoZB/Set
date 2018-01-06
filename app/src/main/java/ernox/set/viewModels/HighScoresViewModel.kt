package ernox.set.viewModels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import ernox.set.database.dao.HighScoreDao
import ernox.set.database.tables.HighScore
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg
import javax.inject.Inject

/**
 * Created by Ernesto on 25/11/2017.
 */

class HighScoresViewModel @Inject constructor(private val highScoreDao: HighScoreDao) : ViewModel() {

    val isEmptyMessageVisible: ObservableField<Boolean> = ObservableField(false)

    val isListVisible: ObservableField<Boolean> = ObservableField(false)

    private val highScores : MutableLiveData<Array<HighScore>> = MutableLiveData()
    fun getHighScores() : LiveData<Array<HighScore>> = highScores

    fun onResume() {
        async(UI) {
            isEmptyMessageVisible.set(false)
            isListVisible.set(false)

            val data = bg { getHighScoresFromDatabase() }
            highScores.value = data.await()

            if(highScores.value?.isNotEmpty()!!) {
                isListVisible.set(true)
            }
            else {
                isEmptyMessageVisible.set(true)
            }
        }
    }

    private fun getHighScoresFromDatabase() : Array<HighScore> {
        return highScoreDao.getItems(10)
    }
}