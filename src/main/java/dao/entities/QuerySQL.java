package dao.entities;

public enum QuerySQL {
    INSERT_USER("INSERT INTO users (name) VALUES (?)"),
    SELECT_USER_ID("SELECT id FROM users WHERE name = ?")

    ;

    public String getQuery() {
        return query;
    }

    private String query;
    QuerySQL(String s) {
        query = s;
    }
}
