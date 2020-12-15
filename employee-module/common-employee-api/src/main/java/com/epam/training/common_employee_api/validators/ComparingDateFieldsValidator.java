package com.epam.training.common_employee_api.validators;

import com.epam.training.common_employee_api.validators.annotations.ComparingDateFields;
import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.util.Objects;

public class ComparingDateFieldsValidator implements ConstraintValidator<ComparingDateFields, Object> {

    private ComparingDateFields.Comparing comparing;
    private String nameDataFieldA;
    private String nameDataFieldB;

    @Override
    public void initialize(ComparingDateFields constraintAnnotation) {
        comparing = constraintAnnotation.comparing();
        nameDataFieldA = constraintAnnotation.dateA();
        nameDataFieldB = constraintAnnotation.dateB();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {
        Object valueDateA = new BeanWrapperImpl(object).getPropertyValue(nameDataFieldA);
        Object valueDateB = new BeanWrapperImpl(object).getPropertyValue(nameDataFieldB);
        if (Objects.isNull(valueDateA) || Objects.isNull(valueDateB)) {
            return true;
        }
        LocalDate dateA = LocalDate.parse(valueDateA.toString());
        LocalDate dateB = LocalDate.parse(valueDateB.toString());
        switch (comparing) {
            case A_MORE_B:
                return dateA.isAfter(dateB);
            case B_MORE_A:
                return dateA.isBefore(dateB);
            case A_EQUAL_B:
            case B_EQUAL_A:
                return dateA.isEqual(dateB);
        }
        return false;
    }
}
