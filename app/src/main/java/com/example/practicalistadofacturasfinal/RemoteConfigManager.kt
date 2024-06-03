package com.example.practicalistadofacturasfinal

import android.util.Log
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteConfigManager @Inject constructor(private val remoteConfig: FirebaseRemoteConfig) {

    fun fetchAndActivateConfig() {
        remoteConfig.fetchAndActivate()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("ÉXITO", "Configuración remota activada")
                } else {
                    val exception = task.exception
                    Log.d("ERROR", "Error al activar la configuración remota", exception)
                }
            }
    }

    fun getBooleanValue(key: String): Boolean {
        Log.d("INFO", remoteConfig.getBoolean(key).toString())
        return remoteConfig.getBoolean(key)
    }
}