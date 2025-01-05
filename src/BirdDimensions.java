import java.awt.Image;

public class BirdDimensions {
    int x;
    int y;
    int width;
    int height;
    Image image;
    BirdDimensions(int x, int y, int width, int height, Image image){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.image = image;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Image getImage() {
        return image;
    }

    public void addY(int y) {
        this.y += y;
        this.y = Math.max(this.y, 0);
    }
}
