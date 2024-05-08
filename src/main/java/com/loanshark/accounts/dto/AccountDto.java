package com.loanshark.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

@Schema(
        name = "Account",
        description = "Schema to hold account information"
)
public record AccountDto(
        @Schema(
                description = "Account number of customer account"
        )
        @NotEmpty(message = "Account number can't be null or empty")
        @Pattern(regexp = "(^$|[0-9]{10})", message = "Account number must contains 10 digits")
        Long accountNumber,
        @Schema(
                description = "Account type of customer account",
                example = "Savings"
        )
        @NotEmpty(message = "Account type can't be null or empty")
        String accountType,
        @Schema(
                description = "Branch address of customer account",
                example = "Savings"
        )
        @NotEmpty(message = "Branch address can't be null or empty")
        String branchAddress) {
}
