package dao.entities.hibernate;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "answer")
public class Answers {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "answer")
    private String answer;

    @ManyToMany
    @JoinColumn(name = "question_id")
    private List<Questions> questions;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    public Answers(String answer) {
        this.answer = answer;
    }

  /*  public Answers(int id, String answer, int questionID, int userID) {
        this(answer, questionID, userID);
        this.id = id;
    }*/

    public Answers() {}

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

    public List<Questions> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Questions> questions) {
        this.questions = questions;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return answer;
    }
}
