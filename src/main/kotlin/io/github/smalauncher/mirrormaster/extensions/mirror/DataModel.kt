package io.github.smalauncher.mirrormaster.extensions.mirror

import dev.kordex.core.i18n.types.Key
import io.github.smalauncher.mirrormaster.i18n.Translations

enum class Platform(key: String) {
	Windows("windows"),
	MacOS("macos"),	// perhaps one day...
	Linux("linux");

	val key = Key("platform.$key")
		.withBundle(Translations.bundle)
}

enum class DistributionType(key: String, val platform: Platform, val isPortable: Boolean) {
	WindowsZip("windows_zip", Platform.Windows, true),
	LinuxDeb("linux_deb", Platform.Linux, false),
	LinuxTarGz("linux_tar_gz", Platform.Linux, true);

	val key = Key("distribution_type.$key")
		.withBundle(Translations.bundle)
}
