package io.github.smalauncher.mirrormaster.extensions

import dev.kordex.core.commands.Arguments
import dev.kordex.core.commands.application.slash.publicSubCommand
import dev.kordex.core.commands.converters.impl.string
import dev.kordex.core.extensions.Extension
import dev.kordex.core.extensions.publicSlashCommand
import dev.kordex.core.i18n.withContext
import io.github.oshai.kotlinlogging.KotlinLogging
import io.github.smalauncher.mirrormaster.TEST_SERVER_ID
import io.github.smalauncher.mirrormaster.i18n.Translations

class MirrorExtension : Extension() {
	override val name = "mirror"

	val logger = KotlinLogging.logger {}

	override suspend fun setup() {
		// TODO uh. everything.

		publicSlashCommand {
			name = Translations.Commands.Mirror.name
			description = Translations.Commands.Mirror.description
			guild(TEST_SERVER_ID)

			publicSubCommand(arguments = ::LogArgs) {
				name = Translations.Commands.Mirror.Log.name
				description = Translations.Commands.Mirror.Log.description

				action {
					logger.info { arguments.message }

					try {
						throw RuntimeException("This is a test exception.")
					} catch (e: RuntimeException) {
						logger.error(e) { "Caught an exception!" }
					}

					respond {
						content = Translations.Commands.Mirror.Log.response
							.withContext(this@action)
							.translate()
					}
				}
			}
		}
	}

	class LogArgs : Arguments() {
		val message by string {
			name = Translations.Arguments.Message.name
			description = Translations.Arguments.Message.description
		}
	}
}
