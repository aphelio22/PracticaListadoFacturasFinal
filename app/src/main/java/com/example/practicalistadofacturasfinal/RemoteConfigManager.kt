package com.example.practicalistadofacturasfinal

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.google.firebase.remoteconfig.remoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings

class RemoteConfigManager {
    private var remoteConfig: FirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()

    init {
        remoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 1
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
    }

    fun fetchAndActivateConfig() {
        remoteConfig.fetchAndActivate()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    //remoteConfig.getBoolean("showSwitch")
                    Log.d("ÉXITO", "Configuración remota activada")
                } else {
                    // Error al activar la configuración remota
                    val exception = task.exception
                    Log.d("ERROR", "Error al activar la configuración remota", exception)
                }
            }
    }

    fun getBooleanValue(key: String): Boolean {
        Log.d("INFO", remoteConfig.getBoolean(key).toString())
        return remoteConfig.getBoolean(key)
    }

    companion object {
        @Volatile
        private var instance: RemoteConfigManager? = null

        fun getInstance(): RemoteConfigManager {
            return instance ?: synchronized(this) {
                instance ?: RemoteConfigManager().also { instance = it }
            }
        }
    }
}