package com.thebrodyaga.christianradio.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.thebrodyaga.christianradio.R
import com.thebrodyaga.christianradio.app.App
import com.thebrodyaga.christianradio.domine.entities.data.RadioDto
import com.thebrodyaga.christianradio.tools.RadioPlayer
import timber.log.Timber
import javax.inject.Inject


class PlayerService : Service() {
    @Inject
    lateinit var player: RadioPlayer

    private var iconHeight = 0
    private var iconWidth = 0
    private val bitmapOptions = BitmapFactory.Options()
        .apply {
            outHeight = iconHeight
            outWidth = iconWidth
        }

    override fun onCreate() {
        App.appComponent.inject(this)
        super.onCreate()
        iconHeight = resources
            .getDimensionPixelSize(android.R.dimen.notification_large_icon_height)
        iconWidth = resources
            .getDimensionPixelSize(android.R.dimen.notification_large_icon_width)
        createNotificationChannel()
        Timber.i("onCreate")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.i("onDestroy")
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        handleIntent(intent)
        return START_NOT_STICKY
    }

    private fun handleIntent(intent: Intent) {
        val action = intent.action
        Timber.i("handleIntent action = $action")
        when {
            action.equals(ACTION_PLAY) -> playStart(intent)
            action.equals(ACTION_PAUSE) -> playPause(intent)
            action.equals(ACTION_STOP) -> playStop(intent)

            action.equals(ACTION_MUST_PLAY) -> player.startPlayer()
            action.equals(ACTION_MUST_PAUSE) -> player.pausePlayer()
            action.equals(ACTION_MUST_RELEASE) -> player.release()
            else -> throw IllegalArgumentException()
        }
    }

    private fun playStart(intent: Intent) {
        val currentRadio = player.currentRadio ?: return
        val action = generateAction(R.drawable.ic_pause, "Pause", ACTION_MUST_PAUSE)
        val builder = buildNotification(action, currentRadio, intent)
        startForeground(PLAYER_NOTIFICATION, builder.build())
    }

    private fun playPause(intent: Intent) {
        val currentRadio = player.currentRadio ?: return
        val action = generateAction(R.drawable.ic_play, "Play", ACTION_MUST_PLAY)
        val builder = buildNotification(action, currentRadio, intent)
        stopForeground(false)
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(PLAYER_NOTIFICATION, builder.build())
    }

    private fun playStop(intent: Intent) {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancel(PLAYER_NOTIFICATION)
        stopForeground(true)
        stopSelf()
    }

    private fun generateAction(
        icon: Int,
        title: String,
        intentAction: String
    ): NotificationCompat.Action {
        val intent = Intent(applicationContext, PlayerService::class.java)
        intent.action = intentAction
        val pendingIntent = PendingIntent.getService(applicationContext, 1, intent, 0)
        return NotificationCompat.Action.Builder(icon, title, pendingIntent).build()
    }

    private fun buildNotification(
        action: NotificationCompat.Action,
        currentRadio: RadioDto,
        intent: Intent
    ): NotificationCompat.Builder {
        val style = androidx.media.app.NotificationCompat.MediaStyle()
        val closeIntent = Intent(applicationContext, PlayerService::class.java)
        closeIntent.action = ACTION_MUST_RELEASE
        val pendingIntent = PendingIntent.getService(applicationContext, 1, closeIntent, 0)
        val bitmapArray = intent.getByteArrayExtra(ICON_EXTRA)
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_arrow_back)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setContentTitle(currentRadio.radioName)
            .setDeleteIntent(pendingIntent)
        style.setShowActionsInCompactView(0, 1, 2, 3, 4)
        builder.setStyle(style)
        bitmapArray?.also {
            val bitmap: Bitmap = BitmapFactory
                .decodeByteArray(it, 0, it.size, bitmapOptions)
            builder.setLargeIcon(bitmap)
        }
        var actionNew = generateAction(R.drawable.ic_shuffle, "Play", ACTION_MUST_PLAY)
        builder.addAction(actionNew)

        actionNew = generateAction(R.drawable.ic_skip_previous, "Play", ACTION_MUST_PLAY)
        builder.addAction(actionNew)

        builder.addAction(action)

        actionNew = generateAction(R.drawable.ic_skip_next, "Play", ACTION_MUST_PLAY)
        builder.addAction(actionNew)

        actionNew = generateAction(R.drawable.ic_favorite, "Play", ACTION_MUST_PLAY)
        builder.addAction(actionNew)
        return builder
    }

    override fun onBind(intent: Intent): IBinder? = null

    private fun createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                "Foreground Service Channel",
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(NotificationManager::class.java) as NotificationManager
            manager.createNotificationChannel(serviceChannel)
        }
    }

    companion object {
        private const val CHANNEL_ID = "ForegroundServiceChannel"
        private const val PLAYER_NOTIFICATION = 1

        const val ICON_EXTRA: String = "notification_icon"

        const val ACTION_PLAY = "action_play"
        const val ACTION_PAUSE = "action_pause"
        const val ACTION_STOP = "action_stop"

        private const val ACTION_MUST_PLAY = "action_must_play"
        private const val ACTION_MUST_PAUSE = "action_must_pause"
        private const val ACTION_MUST_RELEASE = "action_must_release"
    }
}