package cs2410.assn4;

public class ImageModel {
    private String url;
    private String title;

    ImageModel(String url, String title) {
        this.url = url;
        this.title = title;
    }

    public void setURL(String inputURL) { url = inputURL; }
    public void setTitle(String inputTitle) { title = inputTitle; }

    public String getUrl() { return url; }
    public String getTitle() { return title; }
}
