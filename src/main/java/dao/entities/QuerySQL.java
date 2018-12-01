package dao.entities;

public enum QuerySQL {
    INSERT_USER("INSERT INTO users (name) VALUES (?)"),

    ;

    public String getQuery() {
        return query;
    }

    private String query;
    QuerySQL(String s) {
        query = s;
    }
}
