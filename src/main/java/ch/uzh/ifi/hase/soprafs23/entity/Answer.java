package ch.uzh.ifi.hase.soprafs23.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private Integer question_id;

    private Integer who_answers;

    private String content;

    private Date change_time;

    private Integer comment_count;

    private Integer vote_count;


    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(Integer question_id) {
        this.question_id = question_id;
    }

    public Integer getWho_answers() {
        return who_answers;
    }

    public void setWho_answers(Integer who_answers) {
        this.who_answers = who_answers;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getChange_time() {
        return change_time;
    }

    public void setChange_time(Date change_time) {
        this.change_time = change_time;
    }

    public Integer getComment_count() {
        return comment_count;
    }

    public void setComment_count(Integer comment_count) {
        this.comment_count = comment_count;
    }

    public Integer getVote_count() {
        return vote_count;
    }

    public void setVote_count(Integer vote_count) {
        this.vote_count = vote_count;
    }
}
