package team.greenstudio.modules.sys.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import team.greenstudio.common.annotation.SysLog;
import team.greenstudio.common.utils.Constant;
import team.greenstudio.common.utils.PageUtils;
import team.greenstudio.common.utils.R;
import team.greenstudio.common.validator.Assert;
import team.greenstudio.common.validator.ValidatorUtils;
import team.greenstudio.common.validator.group.AddGroup;
import team.greenstudio.common.validator.group.UpdateGroup;
import team.greenstudio.modules.sys.entity.SysUserEntity;
import team.greenstudio.modules.sys.form.PasswordForm;
import team.greenstudio.modules.sys.service.SysUserRoleService;
import team.greenstudio.modules.sys.service.SysUserService;
import org.apache.commons.lang.ArrayUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 系统用户
 *
 * @author trumangu trumangu1998@gmail.com
 */
@RestController
@RequestMapping("/sys/user")
@Api(value = "ApiValue", tags = {"后台系统接口"})
public class SysUserController extends AbstractController {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserRoleService sysUserRoleService;


    /**
     * 所有用户列表
     */
    @GetMapping("/list")
    @RequiresPermissions("sys:user:list")
    public R list(@RequestParam Map<String, Object> params) {
        //只有超级管理员，才能查看所有管理员列表
        if (getUserId() != Constant.SUPER_ADMIN) {
            params.put("createUserId", getUserId());
        }
        PageUtils page = sysUserService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 获取登录的用户信息
     */
    @GetMapping("/info")
    public R info() {
        return R.ok().put("user", getUser());
    }



    @PostMapping("/password")
    @SysLog("修改密码")
    @ApiOperation("后台用户修改密码")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "prePassword", value = "旧密码", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "newPassword", value = "新密码", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "confirm", value = "确认密码", required = true, dataType = "String")
    })
    public R password(@RequestParam(value = "prePassword") String prePassword,
                      @RequestParam(value = "newPassword") String newPassword,
                      @RequestParam(value = "confirm") String confirm) {
        Assert.isBlank(newPassword, "新密码不为能空");
        Assert.isEqual(newPassword, confirm, "两次密码输入不一致");

        //sha256加密
        String encryptedPassword = new Sha256Hash(prePassword, getUser().getSalt()).toHex();
        //sha256加密
        String newEncryptedPassword = new Sha256Hash(newPassword, getUser().getSalt()).toHex();

        //更新密码
        boolean flag = sysUserService.updatePassword(getUserId(), encryptedPassword, newEncryptedPassword);
        if (!flag) {
            return R.error("原密码不正确");
        }

        return R.ok();
    }

    /**
     * 用户信息
     */
    @GetMapping("/info/{userId}")
    @RequiresPermissions("sys:user:info")
    @ApiOperation("后台查询用户个人信息")
    @ApiImplicitParam(paramType="query", name = "userId", value = "查询的用户id", required = true, dataType = "Long")
    public R info(@PathVariable("userId") Long userId) {
        SysUserEntity user = sysUserService.getById(userId);

        //获取用户所属的角色列表
        List<Long> roleIdList = sysUserRoleService.queryRoleIdList(userId);
        user.setRoleIdList(roleIdList);

        return R.ok().put("user", user);
    }

    /**
     * 保存用户
     */
    @SysLog("保存用户")
    @PostMapping("/save")
    @RequiresPermissions("sys:user:save")
    public R save(@RequestBody SysUserEntity user) {
        ValidatorUtils.validateEntity(user, AddGroup.class);

        user.setCreateUserId(getUserId());
        sysUserService.saveUser(user);

        return R.ok();
    }

    /**
     * 修改用户
     */
    @SysLog("修改用户")
    @PostMapping("/update")
    @RequiresPermissions("sys:user:update")
    public R update(@RequestBody SysUserEntity user) {
        ValidatorUtils.validateEntity(user, UpdateGroup.class);

        user.setCreateUserId(getUserId());
        sysUserService.update(user);

        return R.ok();
    }

    /**
     * 删除用户
     */
    @SysLog("删除用户")
    @PostMapping("/delete")
    @RequiresPermissions("sys:user:delete")
    public R delete(@RequestBody Long[] userIds) {
        if (ArrayUtils.contains(userIds, 1L)) {
            return R.error("系统管理员不能删除");
        }

        if (ArrayUtils.contains(userIds, getUserId())) {
            return R.error("当前用户不能删除");
        }

        sysUserService.deleteBatch(userIds);

        return R.ok();
    }
}
