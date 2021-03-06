package team.greenstudio.modules.app.controller;


import team.greenstudio.common.utils.LegalityCheck;
import team.greenstudio.common.utils.R;
import team.greenstudio.common.validator.ValidatorUtils;
import team.greenstudio.modules.app.form.LoginForm;
import team.greenstudio.modules.app.service.UserService;
import team.greenstudio.modules.app.utils.JwtUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * APP登录授权
 *
 * @author Mark sunlightcs@gmail.com
 * @author Radoapx 84225343@qq.com
 */
@RestController
@RequestMapping("/app")
@Api(value="用户controller",tags={"app用户账户操作"})
public class AppLoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtils jwtUtils;

    /**
     * 登录
     */
    @ApiOperation("用户登陆接口")
    @PostMapping("login")
    public R login(LoginForm form) {
        //表单校验
        ValidatorUtils.validateEntity(form);
        //邮箱检查
        if(!LegalityCheck.EmailLegalityCheck(form.getUser_email())) return R.error("邮箱有误!");
        //用户登录
        long userId = userService.login(form);
        //生成token
        String token = jwtUtils.generateToken(userId);

        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("expire", jwtUtils.getExpire());

        return R.ok(map);
    }
}
