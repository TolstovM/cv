package ru.vsu.model;

import ru.vsu.persistence.annotation.Column;
import ru.vsu.persistence.annotation.Id;
import ru.vsu.persistence.annotation.Table;

@Table(name="item")
public class TODOItem {

    @Id
    @Column(name="id")
    private Long id;

    @Column(name="user_id")
    private Long userId;

    @Column(name="is_done")
    private boolean isDone;

    @Column(name="comment")
    private String comment;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean getIsDone() {
        return isDone;
    }

    public void setIsDone(boolean done) {
        isDone = done;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public TODOItem() {
    }

    public TODOItem(Long id) {
        this.id = id;
    }

    public TODOItem(Long userId, boolean isDone, String comment) {
        this.userId = userId;
        this.isDone = isDone;
        this.comment = comment;
    }
}
