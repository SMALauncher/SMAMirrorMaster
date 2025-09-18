package io.github.smalauncher.mirrormaster

import dev.kord.common.entity.Snowflake
import dev.kordex.core.ExtensibleBot
import dev.kordex.core.utils.env
import dev.kordex.core.utils.envOrNull
import io.github.smalauncher.mirrormaster.extensions.mirror.MirrorExtension
import io.github.smalauncher.mirrormaster.extensions.ShutdownExtension
import io.sentry.SentryLevel
import java.io.File

val TEST_SERVER_ID = Snowflake(
	env("TEST_SERVER").toULong()
)

val MIRROR_CHANNEL_IDS = envOfSnowflakeSet("MIRROR_CHANNELS")

private val TOKEN = env("TOKEN")

private val SENTRY_DSN = envOrNull("SENTRY_DSN")

suspend fun main() {
	val bot = ExtensibleBot(TOKEN) {
		extensions {
			add(::MirrorExtension)

			if (SENTRY_DSN != null) {
				sentry {
					enable = true
					dsn = SENTRY_DSN

					setup {
						this.init { options ->
							options.dsn = dsn
							options.isDebug = debug

							options.dist = distribution
							options.environment = environment
							options.release = release
							options.serverName = serverName

							options.isAttachThreads = false
							options.sampleRate = sampleRate

							options.setDiagnosticLevel(SentryLevel.WARNING)

							@Suppress("UnstableApiUsage")
							options.logs.isEnabled = true
						}
					}
				}
			}
		}

		if (devMode) {
			extensions {
				add(::ShutdownExtension)
			}

			// In development mode, load any plugins from `src/main/dist/plugin` if it exists.
			plugins {
				if (File("src/main/dist/plugins").isDirectory) {
					pluginPath("src/main/dist/plugins")
				}
			}
		}
	}

	bot.start()
}
