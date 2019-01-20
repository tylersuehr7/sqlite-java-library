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