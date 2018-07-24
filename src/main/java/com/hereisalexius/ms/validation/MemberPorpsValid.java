package com.hereisalexius.ms.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD,ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {DateValidCheck.class, PostalCodeValidCheck.class, ImageURLValidCheck.class})
@Documented
public @interface MemberPorpsValid {
    String message() default "Please provide a valid member info properties";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
