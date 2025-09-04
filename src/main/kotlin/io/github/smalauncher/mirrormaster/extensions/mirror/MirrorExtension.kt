package io.github.smalauncher.mirrormaster.extensions.mirror

import dev.kordex.core.extensions.Extension
import dev.kordex.core.extensions.publicSlashCommand
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
		}
	}
}
