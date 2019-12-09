package com.thebrodyaga.christianradio.tools

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import com.bumptech.glide.Glide
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.dash.DashMediaSource
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.util.Util.getUserAgent
import com.thebrodyaga.christianradio.R
import com.thebrodyaga.christianradio.domine.entities.data.PlayingRadio
import com.thebrodyaga.christianradio.domine.entities.data.RadioDto
import com.thebrodyaga.christianradio.service.PlayerService
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.io.ByteArrayOutputStream
import java.lang.Exception
import java.util.concurrent.CopyOnWriteArraySet

class RadioPlayer constructor(
    private val context: Context
) : Player.EventListener {


    private val playingListeners: CopyOnWriteArraySet<
                (currentRadio: PlayingRadio?, isPlaying: Boolean) -> Unit
            > = CopyOnWriteArraySet()
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private var player = ExoPlayerFactory.newSimpleInstance(context)
    private val dataSourceFactory = DefaultDataSourceFactory(
        context,
        getUserAgent(context, context.getString(R.string.app_name))
    )
    private val sourceFactory = ProgressiveMediaSource.Factory(dataSourceFactory)
    var currentRadio: PlayingRadio? = null
        private set

    init {
        player.addListener(this)
    }

    fun playRadio(radio: PlayingRadio, playWhenReady: Boolean = true) {
        if (currentRadio == radio) {
            togglePlay()
            return
        }
        currentRadio = radio
        val source = sourceFactory.createMediaSource(Uri.parse(radio.radioDto.radioUrl))
        player.prepare(source)
        if (playWhenReady)
            startPlayer()
    }

    fun pausePlayer() {
        Timber.i("pausePlayer")
        player.playWhenReady = false
        player.playbackState
    }

    fun startPlayer() {
        Timber.i("startPlayer")
        player.playWhenReady = true
        player.playbackState
    }

    fun togglePlay() {
        Timber.i("togglePlay")
        player.playWhenReady = !player.playWhenReady
        player.playbackState
    }

    fun stopPlay() {
        Timber.i("stopPlay")
        player.stop()
        player.playbackState
    }

    fun addPlayingListeners(listener: (currentRadio: PlayingRadio?, isPlaying: Boolean) -> Unit)=
        playingListeners.add(listener)

    fun removePlayingListeners(listener: (currentRadio: PlayingRadio?, isPlaying: Boolean) -> Unit) =
        playingListeners.remove(listener)

    fun release() {
        Timber.i("release")
        //todo перенести плеер в сервис, там создавать инстанст и релизить
        player.stop()
        currentRadio = null
        loadImage?.dispose()
        startService(PlayerService.ACTION_STOP)
    }

    override fun onIsPlayingChanged(isPlaying: Boolean) {
        Timber.i("onIsPlayingChanged isPlaying = $isPlaying")
        startService(if (isPlaying) PlayerService.ACTION_PLAY else PlayerService.ACTION_PAUSE)
        playingListeners.forEach { it.invoke(currentRadio, isPlaying) }
    }

    private var loadImage: Disposable? = null

    private fun startService(action: String) {
        val currentRadio = this.currentRadio ?: return
        fun start(icon: ByteArray?) {
            val intent = Intent(context, PlayerService::class.java)
            icon?.also { intent.putExtra(PlayerService.ICON_EXTRA, icon) }
            intent.action = when (action) {
                PlayerService.ACTION_PLAY,
                PlayerService.ACTION_PAUSE,
                PlayerService.ACTION_STOP -> action
                else -> throw IllegalArgumentException()
            }
            context.startService(intent)
        }
        start(null)
        loadImage?.dispose()
        loadImage = loadImage(currentRadio.radioDto.radioImage)
            .map { bitmapArray(it) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                start(it)
            }, {
                start(null)
                Timber.i(it)
            })

    }

    private fun loadImage(utl: String) =
        Observable.create<Bitmap> {
            try {
                it.onNext(
                    Glide.with(context)
                        .asBitmap()
                        .load(utl)
                        .submit()
                        .get()
                )
            } catch (e: Exception) {
                it.tryOnError(e)
            }

        }

    private fun bitmapArray(bitmap: Bitmap): ByteArray {
        val bStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bStream)
        return bStream.toByteArray()
    }
}