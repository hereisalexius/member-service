package com.hereisalexius.ms.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class ValidCheck implements ConstraintValidator<MemberPorpsValid, Map<String, Object>> {

    private Pattern pattern;
    private Matcher matcher;
    private String key;
    private String regex;


    public ValidCheck(String key, String regex) {
        this.key = key;
        this.regex = regex;
    }

    @Override
    public boolean isValid(Map<String, Object> stringObjectMap, ConstraintValidatorContext constraintValidatorContext) {
        AtomicBoolean result = new AtomicBoolean(true);
        stringObjectMap.computeIfPresent(key, (s, o) -> {
            result.set(validate((String) o));
            return result.get();
        });


        return result.get();
    }

    private boolean validate(String data) {
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(data);
        return matcher.matches();
    }

}
