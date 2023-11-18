package registration.validator;

import org.springframework.stereotype.Service;

import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UsernameValidator implements Predicate<String> {

    @Override
    public boolean test(String username) {
        String usernameRegex = "^[A-Za-z0-9]+$";
        Pattern pattern = Pattern.compile(usernameRegex);
        Matcher matcher = pattern.matcher(username);
        return matcher.find();
    }
}
