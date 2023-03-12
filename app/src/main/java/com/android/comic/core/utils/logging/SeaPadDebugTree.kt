package com.android.comic.core.utils.logging

import org.jetbrains.annotations.NotNull
import timber.log.Timber

class SeaPadDebugTree : @NotNull Timber.DebugTree() {
	override fun createStackElementTag(element: StackTraceElement): String {
		return String.format(
			"Class:%s: Line: %s, Method: %s",
			super.createStackElementTag(element),
			element.lineNumber,
			element.methodName,
		)
	}
}
