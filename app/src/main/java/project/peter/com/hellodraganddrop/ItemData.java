package project.peter.com.hellodraganddrop;

/**
 * Created by linweijie on 6/8/16.
 */
public class ItemData {
    private String title;
    private int imageId;

    public ItemData(String title, int imageUrl){

        this.title = title;
        this.imageId = imageUrl;

    }

    public String getTitle() {
        return title;
    }

    public int getimageId() {
        return imageId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setimageId(int imageId) {
        this.imageId = imageId;
    }
}