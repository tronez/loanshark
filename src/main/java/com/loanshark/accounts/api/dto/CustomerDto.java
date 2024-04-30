package com.loanshark.accounts.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(
        name = "Customer",
        description = "Schema to hold customer information"
)
public record CustomerDto(
        @Schema(
                description = "Full name of customer", example = "John Doe"
        )
        @NotEmpty(message = "Full name can't be null or empty")
        @Size(min = 2, max = 30, message = "Name length should be between 5 and 30")
        String name,
        @Schema(
                description = "Email of customer", example = "john.doe@gmail.com"
        )
        @NotEmpty(message = "Email can't be null or empty")
        @Email(message = "Email format is invalid")
        String email,
        @Schema(
                description = "Mobile number of customer", example = "6965816221"
        )
        @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must contains 10 digits")
        String mobileNumber) {
}
