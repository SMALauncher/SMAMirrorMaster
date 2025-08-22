package io.github.smalauncher.mirrormaster.extensions

import dev.kordex.core.checks.isBotOwner
import dev.kordex.core.extensions.Extension
import dev.kordex.core.extensions.publicSlashCommand
import dev.kordex.core.i18n.withContext
import io.github.smalauncher.mirrormaster.TEST_SERVER_ID
import io.github.smalauncher.mirrormaster.i18n.Translations
import kotlin.system.exitProcess

class ShutdownExtension : Extension() {
	override val name = "shutdown"

	override suspend fun setup() {
		publicSlashCommand {
			name = Translations.Commands.Shutdown.name
			description = Translations.Commands.Shutdown.description
			guild(TEST_SERVER_ID)

			check {
				isBotOwner()
			}

			action {
				respond {
					content = Translations.Commands.Shutdown.response
						.withContext(this@action)
						.translate()
				}

				this@ShutdownExtension.bot.close()
				exitProcess(0)
			}
		}
	}
}
