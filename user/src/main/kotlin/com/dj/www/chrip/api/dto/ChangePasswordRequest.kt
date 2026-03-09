package com.dj.www.chrip.api.dto

import com.dj.www.chrip.api.util.Password
import jakarta.validation.constraints.NotBlank

data class ChangePasswordRequest(
    @field:NotBlank
    val oldPassword: String,
    @field:Password
    val newPassword: String,
)
