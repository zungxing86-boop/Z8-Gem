package com.example.data.local

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "prospect2_prefs")

data class StudentPreferences(
    val studentName: String = "دانش‌آموز پایه هشتم",
    val lastUnitId: Int = 1,
    val lastStageIndex: Int = 0,
    val speechRate: Float = 0.9f,
    val showPersianTranslation: Boolean = true,
    val totalXp: Int = 0
)

class UserPrefsStore(private val context: Context) {
    private object Keys {
        val STUDENT_NAME = stringPreferencesKey("student_name")
        val LAST_UNIT_ID = intPreferencesKey("last_unit_id")
        val LAST_STAGE_INDEX = intPreferencesKey("last_stage_index")
        val SPEECH_RATE = floatPreferencesKey("speech_rate")
        val SHOW_PERSIAN = booleanPreferencesKey("show_persian")
        val TOTAL_XP = intPreferencesKey("total_xp")
    }

    val preferencesFlow: Flow<StudentPreferences> = context.dataStore.data.map { prefs ->
        StudentPreferences(
            studentName = prefs[Keys.STUDENT_NAME] ?: "دانش‌آموز پایه هشتم",
            lastUnitId = prefs[Keys.LAST_UNIT_ID] ?: 1,
            lastStageIndex = prefs[Keys.LAST_STAGE_INDEX] ?: 0,
            speechRate = prefs[Keys.SPEECH_RATE] ?: 0.9f,
            showPersianTranslation = prefs[Keys.SHOW_PERSIAN] ?: true,
            totalXp = prefs[Keys.TOTAL_XP] ?: 0
        )
    }

    suspend fun saveLastLocation(unitId: Int, stageIndex: Int) {
        context.dataStore.edit { prefs ->
            prefs[Keys.LAST_UNIT_ID] = unitId
            prefs[Keys.LAST_STAGE_INDEX] = stageIndex
        }
    }

    suspend fun setSpeechRate(rate: Float) {
        context.dataStore.edit { prefs ->
            prefs[Keys.SPEECH_RATE] = rate
        }
    }

    suspend fun setShowPersianTranslation(show: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[Keys.SHOW_PERSIAN] = show
        }
    }

    suspend fun setStudentName(name: String) {
        context.dataStore.edit { prefs ->
            prefs[Keys.STUDENT_NAME] = name.ifBlank { "دانش‌آموز پایه هشتم" }
        }
    }

    suspend fun addXp(points: Int) {
        if (points <= 0) return
        context.dataStore.edit { prefs ->
            val current = prefs[Keys.TOTAL_XP] ?: 0
            prefs[Keys.TOTAL_XP] = current + points
        }
    }

    suspend fun resetXpAndLocation() {
        context.dataStore.edit { prefs ->
            prefs[Keys.LAST_UNIT_ID] = 1
            prefs[Keys.LAST_STAGE_INDEX] = 0
            prefs[Keys.TOTAL_XP] = 0
        }
    }
}
