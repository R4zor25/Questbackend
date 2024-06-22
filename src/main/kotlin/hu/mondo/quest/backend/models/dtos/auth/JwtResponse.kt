package hu.mondo.quest.backend.models.dtos.auth

import hu.mondo.quest.backend.models.dtos.user.LoggedInUserDTO

data class JwtResponse(
    val token: String,
    val refreshToken: String,
    val user: LoggedInUserDTO
)