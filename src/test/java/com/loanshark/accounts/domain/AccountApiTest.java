package com.loanshark.accounts.domain;

import com.loanshark.accounts.BaseIT;
import com.loanshark.accounts.TestUtil;
import com.loanshark.accounts.dto.CustomerDto;
import com.loanshark.accounts.dto.FullCustomerInformationDto;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.Optional;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AccountApiTest extends BaseIT {
    private static final String ACCOUNTS_MAPPING = "/accounts";
    private static final String MOBILE_NUMBER_API_PARAM = "mobileNumber";

    @Autowired
    AccountService accountService;
    @Autowired
    AccountsRepository accountsRepository;
    @Autowired
    CustomerRepository customerRepository;

    @NotNull
    private static CustomerDto createCustomerDto() {
        return new CustomerDto("Tomasz Chajto", "babana@pasach.com", TestUtil.getMobileNumberAtomic().toString());
    }

    @Test
    void create_account() {
        var customerDto = createCustomerDto();

        given()
                .spec(getRequestSpecification())
                .body(customerDto)

                .when()
                .post(ACCOUNTS_MAPPING)

                .then()
                .statusCode(HttpStatus.CREATED.value());

        Customer customer = customerRepository.findByMobileNumber(customerDto.mobileNumber()).get();
        assertEquals(customerDto.mobileNumber(), customer.getMobileNumber());
        assertEquals(customerDto.email(), customer.getEmail());
        assertEquals(customerDto.name(), customer.getName());
    }

    @Test
    void create_account_already_exists() {
        var customerDto = createCustomerDto();
        accountService.createAccount(customerDto);

        given()
                .spec(getRequestSpecification())
                .body(customerDto)

                .when()
                .post(ACCOUNTS_MAPPING)

                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void get_account_details() {
        var customerDto = createCustomerDto();
        var informationDto = accountService.createAccount(customerDto);

        Long extractedAccountNumber = given()
                .spec(getRequestSpecification())
                .param(MOBILE_NUMBER_API_PARAM, customerDto.mobileNumber())

                .when()
                .get(ACCOUNTS_MAPPING)

                .then()
                .statusCode(HttpStatus.OK.value())
                .body("customerId", is(informationDto.customerId()))
                .body("name", is(informationDto.name()))
                .body("email", is(informationDto.email()))
                .body("mobileNumber", is(informationDto.mobileNumber()))
                .body("accountType", is("SAVINGS"))
                .body("branchAddress", is("branch"))
                .extract().jsonPath().getLong("accountNumber");

        // Matcher.is having problem when comes to comparing string value of Long, hence extraction
        assertEquals(informationDto.accountNumber(), extractedAccountNumber);
    }

    @Test
    void get_account_details_account_nonexistent() {
        var mobileNumber = "0000000000";

        given()
                .spec(getRequestSpecification())
                .param(MOBILE_NUMBER_API_PARAM, mobileNumber)

                .when()
                .get(ACCOUNTS_MAPPING)

                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void delete_account() {
        var customerIdApiParam = "customerId";
        var customerDto = createCustomerDto();
        FullCustomerInformationDto informationDto = accountService.createAccount(customerDto);

        given()
                .spec(getRequestSpecification())
                .param(customerIdApiParam, informationDto.customerId())

                .when()
                .delete(ACCOUNTS_MAPPING)

                .then()
                .statusCode(HttpStatus.OK.value());

        var customerId = UUID.fromString(informationDto.customerId());
        Optional<Account> accountEntity = accountsRepository.findByCustomerId(customerId);
        Optional<Customer> customerEntity = customerRepository.findById(customerId);
        assertAll(
                () -> assertTrue(customerEntity.isEmpty()),
                () -> assertTrue(accountEntity.isEmpty())
        );
    }
}