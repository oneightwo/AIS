package com.epam.training.common_employee_api.validators.annotations;

import com.epam.training.common_employee_api.validators.ComparingDateFieldsValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ComparingDateFieldsValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ComparingDateFields {

    enum Comparing {
        A_MORE_B, B_MORE_A, A_EQUAL_B, B_EQUAL_A
    }

    String message() default "Invalid date";

    String dateA();

    String dateB();

    Comparing comparing();

    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
        ComparingDateFields[] value();
    }

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
