package io.github.smalauncher.mirrormaster.extensions.mirror

import dev.kord.core.event.message.MessageCreateEvent
import dev.kord.core.event.message.MessageUpdateEvent
import dev.kord.gateway.Intent
import dev.kord.gateway.PrivilegedIntent
import dev.kordex.core.commands.Arguments
import dev.kordex.core.commands.application.slash.publicSubCommand
import dev.kordex.core.commands.converters.impl.message
import dev.kordex.core.extensions.Extension
import dev.kordex.core.extensions.event
import dev.kordex.core.extensions.publicSlashCommand
import dev.kordex.core.utils.getJumpUrl
import io.github.oshai.kotlinlogging.KotlinLogging
import io.github.smalauncher.mirrormaster.MIRROR_CHANNEL_IDS
import io.github.smalauncher.mirrormaster.TEST_SERVER_ID
import io.github.smalauncher.mirrormaster.i18n.Translations

class MirrorExtension : Extension() {
	override val name = "mirror"

	val logger = KotlinLogging.logger {}

	@OptIn(PrivilegedIntent::class)
	override suspend fun setup() {
		// TODO uh. everything.

		intents += Intent.GuildMessages
		intents += Intent.MessageContent

		event<MessageCreateEvent> {
			check {
				failIf {
					!MIRROR_CHANNEL_IDS.contains(event.message.channel.id)
				}
			}

			action {
				logger.info { "AN MESSAGE HAS BEEN CREATE! ${event.message.getJumpUrl()}" }
			}
		}

		event<MessageUpdateEvent> {
			check {
				failIf {
					!MIRROR_CHANNEL_IDS.contains(event.message.channel.id)
				}
			}

			action {
				val message = event.message.fetchMessage()

				logger.info { "AN MESSAGE HAS BEEN UPDATE! ${message.getJumpUrl()}" }
			}
		}

		publicSlashCommand {
			name = Translations.Commands.Mirror.name
			description = Translations.Commands.Mirror.description
			guild(TEST_SERVER_ID)

			publicSubCommand(::MirrorManualArguments) {
				name = Translations.Commands.Mirror.Manual.name
				description = Translations.Commands.Mirror.Manual.description

				action {
					respond {
						content = "TEST, you wanted this message ${arguments.target.getJumpUrl()} right?"
					}
				}
			}
		}
	}

	class MirrorManualArguments : Arguments() {
		val target by message {
			name = Translations.Arguments.Mirror.Manual.Target.name
			description = Translations.Arguments.Mirror.Manual.Target.description
		}
	}
}
