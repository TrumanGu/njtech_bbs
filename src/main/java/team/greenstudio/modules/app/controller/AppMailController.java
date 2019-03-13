package team.greenstudio.modules.app.controller;
import com.sun.mail.util.MailSSLSocketFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import team.greenstudio.common.utils.LegalityCheck;
import team.greenstudio.common.utils.R;

/**
 * 发送邮件
 *
 * @author Radoapx 84225343@qq.com
 */
@RestController
@RequestMapping("/app")
@Api(value="用户controller",tags={"app用户账户操作"})
public class AppMailController  {
    /**
     *
     * @param email 该用户输入的验证邮箱
     * @return true发送成功
     */
    @ApiOperation("发送邮件接口")
    @PostMapping("sendEmail")
    public R sendEmail(String email) {
        // 1.创建连接对象javax.mail.Session
        // 2.创建邮件对象 javax.mail.Message
        // 3.发送一封激活邮件
        if(!LegalityCheck.EmailLegalityCheck(email)) return R.error("邮箱格式错误!");
        String code=getRandomCode();//生成验证码
        String from = "845225343@qq.com";// 发件人电子邮箱
        String host = "smtp.qq.com"; // 指定发送邮件的主机smtp.qq.com(QQ)|smtp.163.com(网易)

        Properties properties = System.getProperties();// 获取系统属性
        properties.setProperty("mail.smtp.host", host);// 设置邮件服务器
        properties.setProperty("mail.smtp.auth", "true");// 打开认证

        try {
            //QQ邮箱需要下面这段代码，163邮箱不需要
            MailSSLSocketFactory sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
            properties.put("mail.smtp.ssl.enable", "true");
            properties.put("mail.smtp.ssl.socketFactory", sf);


            // 1.获取默认session对象
            Session session = Session.getDefaultInstance(properties, new Authenticator() {
                public PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("845225343@qq.com", "dojupqnynwcsbcjc"); // 发件人邮箱账号、授权码
                }
            });

            // 2.创建邮件对象
            Message message = new MimeMessage(session);
            // 2.1设置发件人
            message.setFrom(new InternetAddress(from));
            // 2.2设置接收人
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            // 2.3设置邮件主题
            message.setSubject("<-=NJTECHBBS账号激活=->");
            // 2.4设置邮件内容
            String content = "<html><head></head><body><h1>这是一封激活邮件,激活请点击以下链接</h1><h3><a href='http://localhost:8080/RegisterDemo/ActiveServlet?code="
                    + code + "'>http://localhost:8080/RegisterDemo/ActiveServlet?code=" + code
                    + "</href></h3></body></html>";
            message.setContent(content, "text/html;charset=UTF-8");
            // 3.发送邮件
            Transport.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return R.ok(code);
    }

    /**
     *
     * @return 生成的六位验证码
     */
    public static String getRandomCode(){
        String str="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb=new StringBuilder(6);
        for(int i=0;i<6;i++) {
            char ch=str.charAt(new Random().nextInt(str.length()));
            sb.append(ch);
        }
        return sb.toString();
    }
}
