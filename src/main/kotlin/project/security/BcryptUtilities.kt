package project.security

import de.nycode.bcrypt.hash
import de.nycode.bcrypt.verify

fun String.encrypt(): String {
    val hash = hash(this)
    return String(hash, Charsets.UTF_8)
}
fun String.verifyEncryptEquals(expected: String) =
    verify(input = expected, expected = this.toByteArray())