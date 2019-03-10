package team.greenstudio.modules.app.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import team.greenstudio.common.exception.GSException;
import team.greenstudio.common.validator.Assert;
import team.greenstudio.modules.app.dao.UserDao;
import team.greenstudio.modules.app.entity.UserEntity;
import team.greenstudio.modules.app.form.LoginForm;
import team.greenstudio.modules.app.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;
/**
 * @author radoapx 845225343@qq.com
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {

    @Override
    public UserEntity queryByMobile(String mobile) {
        return baseMapper.selectOne(new QueryWrapper<UserEntity>().eq("mobile", mobile));
    }
    @Override
    public UserEntity queryByEmail(String email) {
        return baseMapper.selectOne(new QueryWrapper<UserEntity>().eq("user_email", email));
    }

    @Override
    public long login(LoginForm form) {

        UserEntity user = queryByEmail(form.getUser_email());
        Assert.isNull(user, "邮箱错误!");

        if (!user.getPassword().equals(DigestUtils.sha256Hex(form.getPassword()))) {
            throw new GSException("密码错误!");
        }
        return user.getUserId();
    }

    @Override
    public boolean saveUUID() {
        return false;
    }
}
