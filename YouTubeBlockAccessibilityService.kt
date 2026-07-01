package com.example.studygate

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.view.accessibility.AccessibilityEvent

class YouTubeBlockAccessibilityService : AccessibilityService() {
    private var lastBlockAt = 0L

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        val pkg = event?.packageName?.toString() ?: return
        if (pkg != "com.google.android.youtube") return
        if (StudyPrefs.isUnlocked(this)) return

        val now = System.currentTimeMillis()
        if (now - lastBlockAt < 1500) return
        lastBlockAt = now

        performGlobalAction(GLOBAL_ACTION_HOME)
        val intent = Intent(this, BlockedActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }
        startActivity(intent)
    }

    override fun onInterrupt() = Unit
}
