package dao.utils;

public enum QuerySQL {
    INSERT_USER("INSERT INTO users (name) VALUES (?)"),
    SELECT_USER_ID("SELECT id FROM users WHERE name = ?"),
    INSERT_QUESTION("INSERT INTO question (question, user_id) VALUES (?, ?)"),
    SELECT_QUESTION("SELECT * FROM question WHERE question = ?"),
    SELECT_QUESTION_ID("SELECT id FROM question WHERE question = ?"),
    INSERT_ANSWER("INSERT INTO answer (answer, question_id, user_id) VALUES (?, ?, ?)"),
    SELECT_ANSWER_ID("SELECT id FROM answer WHERE answer = ?"),
    SELECT_ANSWER("SELECT * FROM answer WHERE user_id = ? AND question_id = ?"),
    SELECT_USER_ANSWERS("SELECT * FROM answer WHERE user_id = ?"),
    SELECT_QUESTION_ANSWER("SELECT * FROM answer WHERE question_id = ?"),
    DELETE_USER("DELETE FROM users WHERE id = ?"),
    DELETE_ANSWER("DELETE FROM answer WHERE id = ?"),
    DELETE_QUESTION("DELETE FROM question WHERE id = ?"),
    ;

    public String getQuery() {
        return query;
    }

    private String query;
    QuerySQL(String s) {
        query = s;
    }
}
