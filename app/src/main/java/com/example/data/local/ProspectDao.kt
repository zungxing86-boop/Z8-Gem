package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ProspectDao {
    @Query("SELECT * FROM word_mastery")
    fun observeAllWordMastery(): Flow<List<WordMasteryEntity>>

    @Query("SELECT * FROM word_mastery WHERE wordId = :wordId LIMIT 1")
    suspend fun getWordMastery(wordId: String): WordMasteryEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertWordMastery(entity: WordMasteryEntity)

    @Query("SELECT * FROM lesson_progress ORDER BY lessonId ASC")
    fun observeAllLessonProgress(): Flow<List<LessonProgressEntity>>

    @Query("SELECT * FROM lesson_progress WHERE lessonId = :lessonId LIMIT 1")
    suspend fun getLessonProgress(lessonId: Int): LessonProgressEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertLessonProgress(entity: LessonProgressEntity)

    @Query("SELECT * FROM exercise_attempts")
    fun observeAllExerciseAttempts(): Flow<List<ExerciseAttemptEntity>>

    @Query("SELECT * FROM exercise_attempts WHERE exerciseId = :exerciseId LIMIT 1")
    suspend fun getExerciseAttempt(exerciseId: String): ExerciseAttemptEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertExerciseAttempt(entity: ExerciseAttemptEntity)

    @Query("SELECT * FROM mistake_logs ORDER BY timestamp DESC LIMIT 50")
    fun observeRecentMistakes(): Flow<List<MistakeLogEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMistake(entity: MistakeLogEntity)

    @Query("DELETE FROM mistake_logs WHERE lessonId = :lessonId AND topicTag = :topicTag")
    suspend fun clearTopicMistakes(lessonId: Int, topicTag: String)

    @Query("DELETE FROM word_mastery")
    suspend fun clearWordMastery()

    @Query("DELETE FROM lesson_progress")
    suspend fun clearLessonProgress()

    @Query("DELETE FROM exercise_attempts")
    suspend fun clearExerciseAttempts()

    @Query("DELETE FROM mistake_logs")
    suspend fun clearAllMistakes()
}
