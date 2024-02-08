package com.composition.damoa.presentation.common.extensions

fun String.insertCharBetween(
    char: String = "-",
    chunkSize: Int = 4,
): String = chunked(chunkSize).joinToString(char)