package com.google.gson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

public class JsonObjectTest_has {

    @Test
    public void jsonObjectByBean2() {
        final JsonObject jsonObject = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create()
                .toJsonTree(new MyBeanCustomName()).getAsJsonObject();
        assertNotNull(jsonObject);
        assertEquals("Wrong number of keys found:",
                5,
                jsonObject.keySet().size());
        assertFalse("Normal field name (someString) processing did not work",
                jsonObject.has("someString"));
        assertFalse("Normal field name (myDouble) processing did not work",
                jsonObject.has("myDouble"));
        assertFalse("Normal field name (someFloat) processing did not work",
                jsonObject.has("someFloat"));
        assertFalse("Ignored field not found!",
                jsonObject.has("ignoredInt"));
        // getSomeInt() has no user-defined annotation
        assertTrue("Normal field name (someInt) should have been found",
                jsonObject.has("someInt"));
        // the user-defined annotation does not replace any value, so someLong should be found
        assertFalse("Normal field name (someLong) should have not been found",
                jsonObject.has("someLong"));
        // myStringField replaces someString property name via user-defined annotation
        assertTrue("Overridden String field name (myStringField) should have been found",
                jsonObject.has("myStringField"));
        // weird name replaces myDouble property name via user-defined annotation
        assertTrue("Overridden String field name (Some Weird NAme that Normally Wouldn't be possible!) should have been found",
                jsonObject.has("Some Weird NAme that Normally Wouldn't be possible!"));
        // InterfaceField replaces someFloat property name via user-defined annotation
        assertTrue("Overridden String field name (InterfaceField) should have been found",
                jsonObject.has("InterfaceField"));
    }

    @Test
    public void jsonObjectByBean3() {
        final JsonObject jsonObject = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create()
                .toJsonTree(new MyBeanCustomNameSubClass()).getAsJsonObject();

        assertNotNull(jsonObject);
        assertEquals("Wrong number of keys found:",
                11,
                jsonObject.keySet().size());

        assertTrue("Normal int field name (someInt) not found",
                jsonObject.has("someInt"));
        assertFalse("Normal field name (myDouble) processing did not work",
                jsonObject.has("myDouble"));
        // myDouble was replaced by weird name, and then replaced again by AMoreNormalName via user-defined annotation
        assertTrue("Overridden String field name (Some Weird NAme that Normally Wouldn't be possible!) should be FOUND!",
                jsonObject.has("Some Weird NAme that Normally Wouldn't be possible!"));
        assertTrue("Normal field name (someFloat) not found",
                jsonObject.has("someFloat"));
        assertFalse("Ignored field not found!",
                jsonObject.has("ignoredInt"));
        // shouldNotBeJSON property name was first ignored, then replaced by ShouldBeIgnored via user-defined annotations
        assertFalse("Ignored field at the same level as forced name should not have been found",
                jsonObject.has("ShouldBeIgnored"));
        // able property name was replaced by Getable via user-defined annotation
        assertFalse("Normally ignored field (able) with explicit property name should not have been found",
                jsonObject.has("able"));
        // property name someInt was replaced by newIntFieldName via user-defined annotation
        assertTrue("Overridden int field name (newIntFieldName) should have been found",
                jsonObject.has("newIntFieldName"));
        // property name someLong was not replaced via user-defined annotation
        assertFalse("Normal field name (someLong) should have not been found, because its name has changed to ''",
                jsonObject.has("someLong"));
        // property name someString was replaced by myStringField via user-defined annotation
        assertTrue("Overridden String field name (myStringField) should have been found",
                jsonObject.has("myStringField"));
        // property name myDouble was replaced by a weird name, followed by AMoreNormalName via user-defined annotations
        assertTrue("Overridden double field name (AMoreNormalName) should have been found",
                jsonObject.has("AMoreNormalName"));
        // property name someFloat was replaced by InterfaceField via user-defined annotation
        assertTrue("Overridden String field name (InterfaceField) should have been found",
                jsonObject.has("InterfaceField"));
        // property name ignoredInt was replaced by none, followed by forcedInt via user-defined annotations
        assertTrue("Forced field should have been found!",
                jsonObject.has("forcedInt"));
        // property name able was replaced by Getable via user-defined annotation
        assertTrue("Overridden boolean field name (Getable) should have been found",
                jsonObject.has("Getable"));
    }

    @Test
    public void jsonObjectValues() {
        String str =
                "{"+
                        "\"trueKey\":true,"+
                        "\"falseKey\":false,"+
                        "\"trueStrKey\":\"true\","+
                        "\"falseStrKey\":\"false\","+
                        "\"stringKey\":\"hello world!\","+
                        "\"intKey\":42,"+
                        "\"intStrKey\":\"43\","+
                        "\"longKey\":1234567890123456789,"+
                        "\"longStrKey\":\"987654321098765432\","+
                        "\"doubleKey\":-23.45e7,"+
                        "\"doubleStrKey\":\"00001.000\","+
                        "\"BigDecimalStrKey\":\"19007199254740993.35481234487103587486413587843213584\","+
                        "\"negZeroKey\":-0.0,"+
                        "\"negZeroStrKey\":\"-0.0\","+
                        "\"arrayKey\":[0,1,2],"+
                        "\"objectKey\":{\"myKey\":\"myVal\"}"+
                        "}";
        JsonObject jsonObject = JsonParser.parseString(str).getAsJsonObject();

        assertTrue("stringKey should exist",
                jsonObject.has("stringKey"));

    }

    @Test
    public void testIssue548ObjectWithEmptyJsonArray() {
        JsonObject jsonObject = JsonParser.parseString("{\"empty_json_array\": []}").getAsJsonObject();
        assertTrue("missing expected key 'empty_json_array'", jsonObject.has("empty_json_array"));
    }

    private class MyBeanCustomName extends MyBeanCustomNameInterface {
        @Expose
        private int someInt = 42;

        @SerializedName("")
        @Expose
        private long someLong = 42;

        @SerializedName("myStringField")
        @Expose
        private String someString = "someStringValue";

        @SerializedName("Some Weird NAme that Normally Wouldn't be possible!")
        @Expose
        private double myDouble = 0.0d;

        @Expose(serialize = false)
        private int ignoredInt = 40;
    }

    public class MyBeanCustomNameSubClass extends MyBeanCustomName {
        @SerializedName("forcedInt")
        @Expose
        private int forcedInt = 42*42;

        @SerializedName("newIntFieldName")
        @Expose
        private int someInt = 43;

        @Expose
        private String someString = "subClassString";

        @SerializedName("AMoreNormalName")
        @Expose
        private double myDouble = 1.0d;

        @Expose
        private float someFloat = 3.0f;

        @Expose(serialize = false)
        @SerializedName("ShouldBeIgnored")
        private boolean shouldNotBeJSON = true;

        @SerializedName("Getable")
        @Expose
        private boolean getable = true;
    }

    private abstract static class MyBeanCustomNameInterface {
        @SerializedName("InterfaceField")
        @Expose
        protected float someFloat = 5f;
    }



}
