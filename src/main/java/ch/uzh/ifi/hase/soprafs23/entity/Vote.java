package ch.uzh.ifi.hase.soprafs23.entity;

import javax.persistence.*;

@Entity
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Integer answer_id;
    private boolean is_upvote;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAnswer_id() {
        return answer_id;
    }

    public void setAnswer_id(Integer answer_id) {
        this.answer_id = answer_id;
    }

    public boolean isIs_upvote() {
        return is_upvote;
    }

    public void setIs_upvote(boolean is_upvote) {
        this.is_upvote = is_upvote;
    }
}
