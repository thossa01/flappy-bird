import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class FlappyBird extends JPanel implements ActionListener, KeyListener{
    ArrayList<pipeDimensions> pipes;
    BirdDimensions bird;
    boolean gameOver = false;
    double score = 0;
    int boardSizeWidth = 360;
    int boardSizeHeight = 640;
    int birdX = boardSizeWidth/8;
    int birdY = boardSizeHeight/2;
    int birdWidth = 34;
    int birdHeight = 24;
    int speedY = 0;
    int speedX = -4;
    int gravity = 1;

    int pipeX = boardSizeWidth;
    int pipeY = 0;
    int pipeWidth = 64;
    int pipeHeight = 512;
    Image bgImage = new ImageIcon(getClass().getResource("./assets/flappybirdbg.png")).getImage();
    Image birdImage = new ImageIcon(getClass().getResource("./assets/flappybird.png")).getImage();
    Image topPipeImage = new ImageIcon(getClass().getResource("./assets/toppipe.png")).getImage();
    Image bottomPipeImage = new ImageIcon(getClass().getResource("./assets/bottompipe.png")).getImage();
    Random random = new Random();
    Timer gameLoop;
    Timer pipeLoop;

    FlappyBird(){
        setFocusable(true);
        addKeyListener(this);             
        setPreferredSize(new Dimension(boardSizeWidth, boardSizeHeight));
        bird = new BirdDimensions(birdX, birdY, birdWidth, birdHeight, birdImage);
        pipes = new ArrayList<pipeDimensions>();
        gameLoop = new Timer(1000/60, this);
        pipeLoop = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                placePipes();
            }
        });
        pipeLoop.start();
        gameLoop.start();
        
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        
        draw(g, bgImage, 0, 0, boardSizeWidth, boardSizeHeight);
        draw(g, bird.getImage(), bird.getX(), bird.getY(), bird.getWidth(), bird.getHeight());

        for(pipeDimensions pipe : pipes){
            draw(g, pipe.getImage(), pipe.getX(), pipe.getY(), pipe.getWidth(), pipe.getHeight());
        }

        drawText(g);
    }

    private void drawText(Graphics g){
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        if (gameOver){
            g.drawString("Game Over: " + String.valueOf((int)score), 10, 35);
            g.drawString("Press Space to Restart", 10, 60);
        }
        else{
            g.drawString("Score: " + String.valueOf((int)score), 10, 35);
        }
    }

    private void draw(Graphics g, Image image, int x, int y, int width, int height){
        
        g.drawImage(image, x, y, width, height,null);
    }

    public void jump(){
        speedY += gravity;
        bird.addY(speedY);

        for(pipeDimensions pipe : pipes){
            pipe.addX(speedX);

            if (!pipe.hasPassed && bird.getX() > pipe.getX() + pipe.getWidth()) {
                pipe.setPassed(true);
                score += 0.5;

            }
            if(checkCollision(bird, pipe)){
                gameOver = true;

            }
        }

        if(bird.getY() >= boardSizeHeight){
            gameOver = true;
            gameLoop.stop();
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        jump(); 
        repaint();
        if (gameOver){
            pipeLoop.stop();
            gameLoop.stop();
        }
    }

    public boolean checkCollision(BirdDimensions bird, pipeDimensions pipe){
        return bird.getX() < pipe.getX() + pipe.getWidth() && bird.getX() + bird.getWidth() > pipe.getX() && bird.getY() < pipe.getY() + pipe.getHeight() && bird.getY() + bird.getHeight() > pipe.getY();
    }

    public void placePipes(){
        int randomHeight = (int)(pipeY - pipeHeight/4 - Math.random()*(pipeHeight/2));
        int opening = boardSizeHeight/4;
        pipeDimensions topPipe = new pipeDimensions(pipeX, pipeY, pipeWidth, pipeHeight, topPipeImage);
        topPipe.setY(randomHeight);

        pipeDimensions bottomPipe = new pipeDimensions(pipeX, pipeY, pipeWidth, pipeHeight, bottomPipeImage);
        bottomPipe.setY(randomHeight + pipeHeight + opening);
        pipes.add(topPipe);
        pipes.add(bottomPipe);
    }
    @Override
    public void keyTyped(KeyEvent e) {
    }
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_SPACE){
            speedY = -9;
            if (gameOver){
                pipes.clear();
                bird = new BirdDimensions(birdX, birdY, birdWidth, birdHeight, birdImage);
                score = 0;
                gameOver = false;
                gameLoop.start();
                pipeLoop.start();
            }
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {

    }
}
