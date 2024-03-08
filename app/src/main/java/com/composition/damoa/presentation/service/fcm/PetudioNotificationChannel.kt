package com.composition.damoa.presentation.service.fcm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.annotation.StringRes
import com.composition.damoa.R

enum class PetudioNotificationChannel(
    private val channelId: String,
    @StringRes private val channelNameRes: Int,
    @StringRes private val channelDescRes: Int,
    private val channelImportance: Int = NotificationManager.IMPORTANCE_HIGH,
) {
    PROFILE_COMPLETE(
        channelId = R.id.profile_complete_notification_channel_id.toString(),
        channelNameRes = R.string.channel_name_profile_complete,
        channelDescRes = R.string.channel_desc_profile_complete,
    ),
    ;

    private fun createChannel(context: Context): NotificationChannel = NotificationChannel(
        channelId,
        context.getString(channelNameRes),
        channelImportance,
    ).apply {
        description = context.getString(channelDescRes)
    }

    companion object {
        private fun getNotificationManager(context: Context): NotificationManager {
            return context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        }

        fun createChannels(context: Context) {
            val petudioNotificationChannels = PetudioNotificationChannel
                .values()
                .map { channel -> channel.createChannel(context) }

            getNotificationManager(context).createNotificationChannels(petudioNotificationChannels)
        }
    }
}
