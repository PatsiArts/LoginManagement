package com.patsi.validator;

import com.common.validation.repository.ProfanityWordsRepository;
import com.common.validation.utils.ValidationHelper;
import com.patsi.annotations.IsUserName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class UserNameValidation implements Validator {
    @Autowired
    private ProfanityWordsRepository profanityWordsRepository;

    public Class accept() {
        return IsUserName.class;
    }

    public List<String> validate(Object input, Map<String, Object> field) {
        List<String> profanityWordList = profanityWordsRepository.findAll().stream()
            .map(profanityWords -> profanityWords.getWord())
            .toList();
        if (ValidationHelper.validateProfanity(input.toString(), profanityWordList)) {
            return List.of();
        } else {
            return List.of("UserName Must not contain Profanity words!");
        }
    }
}
