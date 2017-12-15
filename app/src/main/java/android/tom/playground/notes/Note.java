package android.tom.playground.notes;

/**
 * Created by tom.saju on 12/15/2017.
 */

public class Note {
    String id;
    String title;
    String content;
    String edittedDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getEdittedDate() {
        return edittedDate;
    }

    public void setEdittedDate(String edittedDate) {
        this.edittedDate = edittedDate;
    }
}
