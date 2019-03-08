package team.greenstudio;

import java.util.HashMap;
import java.util.Map;

class HashMapTest {
    public static void main(String args[]){
        Map<String, Object> map = new HashMap<>();
        map.put("姓名","阿赫");
        System.out.println(map);
        map.put("学号",12138);
        System.out.println(map);

    }
}
