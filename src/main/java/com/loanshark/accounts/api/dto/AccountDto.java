package com.loanshark.accounts.api.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public record AccountDto(
        @NotEmpty(message = "Account number can't be null or empty")
        @Pattern(regexp = "(^$|[0-9]{10})", message = "Account number must contains 10 digits")
        Long accountNumber,
        @NotEmpty(message = "Account type can't be null or empty")
        String accountType,
        @NotEmpty(message = "Branch address can't be null or empty")
        String branchAddress) {
}
