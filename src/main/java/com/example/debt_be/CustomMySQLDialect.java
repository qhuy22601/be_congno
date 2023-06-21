package com.example.debt_be;

import org.hibernate.dialect.MySQLDialect;

public class CustomMySQLDialect extends MySQLDialect {
    @Override
    public long getDefaultLobLength() {
        // Thay đổi kích thước tối đa của cột LOB tại đây
        return 16777215; // Ví dụ: 16 MB
    }
}