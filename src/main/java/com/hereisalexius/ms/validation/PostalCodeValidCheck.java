package com.hereisalexius.ms.validation;

public class PostalCodeValidCheck extends ValidCheck {

    public PostalCodeValidCheck() {
        super("postalCode", "([0-9]{5})");
    }

}
