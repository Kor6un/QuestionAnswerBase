package dao.entities.hibernate;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "question")
public class Questions {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "question")
    private String question;

    @ManyToMany(mappedBy = "questions", cascade = CascadeType.ALL)
    private List<Answers> answers;

    public Questions( String question) {
        this.question = question;
    }

    public Questions(int id, String question) {
        this.id = id;
        this.question = question;
    }

    public Questions() {
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

    public List<Answers> getAnswers() {
        return answers;
    }

    @Override
    public String toString() {
        return question;
    }
}
