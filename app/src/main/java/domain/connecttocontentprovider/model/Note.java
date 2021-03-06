package domain.connecttocontentprovider.model;


import java.io.Serializable;

public class Note implements Serializable {
    private long id;
    private String subject;
    private String content;

    public Note() {
    }

    public Note(String subject, String content) {
        this.subject = subject;
        this.content = content;
    }

    public Note(long id, String subject, String content) {
        this.id = id;
        this.subject = subject;
        this.content = content;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return subject;
    }

    public String toEntireString() {
        return "Subject= " + subject + "/nContent= " + content + "/n";
    }


}