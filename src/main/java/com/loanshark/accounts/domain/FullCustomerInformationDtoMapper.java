package com.loanshark.accounts.domain;

import com.loanshark.accounts.dto.FullCustomerInformationDto;

public class FullCustomerInformationDtoMapper {
    public static FullCustomerInformationDto toDto(Customer customer, Account account) {
        return new FullCustomerInformationDto(account.getCustomerId().toString(),
                customer.getName(),
                customer.getEmail(),
                customer.getMobileNumber(),
                account.getAccountNumber(),
                account.getAccountType(),
                account.getBranchAddress());
    }
}
