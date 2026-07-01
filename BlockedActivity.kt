package com.example.studygate

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity

class BlockedActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blocked)
        updateMessage()

        findViewById<Button>(R.id.goStudyButton).setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun updateMessage() {
        val remaining = (StudyPrefs.TARGET_SECONDS - StudyPrefs.watchedSeconds(this)).coerceAtLeast(0)
        val min = remaining / 60
        val sec = remaining % 60
        findViewById<TextView>(R.id.blockedText).text =
            "남은 강의 시청 시간: ${min}분 ${sec}초\n강의 영상을 30초 시청하면 오늘 YouTube를 볼 수 있어요."
    }
}
