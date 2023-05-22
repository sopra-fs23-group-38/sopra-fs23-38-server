package ch.uzh.ifi.hase.soprafs23.entity;


import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String content;
    private Integer who_comments;
    private Integer answer_ID;
    private Date change_time;

    private Integer parentCommentId;

    public Integer getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public Integer getWho_comments() {
        return who_comments;
    }

    public Integer getAnswer_ID() {
        return answer_ID;
    }


    public Date getChange_time() {
        return change_time;
    }

    // Setter methods
    public void setId(Integer id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setWho_comments(Integer who_comments) {
        this.who_comments = who_comments;
    }

    public void setAnswer_ID(Integer answer_ID) {
        this.answer_ID = answer_ID;
    }


    public void setChange_time(Date change_time) {
        this.change_time = change_time;
    }

    public Integer getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(Integer parentCommentId) {
        this.parentCommentId = parentCommentId;
    }


}