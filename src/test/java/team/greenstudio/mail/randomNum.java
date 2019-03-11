package team.greenstudio.mail;
import java.util.Random;

import sun.awt.geom.AreaOp;

/**
 * @author radoapx 845225343@qq.com
 */
class randomNum {
    public static void main(String[] args) {
        for (int i=0;i<1000;i++){
            System.out.println(getRandomCode());
        }
    }
    public static String getRandomCode(){
        String str="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb=new StringBuilder(6);
        for(int i=0;i<6;i++) {
            char ch=str.charAt(new Random().nextInt(str.length()));
            sb.append(ch);
        }
        return sb.toString();
    }
}



