package ernox.set.views

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import ernox.set.R
import ernox.set.database.AppDatabase
import ernox.set.databinding.ActivityGameBinding
import ernox.set.viewModels.GameViewModel

class GameActivity : AppCompatActivity() {

    private lateinit var viewModel: GameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityGameBinding = DataBindingUtil.setContentView(this, R.layout.activity_game)

        viewModel = ViewModelProviders.of(this).get(GameViewModel::class.java)
        viewModel.setHighScoreDao(AppDatabase.getHighScoreDao())
        binding.viewModel = viewModel

        setErrorListener()
    }

    override fun onStart() {
        super.onStart()

        viewModel.onStartGame()
    }

    private fun setErrorListener() {
        viewModel.getErrorMessageId().observe(this, Observer {
            it?.let { showSetError(it) }
        })
    }

    private fun showSetError(messageId: Int) {
        Toast.makeText(applicationContext, messageId, Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.game, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_restart -> viewModel.onRestartGame()
            R.id.action_clue -> viewModel.onShowHint()
            R.id.action_highscores -> launchHighScoresView()
            else -> return super.onOptionsItemSelected(item)
        }

        return true
    }

    private fun launchHighScoresView() {
        val highScoresIntent = Intent(this, HighScoresActivity::class.java)
        startActivity(highScoresIntent)
    }
}