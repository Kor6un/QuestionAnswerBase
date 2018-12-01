package dao.utils;

public enum QuerySQL {
    INSERT_USER("INSERT INTO users (name) VALUES (?)"),
    SELECT_USER_ID("SELECT id FROM users WHERE name = ?"),
    INSERT_QUESTION("INSERT INTO question (question, user_id) VALUES (?, ?)"),
    SELECT_QUESTION("SELECT * FROM question WHERE question = ?"),
    SELECT_QUESTION_ID("SELECT id FROM question WHERE question = ?"),
    INSERT_ANSWER("INSERT INTO answer (answer) VALUES (?)"),
    SELECT_ANSWER_ID("SELECT id FROM answer WHERE answer = ?"),

    ;

    public String getQuery() {
        return query;
    }

    private String query;
    QuerySQL(String s) {
        query = s;
    }
}
