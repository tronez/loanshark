package com.loanshark.accounts.domain;

import com.loanshark.accounts.api.dto.CustomerDto;
import com.loanshark.accounts.api.dto.FullCustomerInformationDto;
import com.loanshark.accounts.exception.CustomerAlreadyExistsException;
import com.loanshark.accounts.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class AccountService {
    private final AccountsRepository accountsRepository;
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public FullCustomerInformationDto createAccount(CustomerDto customerDto) {
        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customerDto.mobileNumber());
        optionalCustomer.ifPresent(s -> {
            throw new CustomerAlreadyExistsException("Customer had already been registered with given mobile number");
        });

        Customer customer = customerMapper.toEntity(customerDto);

        Customer savedCustomer = customerRepository.save(customer);
        Account savedAccount = accountsRepository.save(newAccount(savedCustomer));
        return FullCustomerInformationDtoMapper.toDto(savedCustomer, savedAccount);
    }

    public FullCustomerInformationDto getAccountDetails(String mobileNumber) {
        final Customer customer = customerRepository
                .findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));
        final Account account = accountsRepository
                .findByCustomerId(customer.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Account", "customerId",
                        customer.getCustomerId().toString()));

        return FullCustomerInformationDtoMapper.toDto(customer, account);
    }

    public CustomerDto updateCustomer(CustomerDto customerDto) {
        return null;
    }

    public boolean deleteAccount(UUID customerId) {
        final Customer customer = customerRepository
                .findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "customerId", customerId.toString()));

        accountsRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        return true;
    }

    private Account newAccount(Customer customer) {
        long randomAccountNumber = 1_000_000_000L + new Random().nextInt(9_000_000_00);
        return new Account(customer.getCustomerId(), randomAccountNumber, "SAVINGS", "branch");
    }

}
