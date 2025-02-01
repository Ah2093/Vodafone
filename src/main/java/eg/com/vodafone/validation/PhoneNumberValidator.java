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

        // Updated regex to match Egyptian and Saudi mobile numbers
        String regex = "^(010[0-9]{8})$";
        return phoneNumber.matches(regex);
    }

}