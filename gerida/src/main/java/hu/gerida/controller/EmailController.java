package hu.gerida.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import hu.gerida.repository.ParentRepository;

@RestController
public class EmailController {

    @Autowired
    ParentRepository pRepository;
/*
    @PostMapping("/sendemail")
    public @ResponseBody ResponseEntity<String> email() {
        
        return new ResponseEntity<String>("Response from POST method", HttpStatus.OK);
    }*/

/*    @RequestMapping(value = "/sendemail", method = RequestMethod.GET)
    @ResponseBody
    public String sendEmail(@RequestParam(required = false) String email, String subject, String content)
            throws MessagingException {        
        sendmail(email, subject, content);
        return "Email sent successfully";
    }*/

    @CrossOrigin
    @PostMapping("/sendemail")
    public String sendemail(@RequestBody String email, String subject, String content) throws MessagingException {
        sendmail(email, subject, content);
        return "Email sent successfully";
    }

    private void sendmail(String email, String subject, String content) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
           protected PasswordAuthentication getPasswordAuthentication() {
              return new PasswordAuthentication("szuloteszt@gmail.com", "Pass word");
           }
        });
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("szuloteszt@gmail.com", false));
        msg.setSubject(subject);
        msg.setContent(content, "text/html");
     
        List<String> emails = pRepository.getAllEmails();
        switch(email){
            case "all":
                emails = pRepository.getAllEmails();
            break;
            case "first":
                emails = pRepository.getFirstCampEmails();
            break;
            case "byCamp":
                String[] splittedEmail = email.split(";");
                emails = pRepository.getEmailsByCamp(splittedEmail[1]);
            break;
            default:
                emails = pRepository.getAllEmails();
        }
        for(String e : emails){
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(e));
            msg.setSentDate(new Date());
            Transport.send(msg);  
        } 
     }
}