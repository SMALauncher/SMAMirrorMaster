package io.github.smalauncher.mirrormaster

import dev.kord.common.entity.Snowflake
import dev.kordex.core.utils.env

fun envOfSnowflakeSet(name: String): Set<Snowflake> {
	val value = env(name)
	val parts = value.split(";")

	val result = LinkedHashSet<Snowflake>(parts.size)

	for (part in parts) {
		try {
			result.add(Snowflake(part.toULong()))
		} catch (e: NumberFormatException) {
			throw IllegalStateException(
				"Environmental variable '$name' has an invalid value.",
				e
			)
		}
	}

	return result
}
