package dao.entities;

import javax.persistence.*;


public class Question {

    private int id;

    private String question;

    public Question( String question) {
        this.question = question;
    }

    public Question(int id, String question) {
        this.id = id;
        this.question = question;
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

    @Override
    public String toString() {
        return question;
    }
}
