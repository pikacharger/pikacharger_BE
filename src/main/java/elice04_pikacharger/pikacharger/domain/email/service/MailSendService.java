package elice04_pikacharger.pikacharger.domain.email.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class MailSendService {
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private RedisUtil redisUtil;
    private int authNumber;

    public void makeRandomNumber(){
        Random random = new Random();
        String randomNumber = "";

        for(int i = 0; i < 6; i++){
            randomNumber += Integer.toString(random.nextInt(10));
        }
        authNumber = Integer.parseInt(randomNumber);
    }

    //mail을 어디서 보내는지, 어디로 보내는지, 인증번호를 html 형식으로 어떻게 보내는지
    public String joinEmail(String email){
        makeRandomNumber();
        String setFrom = "masterhyuk0613@gmail.com";
        String toMail = email;
        String title = "회원 가입 인증 이메일 입니다.";
        String content =
                "피카충전을 방문해주셔서 감사합니다." +
                        "<br><br>" +
                        "인증번호는 " + authNumber + "입니다." +
                        "<br>"+
                        "인증번호를 제대로 입력해주세요";
        mailSend(setFrom, toMail, title, content);
        return Integer.toString(authNumber);
    }

    public void mailSend(String setForm, String toMail, String title, String content){
        MimeMessage message = mailSender.createMimeMessage();
        try{
            MimeMessageHelper helper = new MimeMessageHelper(message, true,"utf-8");
            helper.setFrom(setForm);
            helper.setTo(toMail);
            helper.setSubject(title);
            helper.setText(content,true);
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        redisUtil.setDataExpire(Integer.toString(authNumber),toMail,60*5L);
    }

    public boolean CheckAuthNum(String email, String authNum){
        String storedEmail = redisUtil.getData(authNum);
        return storedEmail != null && storedEmail.equals(email);
    }
}
