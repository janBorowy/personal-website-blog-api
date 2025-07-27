package pl.borovy.personalwebsiteblogapi.model.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.borovy.personalwebsiteblogapi.post.PostRepository;

@Component
@RequiredArgsConstructor
public class UniqueTitleValidator implements ConstraintValidator<UniqueTitle, String> {

    private final PostRepository postRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return !postRepository.existsPostByTitle(value);
    }
}
