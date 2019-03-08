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

    @ApiOperation("用户注册接口")
    @PostMapping("register")
    public R register(RegisterForm form) {
        //表单校验
        ValidatorUtils.validateEntity(form);
        //电话号码合法性检查
        if(! LegalityCheck.PhoneLegalityCheck(form.getMobile()) )
            return R.error("手机号码格式有误！");

        UserEntity user = new UserEntity();
        user.setMobile(form.getMobile());
        user.setUsername(form.getMobile());
        user.setPassword(DigestUtils.sha256Hex(form.getPassword()));
        user.setCreateTime(new Date());
        userService.save(user);

        return R.ok();
    }

    @Autowired
    private UserService userService;
}



