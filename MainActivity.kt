package com.example.studygate

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView

class MainActivity : ComponentActivity() {
    private lateinit var statusText: TextView
    private lateinit var playerView: PlayerView
    private var player: ExoPlayer? = null
    private val handler = Handler(Looper.getMainLooper())

    private val tick = object : Runnable {
        override fun run() {
            val p = player
            if (p?.isPlaying == true && !StudyPrefs.isUnlocked(this@MainActivity)) {
                StudyPrefs.addOneSecond(this@MainActivity)
                updateStatus()
            }
            handler.postDelayed(this, 1000)
        }
    }

    private val pickVideo = registerForActivityResult(PickVisualMedia()) { uri: Uri? ->
        if (uri != null) {
            try {
                contentResolver.takePersistableUriPermission(
                    uri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
            } catch (_: Exception) {
                // 일부 선택기는 영구 권한을 제공하지 않을 수 있습니다. 그래도 현재 세션 재생은 됩니다.
            }
            StudyPrefs.saveVideoUri(this, uri.toString())
            loadVideo(uri)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        statusText = findViewById(R.id.statusText)
        playerView = findViewById(R.id.playerView)

        findViewById<Button>(R.id.pickVideoButton).setOnClickListener {
            pickVideo.launch(PickVisualMediaRequest(PickVisualMedia.VideoOnly))
        }

        findViewById<Button>(R.id.accessibilityButton).setOnClickListener {
            startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
        }

        setupPlayer()
        StudyPrefs.videoUri(this)?.let { loadVideo(Uri.parse(it)) }
        updateStatus()
    }

    private fun setupPlayer() {
        player = ExoPlayer.Builder(this).build().also { exo ->
            playerView.player = exo
            exo.playbackParameters = exo.playbackParameters.withSpeed(1.0f)
        }
    }

    private fun loadVideo(uri: Uri) {
        player?.apply {
            setMediaItem(MediaItem.fromUri(uri))
            prepare()
            playWhenReady = true
        }
    }

    private fun updateStatus() {
        val seconds = StudyPrefs.watchedSeconds(this)
        val min = seconds / 60
        val sec = seconds % 60
        statusText.text = if (StudyPrefs.isUnlocked(this)) {
            "오늘 YouTube 잠금 해제 완료"
        } else {
            "오늘 강의 ${min}분 ${sec}초 / 30초"
        }
    }

    override fun onResume() {
        super.onResume()
        updateStatus()
        handler.post(tick)
    }

    override fun onPause() {
        super.onPause()
        player?.pause()
        handler.removeCallbacks(tick)
    }

    override fun onDestroy() {
        handler.removeCallbacks(tick)
        playerView.player = null
        player?.release()
        player = null
        super.onDestroy()
    }
}
