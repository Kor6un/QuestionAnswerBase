package dao.entities;

public class Question {
    private int id;
    private int userID;
    private String question;

    public Question( String question, int userID) {
        this.question = question;
        this.userID = userID;
    }

    public Question(int id, String question, int userID) {
        this.id = id;
        this.question = question;
        this.userID = userID;
    }

    public Question() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    @Override
    public String toString() {
        return question;
    }
}
