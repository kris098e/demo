# generate
To add the username and password to the JWT token in a Micronaut Kotlin project, you can use the java-jwt library. Here's a step-by-step guide on how to do this:

Add the java-jwt dependency to your project: You can do this by adding the following line to your build.gradle file:
``` kotlin
implementation("io.jsonwebtoken:jjwt:0.9.1")
```

Create a function to generate the JWT with username and password: This function should create a JwtBuilder object, set the necessary claims (including the username and password), sign the JWT with your secret key, and then serialize it to a string. Here's an example:
``` kotlin
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm

fun generateJwt(userId: String, username: String, password: String, secretKey: String): String {
  return Jwts.builder()
      .setSubject(userId)
      .claim("username", username)
      .claim("password", password)
      .signWith(SignatureAlgorithm.HS256, secretKey)
      .compact()
}
```
In this example, the userId is the subject of the JWT, the username and password are additional claims, and the secretKey is the key used to sign the JWT. You should replace these with your actual user ID, username, password, and secret key.

Please note that this is a simplified example. In a real-world application, you would likely need to set more claims in the JWT, such as the issuer, audience, and expiration time. You might also need to handle cases where the JWT cannot be generated, such as when the secret key is not available.

Also, be aware that storing sensitive information like passwords in a JWT is generally not recommended due to security concerns. JWTs are not encrypted, so anyone who intercepts the JWT can read its contents. Therefore, you should avoid storing sensitive information in a JWT unless it's absolutely necessary and you have appropriate security measures in place.

# parse
To parse a JWT token into a class in a Micronaut Kotlin project, you would typically need to use a JWT library, such as java-jwt or jjwt, to decode the JWT and map the decoded data into your class. Here's a step-by-step guide on how to do this:

Create your DTO class: This class should have fields that match the data you expect in the JWT. For example:
```kotlin
data class MyDto(
   val username: String,
   val role: String
)
```
Decode the JWT: You can use the Jwts.parser() method from the java-jwt library to decode the JWT. This method returns a JwtParser object, which you can use to parse the JWT and get the Claims object, which contains the decoded data. Here's an example:
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.Claims

``` kotlin
fun parseJwt(jwt: String, secretKey: String): Claims {
   return Jwts.parser()
       .setSigningKey(secretKey)
       .parseClaimsJws(jwt)
       .body
}
```
In this example, secretKey is the secret key used to sign the JWT. You should replace this with your actual secret key.

Map the decoded data into your DTO: Once you have the Claims object, you can use it to create an instance of your DTO class. Here's an example:
```kotlin
fun parseJwt(jwt: String, secretKey: String): MyDto {
   val claims = parseJwt(jwt, secretKey)
   return MyDto(
       username = claims["username"] as String,
       role = claims["role"] as String
   )
}
```
In this example, the Claims object is used to get the username and role, which are then used to create an instance of MyDto.

Please note that this is a simplified example. In a real-world application, you would likely need to handle more complex scenarios, such as JWTs that contain multiple roles or other custom data. You might also need to handle cases where the JWT is not present or is invalid.


