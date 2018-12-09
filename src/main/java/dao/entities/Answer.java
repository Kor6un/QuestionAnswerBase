package dao.entities;

import javax.persistence.*;

public class Answer {

    private int id;

    private String answer;

    private int questionID;


    private int userID;

    public Answer(String answer, int questionID, int userID) {
        this.answer = answer;
        this.questionID = questionID;
        this.userID = userID;
    }

    public Answer(int id, String answer, int questionID, int userID) {
        this(answer, questionID, userID);
        this.id = id;
    }

    public Answer() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getQuestionID() {
        return questionID;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    @Override
    public String toString() {
        return answer;
    }
}
