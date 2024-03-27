package com.loanshark.accounts.api;

import com.loanshark.accounts.api.dto.CustomerDto;
import com.loanshark.accounts.api.dto.FullCustomerInformationDto;
import com.loanshark.accounts.api.dto.ResponseDto;
import com.loanshark.accounts.domain.AccountService;
import com.loanshark.accounts.exception.AccountExceptionStatus;
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
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Validated
public class AccountsApi {
    private final AccountService accountService;

    @PostMapping("/accounts")
    public ResponseEntity<FullCustomerInformationDto> createAccount(@Valid @RequestBody CustomerDto customerDto) {
        FullCustomerInformationDto customerInformation = accountService.createAccount(customerDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(customerInformation);
    }

    @GetMapping("/accounts")
    public ResponseEntity<FullCustomerInformationDto> getAccountDetails(@RequestParam(name = "mobileNumber") String mobileNumber) {
        final FullCustomerInformationDto details = accountService.getAccountDetails(mobileNumber);
        return ResponseEntity.ok(details);
    }

    @DeleteMapping("/accounts")
    public ResponseEntity<ResponseDto> deleteAccount(@RequestParam UUID customerId) {
        accountService.deleteAccount(customerId);

        return ResponseEntity
                .ok(new ResponseDto(
                        AccountExceptionStatus.STATUS_200.getStatusCode(),
                        AccountExceptionStatus.STATUS_200.getMessage()));
    }
}
