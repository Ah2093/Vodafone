package eg.com.vodafone.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Objects;


public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, String> {

    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext context) {
        if (Objects.isNull(phoneNumber) || phoneNumber.isEmpty()) {
            return false;
        }

        String regex = "^(01[0125][0-9]{8})$";
        return phoneNumber.matches(regex);
    }

}