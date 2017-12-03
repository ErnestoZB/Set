package ernox.set.database.tables

import io.realm.RealmObject
import io.realm.annotations.Index

/**
 * Created by Ernesto on 30/11/2017.
 */
open class HighScore(@Index var highScore: Long = 0) : RealmObject()