package com.patsi.validator;

import com.common.utils.ValidationHelper;
import com.patsi.annotations.IsPassword;
import com.patsi.repository.ProfanityWordRepository;
import com.patsi.utils.ListHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class PasswordValidator implements Validator {
    @Autowired
    private ProfanityWordRepository profanityWordRepository;

    public Class accept() {
        return IsPassword.class;
    }

    public List<String> validate(Object input, Map<String, Object> field) {
        return validateWithParam(input.toString(), (int) field.get("min"), (int) field.get("max"));
    }

    public List<String> validateWithParam(String input, int min, int max) {
        List<String> errorList = ListHelper.newList();
        if (!ValidationHelper.validateLength(input, min, max)) {
            errorList.add(String.format("Password Must be between %d and %d characters!", min, max));
        }
        if (!ValidationHelper.validateContainsSpecialCharacter(input)) {
            errorList.add("Password Must contains at least one special characters (ie. !*?$ )!");
        }
        return errorList;
    }
}
