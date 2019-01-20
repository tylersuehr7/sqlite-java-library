package com.tylersuehr.sql;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Tyler Suehr
 */
public class ContentValuesTest {
    @Test
    public void testInsertData() {
        ContentValues values = new ContentValues();
        values.put("1", "A");
        values.put("2", "B");
        values.put("3", "C");
        Assert.assertTrue(values.size() == 3);
    }

    @Test
    public void testGetData() {
        ContentValues values = new ContentValues();
        values.put("1", "a");
        values.put("2", "b");
        Assert.assertTrue(values.get("1").equals("a"));
    }

    @Test
    public void testFindData() {
        ContentValues values = new ContentValues();
        values.put("1", "a");
        values.put("2", "b");
        values.put("3", 3.42f);
        values.put("4", 823.23421);

        float value = values.getSerializable("3");
        Assert.assertTrue(value == 3.42f);
    }
}