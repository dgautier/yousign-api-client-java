package io.ginko.yousign.client.model;

import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class CosignerTest {

    private static Validator validator;

    @BeforeClass
    public static void setup() {
        ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
        validator = vf.getValidator();

    }

    @Test
    public void wrongPhoneNoPlus() {
        Cosigner cosigner = Cosigner.builder()
                .phone("12241212112")
                .build();

        Set<ConstraintViolation<Cosigner>> violations = this.validator.validate(cosigner);

        assertThat(violations)
                .isNotEmpty()
        ;
    }

    @Test
    public void wrongPhoneNotEnoughDigit() {
        Cosigner cosigner = Cosigner.builder()
                .phone("+1224")
                .build();

        Set<ConstraintViolation<Cosigner>> violations = this.validator.validate(cosigner);

        assertThat(violations)
                .isNotEmpty()
        ;
    }

    @Test
    public void phoneOk() {
        Cosigner cosigner = Cosigner.builder()
                .phone("+1224232323")
                .build();

        Set<ConstraintViolation<Cosigner>> violations = this.validator.validate(cosigner);

        assertThat(violations)
                .isEmpty()
        ;
    }
}
