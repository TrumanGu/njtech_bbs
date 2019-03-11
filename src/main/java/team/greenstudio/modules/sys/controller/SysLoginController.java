package team.greenstudio.modules.sys.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import team.greenstudio.common.utils.R;
import team.greenstudio.modules.sys.entity.SysUserEntity;
import team.greenstudio.modules.sys.service.SysUserService;
import team.greenstudio.modules.sys.service.SysUserTokenService;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * @author TrumanGu trumangu1998@gmail.com
 */

@Api(description = "有关后台操作的接口", tags = {"后台系统接口"})
@RestController
public class SysLoginController extends AbstractController {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserTokenService sysUserTokenService;


    @ApiOperation(value = "后台用户登录", tags = {"后台系统接口"}, notes = "")
    @PostMapping("/sys/login")
    public Map<String, Object> login(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password) {
        SysUserEntity user = sysUserService.queryByUserName(username);
        //账号不存在、密码错误
        if (user == null || !user.getPassword().equals(new Sha256Hash(password, user.getSalt()).toHex())) {
            return R.error("账号或密码不正确");
        } else if (user.getStatus() == 0) {
            //账号锁定
            return R.error("账号已被锁定,请联系管理员");
        }
        //生成token，并保存到数据库
        return sysUserTokenService.createToken(user.getUserId());
    }


    @ApiOperation(value = "后台用户注销", tags = {"后台系统接口"}, notes = "")
    @PostMapping("/sys/logout")
    public R logout() {
        sysUserTokenService.logout(getUserId());
        return R.ok();
    }

}
