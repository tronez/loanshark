package com.loanshark.accounts.dto;

public record FullCustomerInformationDto(String customerId, String name, String email, String mobileNumber,
                                         Long accountNumber, String accountType, String branchAddress) {

}
