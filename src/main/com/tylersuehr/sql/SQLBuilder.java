package com.tylersuehr.sql;

/**
 * Copyright 2017 Tyler Suehr
 * Created by tyler on 8/23/2017.
 *
 * Utility to help build SQL queries and commands.
 */
final class SQLBuilder {
    // SELECT * FROM [table] WHERE [col1] = value ORDER BY [col] LIMIT 0;
    String createQuery(String table, String selection, String order, String limit) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ").append("[").append(table).append("]");

        String temp = (selection != null ? " WHERE " + selection : "");
        sb.append(temp);

        temp = (order != null ? " ORDER BY " + order : "");
        sb.append(temp);

        temp = (limit != null ? " LIMIT " + limit : "");
        sb.append(temp);

        sb.append(";");
        return sb.toString();
    }

    // SELECT ([col1],[col2],[col3]) FROM [table] WHERE [col] = value ORDER BY [col] LIMIT 0;
    String createQuery(String table, String[] cols, String selection, String order, String limit) {
        StringBuilder sb = new StringBuilder();
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

        String temp = (selection != null ? " WHERE " + selection : "");
        sb.append(temp);

        temp = (order != null ? " ORDER BY " + order : "");
        sb.append(temp);

        temp = (limit != null ? " LIMIT " + limit : "");
        sb.append(temp);

        sb.append(";");
        return sb.toString();
    }

    // INSERT INTO [table] ([col1],[col2],[col3],[col4]) VALUES ('test', 'test2', 123, 12.123);
    String createInsert(String table, ContentValues values) {
        StringBuilder sb = new StringBuilder();
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
    String createUpdate(String table, ContentValues values, String selection) {
        StringBuilder sb = new StringBuilder(120);
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

        if (selection != null) {
            sb.append(" WHERE ");
            sb.append(selection);
        }

        sb.append(";");
        return sb.toString();
    }

    // DELETE FROM [table] WHERE [col1] = 23;
    String createDelete(String table, String selection) {
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM ");
        sb.append("[").append(table).append("]");

        if (selection != null) {
            sb.append(" WHERE ");
            sb.append(selection);
        }

        sb.append(";");
        return sb.toString();
    }
}