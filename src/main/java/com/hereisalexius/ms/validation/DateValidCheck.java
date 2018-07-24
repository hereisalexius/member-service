package com.hereisalexius.ms.validation;

public class DateValidCheck extends ValidCheck {

    public DateValidCheck() {
        super("birthDate", "(([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])))");
    }
}
