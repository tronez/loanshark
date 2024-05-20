package com.loanshark.accounts.external;

import com.loanshark.accounts.external.dto.LoanDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "loans", fallback = Object.class)
public interface LoanFeignClient {
    @GetMapping(value = "/api/loans", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<LoanDto> getLoanDetail(@RequestParam String mobileNumber);
}


