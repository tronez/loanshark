package com.loanshark.accounts.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
class Account extends BaseEntity {
    private UUID customerId;
    @Id
    private Long accountNumber;
    private String accountType;
    private String branchAddress;

}
