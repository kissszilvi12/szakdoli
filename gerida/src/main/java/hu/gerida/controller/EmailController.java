package hu.gerida.controller;

import java.text.ParseException;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import hu.gerida.model.Camp;
import hu.gerida.model.Parent;
import hu.gerida.model.Person;
import hu.gerida.repository.CampRepository;
import hu.gerida.repository.ParentRepository;

@RestController
public class EmailController {

    @Autowired
    ParentRepository pRepository;
    @Autowired
    CampRepository cRepository;

    @CrossOrigin
    @PostMapping("/sendemail")
    public String sendemail(@RequestBody String request, String email, String subject, String content) throws MessagingException, ParseException {
        sendmail(email, subject, content);
        return "Email sent successfully";
    }

    private void sendmail(final String to, final String subject, final String content) throws MessagingException, ParseException {
        final Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        final Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("szuloteszt@gmail.com", "Pass word");
            }
        });
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("szuloteszt@gmail.com", false));
        msg.setSubject(subject);

        List<Parent> parents;
        switch (to) {
            case "all":
                parents = pRepository.getAllParents();
                for (Parent p : parents) {
                    String newContent = changeData(content, p);
                    msg.setContent(newContent, "text/plain");
                    msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(p.getEmail()));
                    msg.setSentDate(new java.util.Date());
                    Transport.send(msg);
                }
                break;
            case "first":
                parents = pRepository.getFirstCampParents();
                for (Parent p : parents) {
                    String newContent = changeData(content, p);
                    msg.setContent(newContent, "text/plain");
                    msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(p.getEmail()));
                    msg.setSentDate(new java.util.Date());
                    Transport.send(msg);
                }
                break;
            default:
                parents = pRepository.getParentsByCamp(Long.parseLong(to));
                System.out.println(parents.toString()+" PARENT******");
                Camp camp = cRepository.findById(Integer.parseInt(to));
                for (Parent p : parents) {
                    System.out.println(p.getEmail()+" EMAIL******");
                    String newContent = changeData(content, p, camp);
                    msg.setContent(newContent, "text/plain");
                    msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(p.getEmail()));
                    msg.setSentDate(new java.util.Date());
                    Transport.send(msg);
                }
        }
    }

    private String changeData(String content, Parent p, Camp c){
        content = content.replace("{szulo_nev}", p.getName());
        String children="";
        for(Person child : p.getChildren()){
            children += child.getName()+",";
        }
        content = content.replace("{gyerek_nev}", children);
        content = content.replace("{tabor_nev}", c.getName());
        content = content.replace("{tabor_kezdet}", String.valueOf(c.getFrom()));
        content = content.replace("{tabor_vege}", String.valueOf(c.getTill()));
        return content;
     }

    private String changeData(String content, Parent p) {
        content = content.replace("{nev}", p.getName());
        String children="";
        for(Person child : p.getChildren()){
            children += child.getName()+",";
        }
        if (children.length()>0)
            children.substring(0, children.length()-1);
        content = content.replace("{gyerek_nev}", children);
        return content;
     }
}