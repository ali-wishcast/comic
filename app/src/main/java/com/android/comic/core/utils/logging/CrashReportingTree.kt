package com.android.comic.core.utils.logging

import android.util.Log
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import org.jetbrains.annotations.NotNull
import timber.log.Timber

class CrashReportingTree : @NotNull Timber.Tree() {
	override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
		if (priority == Log.VERBOSE || priority == Log.DEBUG) {
			return
		}
		// SEND ERROR REPORTS TO YOUR Crashlytics.
		t?.let {
			if (priority == Log.ERROR) {
				Firebase.crashlytics.recordException(it)
			} else if (priority == Log.WARN) {
				Firebase.crashlytics.log(it.message ?: "")
			}
		}
	}
}
