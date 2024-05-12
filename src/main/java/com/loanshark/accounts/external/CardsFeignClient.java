package com.loanshark.accounts.external;

import com.loanshark.accounts.external.dto.CardDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("cards")
public interface CardsFeignClient {
    @GetMapping(value = "/api/cards", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CardDto> getCardDetail(@RequestParam String mobileNumber);
}
