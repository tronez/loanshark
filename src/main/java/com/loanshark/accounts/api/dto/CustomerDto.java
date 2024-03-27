package com.loanshark.accounts.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CustomerDto(
        @NotEmpty(message = "Full name can't be null or empty")
        @Size(min = 2, max = 30, message = "Name length should be between 5 and 30")
        String name,
        @NotEmpty(message = "Email can't be null or empty")
        @Email(message = "Email format is invalid")
        String email,
        @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must contains 10 digits")
        String mobileNumber) {
}
