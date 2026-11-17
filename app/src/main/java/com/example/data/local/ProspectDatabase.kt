package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        WordMasteryEntity::class,
        LessonProgressEntity::class,
        ExerciseAttemptEntity::class,
        MistakeLogEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class ProspectDatabase : RoomDatabase() {
    abstract fun prospectDao(): ProspectDao

    companion object {
        @Volatile
        private var INSTANCE: ProspectDatabase? = null

        fun getInstance(context: Context): ProspectDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    ProspectDatabase::class.java,
                    "prospect2_tutor.db"
                ).build().also { INSTANCE = it }
            }
        }
    }
}
