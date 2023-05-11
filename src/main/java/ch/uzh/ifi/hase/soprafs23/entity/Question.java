package ch.uzh.ifi.hase.soprafs23.entity;

import java.util.Date;


import javax.persistence.*;


@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String title;
    private String description;
    private Integer who_asks;
    private Date change_time;
    private Integer answer_count;
    @Column(name = "tag")
    private String tag; 

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getWho_asks() {
        return who_asks;
    }

    public void setWho_asks(Integer who_asks) {
        this.who_asks = who_asks;
    }

    public java.util.Date getChange_time() {
        return change_time;
    }

    public void setChange_time(Date change_time) {
        this.change_time = change_time;
    }


    public Integer getAnswerCount() {
        return answer_count;
    }

    public void setAnswerCount(Integer answer_count) {
        this.answer_count = answer_count;
    }

}
