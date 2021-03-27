package com.gmail.evgenymoshchin.repository.model;

import java.util.Objects;

public class UserReview {
    private Long id;
    private String topic;
    private String review;
    private String date;
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "UserReview{" +
                "id=" + id +
                ", topic='" + topic + '\'' +
                ", review='" + review + '\'' +
                ", date='" + date + '\'' +
                ", user=" + user +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserReview that = (UserReview) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(topic, that.topic) &&
                Objects.equals(review, that.review) &&
                Objects.equals(date, that.date) &&
                Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, topic, review, date, user);
    }
}
