package com.composition.damoa.presentation.service.fcm

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class PetudioFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    private fun showNotification(message: RemoteMessage, isUpdateNeeded: Boolean) {
//        val config = configRepository.getConfig()
//        val isNotificationReceive = config.isNotificationReceive
//        if (!isNotificationReceive || tokenRepository.getToken() == null) return

        when (message.data["notificationType"]?.uppercase()) {
            TYPE_PROFILE_COMPLETE -> showMessageNotification(message)
        }
    }

    private fun showMessageNotification(message: RemoteMessage) {
//        baseContext.showNotification(
//            title = senderName,
//            message = content,
//            notificationId = roomId.hashCode(),
//            channelId = R.id.id_all_message_notification_channel,
//            intent = Intent(this, MainActivity::class.java),
//            groupKey = roomId,
//        )
    }

    companion object {
        private const val TYPE_PROFILE_COMPLETE = "profile_complete"
    }
}
