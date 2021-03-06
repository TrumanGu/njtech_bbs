/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package team.greenstudio.modules.app.service;


import com.baomidou.mybatisplus.extension.service.IService;
import team.greenstudio.modules.app.entity.UserEntity;
import team.greenstudio.modules.app.form.LoginForm;

/**
 * 用户
 *
 * @author Mark sunlightcs@gmail.com
 */
public interface UserService extends IService<UserEntity> {

    UserEntity queryByMobile(String mobile);
    UserEntity queryByEmail(String email);
    /**
     * 用户登录
     *
     * @param form 登录表单
     * @return 返回用户ID
     */
    long login(LoginForm form);

    boolean saveUUID();
}
