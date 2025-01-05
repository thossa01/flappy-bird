import java.awt.Image;

public class pipeDimensions {
    int x;
    int y;
    int width;
    int height;
    Image image;
    boolean hasPassed = false;
    pipeDimensions(int x, int y, int width, int height, Image image){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.image = image;
    }

    public void addX(int x) {
        this.x += x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setPassed(boolean hasPassed) {
        this.hasPassed = hasPassed;
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

    public boolean getPassed() {
        return hasPassed;
    }
}
