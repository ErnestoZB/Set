package ernox.set.views

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import ernox.set.R
import ernox.set.adapters.HighScoresAdapter
import ernox.set.database.AppDatabase
import ernox.set.databinding.ActivityHighscoresBinding
import ernox.set.viewModels.HighScoresViewModel
import kotlinx.android.synthetic.main.activity_highscores.*

class HighScoresActivity : AppCompatActivity() {

    private lateinit var viewModel: HighScoresViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableHomeAsUp()

        viewModel = ViewModelProviders.of(this).get(HighScoresViewModel::class.java)
        viewModel.setHighScoreDao(AppDatabase.getHighScoreDao())

        val binding: ActivityHighscoresBinding = DataBindingUtil.setContentView(this, R.layout.activity_highscores)
        binding.viewModel = viewModel

        setHighScoresListener()
    }

    private fun setHighScoresListener() {
        viewModel.getHighScores().observe(this, Observer {
            if(it != null) {
                highScores_list.layoutManager = LinearLayoutManager(applicationContext)
                highScores_list.adapter = HighScoresAdapter(it)
            }
        })
    }

    override fun onResume() {
        super.onResume()

        viewModel.onResume()
    }

    private fun enableHomeAsUp() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        if (item.getItemId() === android.R.id.home)
        {
            finish()
        }

        return super.onOptionsItemSelected(item)
    }
}
