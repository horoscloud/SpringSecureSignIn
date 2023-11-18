package appuser;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@AllArgsConstructor
@RequestMapping("email-confirmation")
public class AppUserController {

    private final AppUserService appUserService;

    @GetMapping(path = "resend")
    public void resendEmail(@RequestParam("username") String username){

        if(appUserService.findAppUserByUsername(username)!=null)
            appUserService.singUpUser(appUserService.findAppUserByUsername(username));
    }

    @GetMapping
    public ModelAndView emailConfirmationView(@RequestParam("username") String username){
        ModelAndView mav = new ModelAndView("email-confirmation");

        mav.addObject("username", username);

        return mav;
    }


}
