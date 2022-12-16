package com.alibaba;

import com.alibaba.fastjson.JSONArray;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;

public class JSONArrayTest_toList {

    @Test
    public void toList() {
        String jsonArrayStr =
                "[" +
                        "[1,2," +
                        "{\"key3\":true}" +
                        "]," +
                        "{\"key1\":\"val1\",\"key2\":" +
                        "{\"key2\":null}," +
                        "\"key3\":42,\"key4\":[]" +
                        "}," +
                        "[" +
                        "[\"value1\",2.1]" +
                        "," +
                        "[null]" +
                        "]" +
                        "]";

        JSONArray jsonArray = JSONArray.parseArray(jsonArrayStr);
        List<?> list = jsonArray.toJavaList(Object.class);

        assertTrue("List should not be null", list != null);
        assertTrue("List should have 3 elements", list.size() == 3);

        List<?> val1List = (List<?>) list.get(0);
        assertTrue("val1 should not be null", val1List != null);
        assertTrue("val1 should have 3 elements", val1List.size() == 3);

        assertTrue("val1 value 1 should be 1", val1List.get(0).equals(Integer.valueOf(1)));
        assertTrue("val1 value 2 should be 2", val1List.get(1).equals(Integer.valueOf(2)));

        Map<?,?> key1Value3Map = (Map<?,?>)val1List.get(2);
        assertTrue("Map should not be null", key1Value3Map != null);
        assertTrue("Map should have 1 element", key1Value3Map.size() == 1);
        assertTrue("Map key3 should be true", key1Value3Map.get("key3").equals(Boolean.TRUE));

        Map<?,?> val2Map = (Map<?,?>) list.get(1);
        assertTrue("val2 should not be null", val2Map != null);
        assertTrue("val2 should have 4 elements", val2Map.size() == 4);
        assertTrue("val2 map key 1 should be val1", val2Map.get("key1").equals("val1"));
        assertTrue("val2 map key 3 should be 42", val2Map.get("key3").equals(Integer.valueOf(42)));

        Map<?,?> val2Key2Map = (Map<?,?>)val2Map.get("key2");
        assertTrue("val2 map key 2 should not be null", val2Key2Map != null);
        assertTrue("val2 map key 2 should have an entry", val2Key2Map.containsKey("key2"));
        assertTrue("val2 map key 2 value should be null", val2Key2Map.get("key2") == null);

        List<?> val2Key4List = (List<?>)val2Map.get("key4");
        assertTrue("val2 map key 4 should not be null", val2Key4List != null);
        assertTrue("val2 map key 4 should be empty", val2Key4List.isEmpty());

        List<?> val3List = (List<?>) list.get(2);
        assertTrue("val3 should not be null", val3List != null);
        assertTrue("val3 should have 2 elements", val3List.size() == 2);

        List<?> val3Val1List = (List<?>)val3List.get(0);
        assertTrue("val3 list val 1 should not be null", val3Val1List != null);
        assertTrue("val3 list val 1 should have 2 elements", val3Val1List.size() == 2);
        assertTrue("val3 list val 1 list element 1 should be value1", val3Val1List.get(0).equals("value1"));
        assertTrue("val3 list val 1 list element 2 should be 2.1", val3Val1List.get(1).equals(new BigDecimal("2.1")));

        List<?> val3Val2List = (List<?>)val3List.get(1);
        assertTrue("val3 list val 2 should not be null", val3Val2List != null);
        assertTrue("val3 list val 2 should have 1 element", val3Val2List.size() == 1);
        assertTrue("val3 list val 2 list element 1 should be null", val3Val2List.get(0) == null);

        // assert that toList() is not a deep copy
        jsonArray.getJSONObject(1).put("key1", "still val1");
        assertTrue("val2 map key 1 should be val1", val2Map.get("key1").equals("still val1"));

        // assert that the new list is mutable
        assertTrue("Removing an entry should succeed", list.remove(2) != null);
        assertTrue("List should have 2 elements", list.size() == 2);
    }

}
