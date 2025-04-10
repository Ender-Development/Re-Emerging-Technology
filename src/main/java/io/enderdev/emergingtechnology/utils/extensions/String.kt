package io.enderdev.emergingtechnology.utils.extensions

import net.minecraft.client.resources.I18n

fun String.translate(vararg format: Any): String = I18n.format(this, *format)
