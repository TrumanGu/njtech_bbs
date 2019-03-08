package team.greenstudio.common.utils;


/**
 * @author 845225343@qq.com
 */
public class LegalityCheck {

    /**
     *
     * @param String类型的11位phoneNumber
     * @return true合法
     */
    public static boolean PhoneLegalityCheck(String phoneNumber){
//        String ch="\\d{11}";
//        if(!phoneNumber.matches(ch)) return false;
////        if(!phoneNumber.startsWith("86")) return 3;
//        return true;

        return phoneNumber.matches("\\d{11}");
    }
}
