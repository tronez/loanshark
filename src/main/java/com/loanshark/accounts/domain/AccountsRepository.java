package com.loanshark.accounts.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
interface AccountsRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByCustomerId(UUID customerId);

    void deleteByCustomerId(UUID customerId);
}
