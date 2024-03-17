package com.efborchardt.productfeedback.domain._shared.validator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailValidatorTest {

    @Test
    void validEmails() {
        assertTrue(EmailValidator.isValid("email@example.com"));
        assertTrue(EmailValidator.isValid("firstname.lastname@example.com"));
        assertTrue(EmailValidator.isValid("email@subdomain.example.com"));
        assertTrue(EmailValidator.isValid("firstname+lastname@example.com"));
        assertTrue(EmailValidator.isValid("1234567890@example.com"));
        assertTrue(EmailValidator.isValid("email@example-one.com"));
        assertTrue(EmailValidator.isValid("_______@example.com"));
        assertTrue(EmailValidator.isValid("email@example.name"));
        assertTrue(EmailValidator.isValid("email@example.museum"));
        assertTrue(EmailValidator.isValid("email@example.co.jp"));
        assertTrue(EmailValidator.isValid("firstname-lastname@example.com"));
    }

    @Test
    void invalidEmails() {
        assertFalse(EmailValidator.isValid("plainaddress"));
        assertFalse(EmailValidator.isValid("@no-local-part.com"));
        assertFalse(EmailValidator.isValid("Outlook Contact <outlook_contact@outlook.com>"));
        assertFalse(EmailValidator.isValid("no-at-sign"));
        assertFalse(EmailValidator.isValid("no-tld@domain"));
        assertFalse(EmailValidator.isValid("no-domain@.com"));
        assertFalse(EmailValidator.isValid("multiple@domains@domain.com"));
        assertFalse(EmailValidator.isValid("spaces in local@domain.com"));
        assertFalse(EmailValidator.isValid("spaces-in-domain@dom ain.com"));
        assertFalse(EmailValidator.isValid("underscores-in-tld@domain.c_m"));
        assertFalse(EmailValidator.isValid("pipe-in-domain@example.com|"));
    }
}