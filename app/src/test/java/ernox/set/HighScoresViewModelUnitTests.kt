package ernox.set

import android.arch.core.executor.testing.InstantTaskExecutorRule
import ernox.set.database.dao.HighScoreDao
import ernox.set.database.tables.HighScore
import ernox.set.viewModels.HighScoresViewModel
import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

/**
 * Created by Ernesto on 30/11/2017.
 */
class HighScoresViewModelUnitTests {

    @Rule
    @JvmField
    var rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var highScoreDao: HighScoreDao

    private lateinit var viewModel: HighScoresViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        viewModel = HighScoresViewModel()
        viewModel.setHighScoreDao(highScoreDao)
    }

    @Test
    fun should_get10HigherScores_onResume(){

        runBlocking {
            // Act
            viewModel.onResume()
        }

        // Setup
        verify(highScoreDao).getItems(10)
    }

    @Test
    fun should_showList_When_HighscoresAreFound(){

        // Setup
        Mockito.`when`(highScoreDao.getItems(10)).thenReturn(arrayOf(HighScore(10)))

        runBlocking {
            // Act
            viewModel.onResume()
        }

        // Setup
        assertTrue(viewModel.isListVisible.get())
    }

    @Test
    fun should_hideErrorMessage_When_HighscoresAreFound(){

        // Setup
        Mockito.`when`(highScoreDao.getItems(10)).thenReturn(arrayOf(HighScore(10)))

        runBlocking {
            // Act
            viewModel.onResume()
        }

        // Setup
        assertFalse(viewModel.isEmptyMessageVisible.get())
    }


    @Test
    fun should_showErrorMessage_When_NoHighscoresAreFound(){

        // Setup
        Mockito.`when`(highScoreDao.getItems(10)).thenReturn(arrayOf())

        runBlocking {
            // Act
            viewModel.onResume()
        }

        // Setup
        assertTrue(viewModel.isEmptyMessageVisible.get())
    }

    @Test
    fun should_hideList_When_NoHighscoresAreFound(){

        // Setup
        Mockito.`when`(highScoreDao.getItems(10)).thenReturn(arrayOf())

        runBlocking {
            // Act
            viewModel.onResume()
        }

        // Setup
        assertFalse(viewModel.isListVisible.get())
    }
}