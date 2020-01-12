package entities;

public class Image {

    private String mongoId;
    private String title;
    private String content;
    private String filter;

    public String toString(){

        return "id: " + mongoId + " title: " + title + " content: " + content + " filter : "+ filter;
    }

    public String getMongoId() {
        return mongoId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public void setMongoId(String mongoId) {
        this.mongoId = mongoId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }
}
