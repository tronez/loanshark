package com.loanshark.accounts.domain;

import com.loanshark.accounts.dto.CustomerDto;
import com.loanshark.accounts.dto.FullCustomerInformationDto;
import com.loanshark.accounts.dto.ResponseDto;
import com.loanshark.accounts.exception.AccountExceptionStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(path = "/api/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Validated
@Tag(name = "Rest API for accounts")
public class AccountApi {
    private final AccountService accountService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Create account",
            description = "Creates customer and account"
    )
    @ApiResponse(responseCode = "201")
    public ResponseEntity<FullCustomerInformationDto> createAccount(@Valid @RequestBody CustomerDto customerDto) {
        FullCustomerInformationDto customerInformation = accountService.createAccount(customerDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(customerInformation);
    }

    @GetMapping
    @Operation(summary = "Fetch full customer and account information")
    @ApiResponse(responseCode = "200")
    public ResponseEntity<FullCustomerInformationDto> getAccountDetails(@RequestParam(name = "mobileNumber") String mobileNumber) {
        final FullCustomerInformationDto details = accountService.getAccountDetails(mobileNumber);
        return ResponseEntity.ok(details);
    }

    @DeleteMapping
    @Operation(summary = "Delete customer and account")
    @ApiResponse(responseCode = "200")
    public ResponseEntity<ResponseDto> deleteAccount(@RequestParam UUID customerId) {
        accountService.deleteAccount(customerId);

        return ResponseEntity
                .ok(new ResponseDto(
                        AccountExceptionStatus.STATUS_200.getStatusCode(),
                        AccountExceptionStatus.STATUS_200.getMessage()));
    }
}
