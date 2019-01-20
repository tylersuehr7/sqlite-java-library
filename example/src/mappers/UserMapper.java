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

package mappers;
import com.tylersuehr.sql.ContentValues;
import java.sql.ResultSet;
import java.sql.SQLException;
import models.User;
import static repositories.DatabaseContract.Users.*;

/**
 * @author Tyler Suehr
 */
public class UserMapper implements IEntityMapper<User> {
    @Override
    public User map(ResultSet r) {
        try {
            User user = new User();
            user.setId(r.getString(COL_ID));
            user.setFirstName(r.getString(COL_FIRST_NAME));
            user.setLastName(r.getString(COL_LAST_NAME));
            user.setUsername(r.getString(COL_USERNAME));
            return user;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public ContentValues map(User model) {
        ContentValues values = new ContentValues();
        values.put(COL_ID, model.getId());
        values.put(COL_FIRST_NAME, model.getFirstName());
        values.put(COL_LAST_NAME, model.getLastName());
        values.put(COL_USERNAME, model.getUsername());
        return values;
    }
}