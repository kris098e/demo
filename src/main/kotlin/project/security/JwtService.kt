package project.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.micronaut.context.annotation.ConfigurationProperties
import io.micronaut.context.annotation.Context
import jakarta.inject.Singleton
import project.security.dto.AuthenticatedUser

@Context
@ConfigurationProperties("jwt")
class JwtConfig {
    var secretKey: String? = ""
}

@Singleton
class JwtService(
    private val jwtConfig: JwtConfig,
) {
    fun generateJwt(userId: String, username: String, password: String,): String {
        return Jwts.builder()
            .setSubject(userId)
            .claim("username", username)
            .claim("password", password)
            .signWith(SignatureAlgorithm.HS256, jwtConfig.secretKey)
            .compact()
    }

    private fun parseJwt(jwt: String,): Claims {
        return Jwts.parser()
            .setSigningKey(jwtConfig.secretKey)
            .parseClaimsJws(jwt)
            .body
    }

    fun parseJwtIntoUser(jwt: String,): AuthenticatedUser {
        val claims = parseJwt(jwt)
        return AuthenticatedUser(
            username = claims["username"] as String,
            password = claims["password"] as String
        )
    }
}
