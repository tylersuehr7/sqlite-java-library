package com.tylersuehr.sql;
import org.junit.Assert;
import org.junit.Test;

/**
 * Copyright 2017 Tyler Suehr
 * Created by tyler on 8/23/2017.
 */
public class SQLBuilderTest {
    @Test
    public void testProjectionQuery() {
        final String[] projection = new String[] { "name", "username", "password" };
        final String table = "users";
        final String where = "[id]=3";
        final String order = "[name] DESC";

        SQLBuilder builder = new SQLBuilder();
        String sql = builder.createQuery(table, projection, where, order, "10");
        String expected = "SELECT ([name],[username],[password]) FROM [users] WHERE [id]=3 ORDER BY [name] DESC LIMIT 10;";

        Assert.assertEquals(sql, expected);
    }

    @Test
    public void testQuery() {
        final String table = "users";
        final String where = "[id]=3";
        final String order = "[name] DESC";

        SQLBuilder builder = new SQLBuilder();
        String sql = builder.createQuery(table, where, order, "10");
        String expected = "SELECT * FROM [users] WHERE [id]=3 ORDER BY [name] DESC LIMIT 10;";

        Assert.assertEquals(sql, expected);
    }

    @Test
    public void testInsertStatement() {
        final ContentValues values = new ContentValues();
        values.put("name", "Tyler");
        values.put("username", "tyler123");
        values.put("password", "tyler123");

        final String table = "users";

        SQLBuilder builder = new SQLBuilder();
        String sql = builder.createInsert(table, values);
        String expected = "INSERT INTO [users] ([name],[username],[password]) VALUES ('Tyler','tyler123','tyler123');";

        Assert.assertEquals(sql, expected);
    }

    @Test
    public void testUpdateStatement() {
        final ContentValues values = new ContentValues();
        values.put("name", "Tyler");
        values.put("username", "tyler123");
        values.put("password", "tyler123");

        final String table = "users";
        final String where = "[id]=3";

        SQLBuilder builder = new SQLBuilder();
        String sql = builder.createUpdate(table, values, where);
        String expected = "UPDATE [users] SET [name]='Tyler',[username]='tyler123',[password]='tyler123' WHERE [id]=3;";

        Assert.assertEquals(sql, expected);
    }

    @Test
    public void testDeleteStatement() {
        final String table = "users";
        final String where = "[id]=3";

        SQLBuilder builder = new SQLBuilder();
        String sql = builder.createDelete(table, where);
        String expected = "DELETE FROM [users] WHERE [id]=3;";

        Assert.assertEquals(sql, expected);
    }
}