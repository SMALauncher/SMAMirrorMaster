package io.github.smalauncher.mirrormaster.util

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.UnsynchronizedAppenderBase
import io.sentry.Sentry
import io.sentry.SentryInstantDate
import io.sentry.SentryLogLevel

/**
 * Adapts Logback messages to the [Sentry Logs API](https://docs.sentry.io/platforms/java/logs/).
 */
class SentryLogsAppender : UnsynchronizedAppenderBase<ILoggingEvent>() {
	override fun append(eventObject: ILoggingEvent?) {
		if (eventObject == null || !Sentry.isEnabled())
			return

		@Suppress("UnstableApiUsage")
		Sentry.logger().log(
			mapLevel(eventObject.level),
			SentryInstantDate(eventObject.instant),
			eventObject.message,
			eventObject.argumentArray)
	}

	private fun mapLevel(level: Level): SentryLogLevel {
		return when (level) {
			Level.TRACE -> SentryLogLevel.TRACE
			Level.DEBUG -> SentryLogLevel.DEBUG
			Level.INFO -> SentryLogLevel.INFO
			Level.WARN -> SentryLogLevel.WARN
			Level.ERROR -> SentryLogLevel.ERROR
			// Logback doesn't have a "fatal/critical" level, so we never return SentryLogLevel.FATAL
			else -> throw RuntimeException("Unknown Logback level $level")
		}
	}
}
