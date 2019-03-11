package team.greenstudio.common.validator;

import team.greenstudio.common.exception.GSException;
import org.apache.commons.lang.StringUtils;

/**
 * 对数据进行校验
 * @author trumangu trumangu1998@gmail.com
 */
public abstract class Assert {

    public static void isBlank(String str, String message) {
        if (StringUtils.isBlank(str)) {
            throw new GSException(message);
        }
    }

    public static void isNull(Object object, String message) {
        if (object == null) {
            throw new GSException(message);
        }
    }

    public static void isEqual(Object object1, Object object2, String message) {
        if (!object1.equals(object2)) {
            throw new GSException(message);
        }
    }
}
