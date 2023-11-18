package registration;

import appuser.AppUser;
import appuser.AppUserRole;
import appuser.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import registration.token.ConfirmationToken;
import registration.token.ConfirmationTokenService;
import registration.validator.EmailValidator;
import registration.validator.PasswordValidator;
import registration.validator.UsernameValidator;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class UserRegistrationService {

    private final AppUserService appUserService;

    private UsernameValidator usernameValidator;
    private EmailValidator emailValidator;
    private PasswordValidator passwordValidator;

    private final ConfirmationTokenService confirmationTokenService;

    public String register(UserRegistrationRequest request){

        boolean usernameIsValid = usernameValidator.test(request.getUsername());
        boolean emailIsValid = emailValidator.test(request.getEmail());
        boolean passwordIsValid = passwordValidator.test(request.getPassword());

        if(!usernameIsValid || !emailIsValid) {
            throw new IllegalStateException("Email or Username not valid");
        }

        if(!passwordIsValid) {
            throw new IllegalStateException("The password needs at least 8 Characters, 1 Upper-Case Letter and 1 Number");
        }


        String token = appUserService.singUpUser(new AppUser(
                request.getUsername(),
                request.getEmail(),
                request.getPassword(),
                AppUserRole.USER
        ));

        return token;
    }


    @Transactional
    public String confirmToken(String token){
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("Token not found"));

        if(confirmationToken.getConfirmedAt() != null){
            throw new IllegalStateException("Email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if(expiredAt.isBefore(LocalDateTime.now())){
            confirmationTokenService.deleteConfirmationToken(confirmationTokenService.getToken(token).get());
            throw new IllegalStateException("token is expired");
        }

        confirmationTokenService.setConfirmedAt(token);

        appUserService.enableAppUser(confirmationToken.getAppUser().getEmail());
        return "confirmed";
    }



}
