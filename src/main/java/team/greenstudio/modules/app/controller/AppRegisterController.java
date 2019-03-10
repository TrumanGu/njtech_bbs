/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package team.greenstudio.modules.app.controller;


import io.swagger.annotations.ApiOperation;
import team.greenstudio.common.utils.LegalityCheck;
import team.greenstudio.common.utils.R;
import team.greenstudio.common.validator.ValidatorUtils;
import team.greenstudio.modules.app.entity.UserEntity;
import team.greenstudio.modules.app.form.RegisterForm;
import team.greenstudio.modules.app.service.UserService;
import io.swagger.annotations.Api;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.greenstudio.modules.app.mailVerify.*;
import java.util.Date;

/**
 * 注册
 *
 * @author Mark sunlightcs@gmail.com
 * @author Radoapx 84225343@qq.com
 */
@RestController
@RequestMapping("/app")
@Api(value="用户controller",tags={"app用户账户操作"})
public class AppRegisterController {
    @Autowired
    private UserService userService;

    @ApiOperation("用户注册接口")
    @PostMapping("register")
    public R register(RegisterForm form) {
        //表单校验
        ValidatorUtils.validateEntity(form);

        UserEntity user = new UserEntity();
        user.setUser_email(form.getUser_email());
        user.setUsername(form.getUser_email());
        user.setPassword(DigestUtils.sha256Hex(form.getPassword()));
        user.setCreateTime(new Date());
        userService.save(user);

        return R.ok();
    }
}



