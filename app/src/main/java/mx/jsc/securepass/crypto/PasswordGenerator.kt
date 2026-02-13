package mx.jsc.securepass.crypto

import java.security.SecureRandom

/**
 * Generates cryptographically secure random passwords using [SecureRandom].
 */
object PasswordGenerator {

    private const val LOWERCASE = "abcdefghijklmnopqrstuvwxyz"
    private const val UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    private const val DIGITS = "0123456789"
    private const val SYMBOLS = "!@#\$%^&*()-_=+[]{}|;:',.<>?/~`"

    private val secureRandom = SecureRandom()

    /**
     * Generates a random password with the given configuration.
     *
     * @param length  Password length (clamped to 8..128).
     * @param useUppercase Include uppercase letters.
     * @param useDigits    Include digits.
     * @param useSymbols   Include special characters.
     * @return A cryptographically random password string.
     */
    fun generate(
        length: Int = 20,
        useUppercase: Boolean = true,
        useDigits: Boolean = true,
        useSymbols: Boolean = true
    ): String {
        val safeLength = length.coerceIn(8, 128)

        val pool = buildString {
            append(LOWERCASE)
            if (useUppercase) append(UPPERCASE)
            if (useDigits) append(DIGITS)
            if (useSymbols) append(SYMBOLS)
        }

        // Guarantee at least one character from each enabled category
        val guaranteed = mutableListOf<Char>()
        guaranteed += LOWERCASE[secureRandom.nextInt(LOWERCASE.length)]
        if (useUppercase) guaranteed += UPPERCASE[secureRandom.nextInt(UPPERCASE.length)]
        if (useDigits) guaranteed += DIGITS[secureRandom.nextInt(DIGITS.length)]
        if (useSymbols) guaranteed += SYMBOLS[secureRandom.nextInt(SYMBOLS.length)]

        // Fill the rest from the combined pool
        val remaining = safeLength - guaranteed.size
        val chars = guaranteed + List(remaining) { pool[secureRandom.nextInt(pool.length)] }

        // Shuffle to avoid predictable positions for guaranteed chars
        return chars.shuffled(secureRandom).joinToString("")
    }
}
