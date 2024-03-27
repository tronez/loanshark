package com.loanshark.accounts.domain;

import com.loanshark.accounts.api.dto.AccountDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AccountMapper {
    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);
    AccountDto toDto(Account account);

    Account toEntity(AccountDto accountDto);
}
