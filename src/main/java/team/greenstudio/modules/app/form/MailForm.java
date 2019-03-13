package team.greenstudio.modules.app.form;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 邮箱验证表单
 * @author radoapx 845225343@qq.com
 */
@Data
@ApiModel(value = "邮箱验证表单")
class MailForm {
    @ApiModelProperty(value = "邮箱")
    @NotBlank(message = "邮箱不能为空")
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
