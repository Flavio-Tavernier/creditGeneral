package cgb.classesMetier.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mails")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/send-email")
    public String sendEmail() {
        emailService.sendSimpleMessage("lamarche.hugo@orange.fr", "Rapport virement lot", "This is a test email.");
        return "Email sent!";
    }
}

