package com.alibaba.fastjson.util;

import junit.framework.TestCase;

import java.util.List;

public class FieldInfoGetDeclaredClassTest extends TestCase {
    private FieldInfo fieldInfo;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        fieldInfo = new FieldInfo("Foo", Foo.class, Foo.class.getField("bar").getDeclaringClass(),
                Foo.class.getField("bar").getType(), Foo.class.getField("bar"),
                0, 0, 0);
    }

    public void testDeclaringClass() throws Exception {
        assertEquals(Foo.class, fieldInfo.getDeclaredClass());
    }

    private static class Foo {
        @SuppressWarnings("unused")
        public transient List<String> bar;
    }
}
