package mx.com.telce.dss.validatorpwd;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.LengthRule;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.RuleResult;
import org.passay.WhitespaceRule;


public class PasswordContrainsValidator implements ConstraintValidator<ValidPassword, String> {
	
    @Override
    public void initialize(ValidPassword arg0) {
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        PasswordValidator validator = new PasswordValidator(Arrays.asList(
            // minimo 12 caracteres
            new LengthRule(8, 12),

            // almenos un caracter en mayuscula
            new CharacterRule(EnglishCharacterData.UpperCase, 1),

            // almenos un caracter en minuscula
            new CharacterRule(EnglishCharacterData.LowerCase, 1),

            // almenos un digito
            new CharacterRule(EnglishCharacterData.Digit, 1),

            // almenos un caracter especial
            new CharacterRule(EnglishCharacterData.Special, 1),

            // sin espacios
            new WhitespaceRule()

        ));
        RuleResult result = validator.validate(new PasswordData(password));
        if (result.isValid()) {
            return true;
        }
        List<String> messages = validator.getMessages(result);
        String messageTemplate = messages.stream()
            .collect(Collectors.joining(","));
        context.buildConstraintViolationWithTemplate(messageTemplate)
            .addConstraintViolation()
            .disableDefaultConstraintViolation();
        return false;
    }
	
}
