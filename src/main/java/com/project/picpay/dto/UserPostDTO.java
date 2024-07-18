package com.project.picpay.dto;

import com.project.picpay.domain.user.UserType;

import java.math.BigDecimal;

public record UserPostDTO(String fullName, BigDecimal balance, String cpf, String email, String password, UserType userType) {
}
