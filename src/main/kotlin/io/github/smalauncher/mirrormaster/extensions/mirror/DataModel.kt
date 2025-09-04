package io.github.smalauncher.mirrormaster.extensions.mirror

enum class Platform {
	Windows,
	Linux
}

enum class DistributionType(val platform: Platform, val isArchive: Boolean) {
	WindowsZip(Platform.Windows, true),
	LinuxDeb(Platform.Linux, false),
	LinuxTarGz(Platform.Linux, true)
}
