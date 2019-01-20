/*
 * MIT License
 *
 * Copyright (c) Tyler Suehr 2019.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.tylersuehr.sql;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Tyler Suehr
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