package org.json;

import junit.framework.TestCase;

import java.util.List;
import java.util.stream.Collectors;

public class JSONArrayToListTest extends TestCase {
    public void test_for_issue() throws Exception {
        String text = "[1,2,3]";
        JSONArray array = new JSONArray(text);
        List<Object> list = array.toList();

        List<Long> longs = list.stream()
                .map(element-> Long.valueOf((Integer) element))
                .collect(Collectors.toList());

        assertEquals(1L, longs.get(0).longValue());
        assertEquals(2L, longs.get(1).longValue());
        assertEquals(3L, longs.get(2).longValue());
    }
}
