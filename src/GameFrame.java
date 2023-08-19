import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.sql.Time;
import java.util.TimerTask;
import java.util.Timer;

public class GameFrame extends JPanel implements ActionListener, MouseMotionListener {
    int compPaddleSpeed = 5;
    int humanScore = 0;
    int compScore = 0;
    final int PADDLE_WIDTH = 10;
    final int PADDLE_HEIGHT = 80;
    int SPEED = 9;
    final int WIDTH = 1000;
    final int HEIGHT = 700;
    final int UNIT_SIZE = 25;
    int humanPaddleX = 10;
    int humanPaddleY = HEIGHT/2 - (PADDLE_HEIGHT / 2);
    int computerPaddleY = HEIGHT/2 - (PADDLE_HEIGHT / 2);
    Ball  ball = new Ball();
    JPanel humanPanelPaddle;
    JPanel computerPanelPaddle;
    JLabel compScoreL;
    JLabel humanScoreL;
    double randomDirection = 0.3;
    double previouseRandomDirection = 0.1;
    Timer timer;
    Timer start;
    TimerTask timerTask;
    String compScoreStr;
    String humanScoreStr;
    JButton restart;
    GameFrame(){

        humanPanelPaddle = new JPanel();
        computerPanelPaddle = new JPanel();
        this.setPreferredSize(new Dimension(WIDTH,HEIGHT));
        this.setFocusable(true);
        this.setLayout(null);
        this.setBackground(new Color(255, 114, 114));

        humanPanelPaddle.setLayout(null);
        humanPanelPaddle.setBounds(10, humanPaddleY, PADDLE_WIDTH, PADDLE_HEIGHT);
        humanPanelPaddle.setBackground(new Color(0, 0, 0));
        humanPanelPaddle.setVisible(true);

        computerPanelPaddle.setLayout(null);
        computerPanelPaddle.setBounds(WIDTH-PADDLE_WIDTH*2, computerPaddleY, PADDLE_WIDTH, PADDLE_HEIGHT);
        computerPanelPaddle.setBackground(new Color(0, 0, 0));
        computerPanelPaddle.setVisible(true);


        this.add(humanPanelPaddle);
        this.add(computerPanelPaddle);
        this.addMouseMotionListener(this);
        startGame();
    }
    public  void startGame(){
        if(restart != null){
            this.remove(restart);
        }
        humanScoreL = new JLabel();
        humanScoreL.setBounds((WIDTH / 2) - 50, 10 , 200, 50);
        humanScoreStr = Integer.toString(humanScore);
        compScoreStr = Integer.toString(compScore);

        humanScoreL.setText(humanScoreStr);

        humanScoreL.setFont(new Font("MV Boli", Font.PLAIN, 40));
        humanScoreL.setForeground(Color.red);

        compScoreL = new JLabel();
        compScoreL.setBounds((WIDTH / 2) + 20, 10 , 200, 50);
        compScoreL.setText(compScoreStr);
        compScoreL.setFont(new Font("MV Boli", Font.PLAIN, 40));
        compScoreL.setForeground(Color.red);

        this.add(humanScoreL);
        this.add(compScoreL);


        ball.positionX = WIDTH/2;
        ball.positionY = HEIGHT/2;
        while((ball.directionX <= 0.3 || ball.directionX >= 0.7) && (ball.directionY <=0.3 || ball.directionY >= 0.7)){

            randomDirection = (Math.random() *1) * 2 * (Math.PI);
            previouseRandomDirection = randomDirection;
            ball.directionX = (Math.cos(randomDirection));
            ball.directionY = (Math.sin(randomDirection));

        }
        while(randomDirection == previouseRandomDirection && (randomDirection <= 0.3 || randomDirection >= 6.20)){
            randomDirection = (Math.random() *1) * 2 * (Math.PI);
            ball.directionX = (Math.cos(randomDirection));
            ball.directionY = (Math.sin(randomDirection));

        }

        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                move();
            }

        };
        timer.scheduleAtFixedRate(timerTask, 0, 17);

        if(compScore == 10){

            timer.cancel();
            JLabel winComp = new JLabel();
            winComp.setBounds(WIDTH/2 - 250, HEIGHT/2 - 50, 600,50);
            winComp.setText("Computer wins!");
            winComp.setFont(new Font("MV Boli", Font.BOLD, 60));
            restart = new JButton();
            restart.setBounds(WIDTH/2 - 100, HEIGHT/2 + 100, 200,50);
            restart.setText("RESTART");
            restart.setFont(new Font("MV Boli", Font.BOLD, 30));
            restart.setBackground(Color.white);


            this.add(restart);

            this.add(winComp);
            restart.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    humanScore = 0;
                    compScore = 0;
                    SPEED = 10;
                    humanScoreStr = "0";
                    compScoreStr = "0";
                    winComp.setText(null);
                    compScoreL.setText(null);
                    humanScoreL.setText(null);

                    startGame();
                }
            });
        }
        if(humanScore == 10){
            timer.cancel();
            JLabel playerWin = new JLabel();
            playerWin.setBounds(WIDTH/2 - 250, HEIGHT/2 - 50, 600,60);
            playerWin.setText("Player wins!");
            playerWin.setFont(new Font("MV Boli", Font.BOLD, 60));
            restart = new JButton();
            restart.setBounds(WIDTH/2 - 100, HEIGHT/2 + 100, 200,60);
            restart.setText("RESTART");
            restart.setFont(new Font("MV Boli", Font.BOLD, 30));
            restart.setBackground(Color.white);


            this.add(restart);

            this.add(playerWin);
            restart.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    humanScore = 0;
                    compScore = 0;
                    SPEED = 10;
                    humanScoreStr = "0";
                    compScoreStr = "0";
                    playerWin.setText(null);
                    humanScoreL.setText(null);
                    compScoreL.setText(null);

                    startGame();
                }
            });

        }

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
        g.setColor(Color.black);
        g.drawLine(WIDTH/2,0, WIDTH/2,HEIGHT);

        g.setColor(Color.white);
        g.fillOval(ball.positionX - (int)(UNIT_SIZE/2), ball.positionY-(int)(UNIT_SIZE/2), UNIT_SIZE, UNIT_SIZE);
        moveComputerPaddle();
    }
    public void moveComputerPaddle(){
        computerPanelPaddle.setLocation(WIDTH-PADDLE_WIDTH*2, ball.positionY + compPaddleSpeed );
    }

    public void move(){

        ball.positionX += ball.directionX * SPEED;
        ball.positionY += ball.directionY * SPEED;
        if(ball.positionY >= HEIGHT || ball.positionY <= 0){
            ball.directionY *= -1;
        }
        if(((ball.positionX - (UNIT_SIZE/2) <= humanPanelPaddle.getX() + 1) && (((ball.positionY >= humanPanelPaddle.getY()) && (ball.positionY <= humanPanelPaddle.getY() + PADDLE_HEIGHT))) && ((ball.positionY + UNIT_SIZE/2 >= humanPanelPaddle.getY()) || (ball.positionY - UNIT_SIZE/2 <= humanPanelPaddle.getY() + PADDLE_HEIGHT)))){
            ball.directionX *= -1;
        }
//        if(){
//            ball.directionX *= -1;
//        }
        if(((ball.positionX + (UNIT_SIZE/2) >= computerPanelPaddle.getX() - 1) && ((ball.positionY >= computerPanelPaddle.getY()) && (ball.positionY <= computerPanelPaddle.getY() + PADDLE_HEIGHT))) && (((ball.positionY + UNIT_SIZE/2) >= computerPanelPaddle.getY()) || ((ball.positionY - UNIT_SIZE/2) <= computerPanelPaddle.getY() + PADDLE_HEIGHT))) {
            ball.directionX *= -1;
        }
//        if(){
//            ball.directionX *= -1;
//        }
        if(ball.positionX <= 10 || ball.positionX >= WIDTH - 10){
            timer.cancel();
            SPEED += 1;
            if(ball.positionX <= 10){
                compScoreL.setText(null);
                humanScoreL.setText(null);
                compScore += 1;




            }else if(ball.positionX >= WIDTH - 10){
                humanScoreL.setText(null);
                compScoreL.setText(null);
                humanScore += 1;

            }
            startGame();
        }
        repaint();

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

        if(e.getY() > humanPaddleY){
            if(start != null) start.cancel();
            start = new Timer();
            TimerTask begin = new TimerTask() {
                int lastPoint;
                @Override
                public void run() {

                        if(e.getY() != lastPoint) {
                            movePaddle("UP");
                            lastPoint = e.getY();
                        }

                }


            };
            start.scheduleAtFixedRate(begin, 0 ,5);
        }
        if(e.getY() < humanPaddleY){
            if(start != null) start.cancel();
            start = new Timer();
            TimerTask begin = new TimerTask() {
                int lastPoint;
                @Override
                public void run() {

                    if(e.getY() != lastPoint){
                        movePaddle("DOWN");
                        lastPoint = e.getY();
                    }

                }
            };
            start.scheduleAtFixedRate(begin, 0 ,5);
        }
    }
    public void movePaddle(String direction){
            switch (direction){
                case "UP":
                    humanPaddleY += 5;
                    humanPanelPaddle.setLocation(humanPaddleX, humanPaddleY);
                    break;
                case "DOWN":
                    humanPaddleY -= 5;
                    humanPanelPaddle.setLocation(humanPaddleX, humanPaddleY);
                    break;
            }
    }

}
