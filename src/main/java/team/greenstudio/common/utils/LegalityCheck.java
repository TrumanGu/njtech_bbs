package team.greenstudio.common.utils;

import java.util.regex.Pattern;

/**
 * @author 845225343@qq.com
 */
public class LegalityCheck {

    /**
     * @param String类型的11位phoneNumber
     * @return true合法
     */
    public static boolean PhoneLegalityCheck(String phoneNumber) {
        System.out.println(phoneNumber.substring(0, 1));
        return phoneNumber.matches("\\d{11}") && Integer.valueOf(phoneNumber.substring(0, 1)).intValue() == 1;
    }

    /**
     *
     * @param email address
     * @return true if legal
     */
    public static boolean EmailLegalityCheck(String email) {
        String regex = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
        return Pattern.matches(regex,email);
    }
}
