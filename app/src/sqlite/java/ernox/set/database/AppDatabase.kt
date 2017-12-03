package ernox.set.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import ernox.set.database.dao.HighScoreDao
import ernox.set.database.tables.HighScore

/**
 * Created by Ernesto on 30/11/2017.
 */
@Database(version = 1,
          entities = arrayOf(HighScore::class))
abstract class AppDatabase : RoomDatabase() {

    abstract fun highScoreDao(): HighScoreDao

    companion object : IDatabase {

        private lateinit var database: AppDatabase
        override fun init(context: Context) {
            database = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "issstecali").build()
        }

        override fun getHighScoreDao(): HighScoreDao {
            return database.highScoreDao()
        }
    }
}