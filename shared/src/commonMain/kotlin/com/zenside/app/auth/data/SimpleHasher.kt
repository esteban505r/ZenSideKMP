package com.zenside.app.auth.data

/**
 * V1 placeholder only — NOT production-grade crypto. Deterministic,
 * pure-Kotlin string hash so passwords aren't stored as plaintext.
 */
internal object SimpleHasher {
    private const val SALT = "zenside-v1-salt"

    fun hash(value: String): String {
        val input = SALT + value
        var h1 = 0x811c9dc5L
        for (c in input) {
            h1 = h1 xor c.code.toLong()
            h1 = (h1 * 0x01000193L) and 0xFFFFFFFFL
        }
        return h1.toString(16)
    }
}
