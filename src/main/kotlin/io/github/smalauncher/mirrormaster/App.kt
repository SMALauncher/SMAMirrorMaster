package io.github.smalauncher.mirrormaster

import dev.kord.common.entity.Snowflake
import dev.kordex.core.ExtensibleBot
import dev.kordex.core.utils.env
import io.github.smalauncher.mirrormaster.extensions.MirrorExtension
import io.github.smalauncher.mirrormaster.extensions.ShutdownExtension
import java.io.File

val TEST_SERVER_ID = Snowflake(
	env("TEST_SERVER").toLong()  // Get the test server ID from the env vars or a .env file
)

private val TOKEN = env("TOKEN")   // Get the bot's token from the env vars or a .env file

suspend fun main() {
	val bot = ExtensibleBot(TOKEN) {
		extensions {
			add(::MirrorExtension)
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
