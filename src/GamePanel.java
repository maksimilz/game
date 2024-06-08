import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.Timer;

public class GamePanel extends JPanel {
    private SortingGame sortingGame;
    private JLabel itemLabel;
    private JButton healingShelfButton;
    private JButton poisonShelfButton;
    private JButton herbShelfButton;
    private JButton returnToMenuButton; // Добавленная кнопка
    private JLabel scoreLabel;
    private JLabel timeLabel;
    private JLabel streakLabel;
    private JLabel livesLabel;
    private int score;
    private int streak;
    private int lives;
    private int timeElapsed;

    private int itemX, itemY;
    private ImageIcon healingIcon;
    private ImageIcon poisonIcon;
    private ImageIcon herbIcon;
    private ImageIcon currentItemIcon;
    private String currentItem;
    private boolean dragging;
    private ImageIcon backgroundImage;
    private Timer timer;

    public GamePanel(SortingGame sortingGame) {
        this.sortingGame = sortingGame;
        setLayout(null);

        // Initialize variables
        score = 0;
        streak = 0;
        lives = 3;
        timeElapsed = 0;

        // Load images
        healingIcon = new ImageIcon(new ImageIcon("img/potion.png").getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
        poisonIcon = new ImageIcon(new ImageIcon("img/poison2.png").getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
        herbIcon = new ImageIcon(new ImageIcon("img/herbs.png").getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
        backgroundImage = new ImageIcon(new ImageIcon("img/background.png").getImage().getScaledInstance(600, 400, Image.SCALE_SMOOTH));

        // Create background panel with image
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage.getImage(), 0, 0, null);
            }
        };
        backgroundPanel.setLayout(null);
        backgroundPanel.setBounds(0, 0, 600, 400);
        add(backgroundPanel);

        // Create item label
        itemLabel = new JLabel();
        itemLabel.setSize(100, 100);
        itemLabel.setHorizontalAlignment(SwingConstants.CENTER);
        itemLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        backgroundPanel.add(itemLabel);

        // Create shelf buttons
        healingShelfButton = new JButton("Healing Shelf");
        healingShelfButton.setBounds(50, 300, 150, 50);
        backgroundPanel.add(healingShelfButton);

        poisonShelfButton = new JButton("Poison Shelf");
        poisonShelfButton.setBounds(250, 300, 150, 50);
        backgroundPanel.add(poisonShelfButton);

        herbShelfButton = new JButton("Herb Shelf");
        herbShelfButton.setBounds(450, 300, 150, 50);
        backgroundPanel.add(herbShelfButton);

        // Create top panel for score, time, streak, lives, and return to menu button
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(1, 5)); // Updated to 5 columns
        topPanel.setBounds(0, 0, 600, 50);
        backgroundPanel.add(topPanel);

        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setFont(new Font("Serif", Font.BOLD, 20));
        topPanel.add(scoreLabel);

        timeLabel = new JLabel("Time: 0");
        timeLabel.setFont(new Font("Serif", Font.BOLD, 20));
        topPanel.add(timeLabel);

        streakLabel = new JLabel("Streak: 0");
        streakLabel.setFont(new Font("Serif", Font.BOLD, 20));
        topPanel.add(streakLabel);

        livesLabel = new JLabel("Lives: 3");
        livesLabel.setFont(new Font("Serif", Font.BOLD, 20));
        topPanel.add(livesLabel);

        returnToMenuButton = new JButton("Return to Menu");
        returnToMenuButton.setFont(new Font("Serif", Font.BOLD, 20));
        topPanel.add(returnToMenuButton);

        // Add action listener for return to menu button
        returnToMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sortingGame.showMainMenu();
            }
        });

        // Add mouse listeners for dragging items
        backgroundPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (itemLabel.getBounds().contains(e.getPoint())) {
                    dragging = true;
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (dragging) {
                    checkItemPlacement();
                }
                dragging = false;
            }
        });

        backgroundPanel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (dragging) {
                    itemX = e.getX() - itemLabel.getWidth() / 2;
                    itemY = e.getY() - itemLabel.getHeight() / 2;
                    itemLabel.setLocation(itemX, itemY);
                }
            }
        });

        // Start timer to update time
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeElapsed++;
                updateTime();
            }
        });
    }

    public void startGame() {
        score = 0;
        streak = 0;
        lives = 3;
        timeElapsed = 0;
        updateScore();
        updateTime();
        updateStreak();
        updateLives();
        randomizeItem();
        timer.start();
    }

    private void randomizeItem() {
        Random rand = new Random();
        int itemType = rand.nextInt(3); // 0 - Healing, 1 - Poison, 2 - Herb
        switch (itemType) {
            case 0:
                currentItem = "Healing Potion";
                currentItemIcon = healingIcon;
                break;
            case 1:
                currentItem = "Poison";
                currentItemIcon = poisonIcon;
                break;
            case 2:
                currentItem = "Herb";
                currentItemIcon = herbIcon;
                break;
        }
        itemLabel.setIcon(currentItemIcon);
        itemX = getWidth() / 2 - itemLabel.getWidth() / 2;
        itemY = getHeight() / 2 - itemLabel.getHeight() / 2;
        itemLabel.setLocation(itemX, itemY);
    }

    private void checkItemPlacement() {
        Rectangle healingShelfBounds = healingShelfButton.getBounds();
        Rectangle poisonShelfBounds = poisonShelfButton.getBounds();
        Rectangle herbShelfBounds = herbShelfButton.getBounds();
        Rectangle itemBounds = itemLabel.getBounds();

        if (currentItem.equals("Healing Potion") && healingShelfBounds.intersects(itemBounds)) {
            JOptionPane.showMessageDialog(this, "Correctly placed on Healing Shelf!");
            score++;
            streak++;
            updateScore();
            updateStreak();
            randomizeItem();
        } else if (currentItem.equals("Poison") && poisonShelfBounds.intersects(itemBounds)) {
            JOptionPane.showMessageDialog(this, "Correctly placed on Poison Shelf!");
            score++;
            streak++;
            updateScore();
            updateStreak();
            randomizeItem();
        } else if (currentItem.equals("Herb") && herbShelfBounds.intersects(itemBounds)) {
            JOptionPane.showMessageDialog(this, "Correctly placed on Herb Shelf!");
            score++;
            streak++;
            updateScore();
            updateStreak();
            randomizeItem();
        } else {
            JOptionPane.showMessageDialog(this, "Incorrect placement. Try again.");
            streak = 0;
            lives--;
            updateStreak();
            updateLives();
            if (lives <= 0) {
                timer.stop();
                JOptionPane.showMessageDialog(this, "Game Over! Your final score is: " + score);
                sortingGame.showMainMenu();
            }
            itemX = getWidth() / 2 - itemLabel.getWidth() / 2;
            itemY = getHeight() / 2 - itemLabel.getHeight() / 2;
            itemLabel.setLocation(itemX, itemY);
        }
    }

    private void updateScore() {
        scoreLabel.setText("Score: " + score);
    }

    private void updateTime() {
        timeLabel.setText("Time: " + timeElapsed);
    }

    private void updateStreak() {
        streakLabel.setText("Streak: " + streak);
    }

    private void updateLives() {
        livesLabel.setText("Lives: " + lives);
    }
}
