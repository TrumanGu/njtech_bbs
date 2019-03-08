package team.greenstudio.common.exception;

/**
 * @apiNote 自定义异常类
 * @author TrumanGu trumangu1998@gmail.com
 */
public class GSException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private String msg;
    private int code = 500;

    public GSException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public GSException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public GSException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public GSException(String msg, int code, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }


}
