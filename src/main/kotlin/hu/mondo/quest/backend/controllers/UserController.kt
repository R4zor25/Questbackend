package hu.mondo.quest.backend.controllers

import hu.mondo.quest.backend.models.dtos.auth.JwtResponse
import hu.mondo.quest.backend.models.dtos.user.AuthDTO
import hu.mondo.quest.backend.models.dtos.user.LoggedInUserDTO
import hu.mondo.quest.backend.security.JwtTokenProvider
import hu.mondo.quest.backend.services.user.UserService
import lombok.RequiredArgsConstructor
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@CrossOrigin(origins = ["*"])
class UserController(
    private val userService: UserService,
    private val jwtTokenProvider: JwtTokenProvider,
    private val authenticationManager: AuthenticationManager,
    private val userDetailsService: UserDetailsService
) {
    private val log = LoggerFactory.getLogger(this.javaClass)
    var UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/uploads"

    @PostMapping("/auth/register")
    fun register(@RequestBody authDTO: AuthDTO) : ResponseEntity<Any> {
        return try {
            ResponseEntity.ok().body(userService.registerUser(authDTO))
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(e.localizedMessage)
        }
    }

    @PostMapping("/auth/login")
    fun loginUser(@RequestBody authDTO: AuthDTO): ResponseEntity<JwtResponse> {
        val user = userService.loginUser(authDTO)
        val authentication = jwtTokenProvider.getAuthenticationFromUsername(user.username)
        val token = jwtTokenProvider.generateToken(authentication)
        val refreshToken = jwtTokenProvider.generateToken(authentication) // Generate a separate refresh token if needed
        val loggedInUserDTO = LoggedInUserDTO(user.questUserId!!, user.username, user.infinitePoints, user.role.value, user.answeredStoryQuestions.isNotEmpty())
        return ResponseEntity.ok(JwtResponse(token, refreshToken, loggedInUserDTO))
    }

    @GetMapping("/{username}")
    fun getUser(@PathVariable username: String) : ResponseEntity<Any> {
        return try {
            ResponseEntity.ok().body(userService.findByUsername(username))
        } catch (e: Exception) {
            ResponseEntity.status(404).body(e.localizedMessage)
        }
    }

    @GetMapping("/leaderboard/infinite")
    fun getLeaderboardInfiniteUsers() : ResponseEntity<Any> {
        return try {
            ResponseEntity.ok().body(userService.getInfiniteLeaderboard())
        } catch (e: Exception) {
            ResponseEntity.status(404).body(e.localizedMessage)
        }
    }

    @GetMapping("/leaderboard/story")
    fun getLeaderboardStoryUsers() : ResponseEntity<Any> {
        return try {
            ResponseEntity.ok().body(userService.getStoryLeaderboard())
        } catch (e: Exception) {
            ResponseEntity.status(404).body(e.localizedMessage)
        }
    }

    @PostMapping("/refreshToken")
    fun refreshToken() : ResponseEntity<Any> {
        return try {
            ResponseEntity.ok().body(userService)
        } catch (e: Exception) {
            ResponseEntity.status(404).body(e.localizedMessage)
        }
    }

    @PostMapping("/logout")
    fun logout() : ResponseEntity<Any> {
        return try {
            ResponseEntity.ok().body(userService)
        } catch (e: Exception) {
            ResponseEntity.status(404).body(e.localizedMessage)
        }
    }
}