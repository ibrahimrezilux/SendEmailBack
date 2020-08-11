package com.diallo.controller;


import com.diallo.entities.SendModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/testapp")
public class EmailSender {

    @Autowired
    private JavaMailSender sender;

    @RequestMapping("/employees")
    public @ResponseBody
    SendModel sendMail(@RequestBody SendModel sendModel) throws Exception {

        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        Map<String, Object> model = new HashMap<>();
        model.put("objet", sendModel.getObjet());
        model.put("message", sendModel.getMessage());
        model.put("email", sendModel.getEmail());

        try {
            helper.setSubject(sendModel.getObjet());
            helper.setText(sendModel.getMessage());
            helper.setTo(sendModel.getEmail());
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        sender.send(message);

        return sendModel;
    }
}
