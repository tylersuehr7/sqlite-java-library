package com.tylersuehr.sql;

/**
 * Utility to help construct SQL queries and commands.
 * @author Tyler Suehr
 */
final class SQLBuilder {
    // SELECT * FROM [table] WHERE [col1] = value ORDER BY [col] LIMIT 0;
    static String createQuery(String table, String selection, String order, String limit) {
        final StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ").append("[").append(table).append("]");
        sb.append(selection != null ? " WHERE " + selection : "");
        sb.append(order != null ? " ORDER BY " + order : "");
        sb.append(limit != null ? " LIMIT " + limit : "");
        sb.append(";");
        return sb.toString();
    }

    // SELECT ([col1],[col2],[col3]) FROM [table] WHERE [col] = value ORDER BY [col] LIMIT 0;
    static String createQuery(String table, String[] cols, String selection, String order, String limit) {
        final StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");

        if (cols != null) {
            sb.append("(");
            int i = 0;
            for (String c : cols) {
                sb.append((i > 0) ? "," : "");
                sb.append("[").append(c).append("]");
                i++;
            }
            sb.append(") FROM ");
        } else {
            sb.append("* FROM ");
        }
        sb.append("[").append(table).append("]");

        sb.append(selection != null ? " WHERE "  + selection : "");
        sb.append(order != null ? " ORDER BY " + order : "");
        sb.append(limit != null ? " LIMIT " + limit : "");
        sb.append(";");
        return sb.toString();
    }

    // INSERT INTO [table] ([col1],[col2],[col3],[col4]) VALUES ('test', 'test2', 123, 12.123);
    static String createInsert(String table, ContentValues values) {
        final StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ");
        sb.append("[").append(table).append("] (");

        int i = 0;
        for (String col : values.getKeys()) {
            sb.append((i > 0) ? "," : "");
            sb.append("[").append(col).append("]");
            i++;
        }
        sb.append(") VALUES (");

        i = 0;
        for (Object o : values.getData()) {
            sb.append((i > 0) ? "," : "");
            if (o instanceof String) {
                sb.append("'").append(o).append("'");
            } else {
                sb.append(o);
            }
            i++;
        }
        sb.append(");");
        return sb.toString();
    }

    // UPDATE [table] SET [col1] = 'test' WHERE [col2] = 3;
    static String createUpdate(String table, ContentValues values, String selection) {
        final StringBuilder sb = new StringBuilder(120);
        sb.append("UPDATE ");
        sb.append("[").append(table).append("]");
        sb.append(" SET ");

        int i = 0;
        for (String col : values.getKeys()) {
            sb.append((i > 0) ? "," : "");
            sb.append("[").append(col).append("]");
            sb.append("=");

            if (values.get(col) instanceof String) {
                sb.append("'").append(values.get(col)).append("'");
            } else {
                sb.append(values.get(col));
            }
            i++;
        }

        sb.append(selection != null ? " WHERE " + selection : "");
        sb.append(";");
        return sb.toString();
    }

    // DELETE FROM [table] WHERE [col1] = 23;
    static String createDelete(String table, String selection) {
        final StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM ");
        sb.append("[").append(table).append("]");
        sb.append(selection != null ? " WHERE " + selection : "");
        sb.append(";");
        return sb.toString();
    }
}