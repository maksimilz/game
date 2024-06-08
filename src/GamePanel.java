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

        // Инициализация переменных
        score = 0;
        streak = 0;
        lives = 3;
        timeElapsed = 0;

        // Загрузка изображений
        healingIcon = new ImageIcon(new ImageIcon("img/potion.png").getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
        poisonIcon = new ImageIcon(new ImageIcon("img/poison2.png").getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
        herbIcon = new ImageIcon(new ImageIcon("img/herbs.png").getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
        backgroundImage = new ImageIcon(new ImageIcon("img/background.png").getImage().getScaledInstance(625, 450, Image.SCALE_SMOOTH));

        // Создание фона с изображением
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage.getImage(), 0, 0, null);
            }
        };
        backgroundPanel.setLayout(null);
        backgroundPanel.setBounds(0, 0, 625, 450);
        add(backgroundPanel);

        // Создание метки для предмета
        itemLabel = new JLabel();
        itemLabel.setSize(100, 100);
        itemLabel.setHorizontalAlignment(SwingConstants.CENTER);
        itemLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        backgroundPanel.add(itemLabel);

        // Создание кнопок для полок
        healingShelfButton = new JButton("Полка с лечебными зельями");
        healingShelfButton.setBounds(50, 300, 150, 50);
        backgroundPanel.add(healingShelfButton);

        poisonShelfButton = new JButton("Полка с ядами");
        poisonShelfButton.setBounds(250, 300, 150, 50);
        backgroundPanel.add(poisonShelfButton);

        herbShelfButton = new JButton("Полка с травами");
        herbShelfButton.setBounds(450, 300, 150, 50);
        backgroundPanel.add(herbShelfButton);

        // Создание верхней панели для счета, времени, серии, жизней и кнопки возврата в меню
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(1, 5)); // Обновлено до 5 столбцов
        topPanel.setBounds(0, 0, 625, 50);
        backgroundPanel.add(topPanel);

        scoreLabel = new JLabel("Счет: 0");
        scoreLabel.setFont(new Font("Serif", Font.BOLD, 20));
        topPanel.add(scoreLabel);

        timeLabel = new JLabel("Время: 0");
        timeLabel.setFont(new Font("Serif", Font.BOLD, 20));
        topPanel.add(timeLabel);

        streakLabel = new JLabel("Серия: 0");
        streakLabel.setFont(new Font("Serif", Font.BOLD, 20));
        topPanel.add(streakLabel);

        livesLabel = new JLabel("Жизни: 3");
        livesLabel.setFont(new Font("Serif", Font.BOLD, 20));
        topPanel.add(livesLabel);

        returnToMenuButton = new JButton("Вернуться в меню");
        returnToMenuButton.setFont(new Font("Serif", Font.BOLD, 20));
        topPanel.add(returnToMenuButton);

        // Добавление слушателя действий для кнопки возврата в меню
        returnToMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sortingGame.showMainMenu();
            }
        });

        // Добавление слушателей мыши для перетаскивания предметов
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

        // Запуск таймера для обновления времени
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
        int itemType = rand.nextInt(3); // 0 - Лечебное, 1 - Яд, 2 - Трава
        switch (itemType) {
            case 0:
                currentItem = "Лечебное зелье";
                currentItemIcon = healingIcon;
                break;
            case 1:
                currentItem = "Яд";
                currentItemIcon = poisonIcon;
                break;
            case 2:
                currentItem = "Трава";
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

        if (currentItem.equals("Лечебное зелье") && healingShelfBounds.intersects(itemBounds)) {
            JOptionPane.showMessageDialog(this, "Вы верно разместили предмет на полке с лечебными зельями!");
            score++;
            streak++;
            updateScore();
            updateStreak();
            randomizeItem();
        } else if (currentItem.equals("Яд") && poisonShelfBounds.intersects(itemBounds)) {
            JOptionPane.showMessageDialog(this, "Вы верно разместили предмет на полке с ядами!");
            score++;
            streak++;
            updateScore();
            updateStreak();
            randomizeItem();
        } else if (currentItem.equals("Трава") && herbShelfBounds.intersects(itemBounds)) {
            JOptionPane.showMessageDialog(this, "Вы верно разместили предмет на полке с травой!");
            score++;
            streak++;
            updateScore();
            updateStreak();
            randomizeItem();
        } else {
            JOptionPane.showMessageDialog(this, "Неверное размещение. Попробуйте еще раз.");
            streak = 0;
            lives--;
            updateStreak();
            updateLives();
            if (lives <= 0) {
                timer.stop();
                JOptionPane.showMessageDialog(this, "Игра окончена! Ваш итоговый счет: " + score);
                sortingGame.showMainMenu();
            }
            itemX = getWidth() / 2 - itemLabel.getWidth() / 2;
            itemY = getHeight() / 2 - itemLabel.getHeight() / 2;
            itemLabel.setLocation(itemX, itemY);
        }
    }

    private void updateScore() {
        scoreLabel.setText("Счет: " + score);
    }

    private void updateTime() {
        timeLabel.setText("Время: " + timeElapsed);
    }

    private void updateStreak() {
        streakLabel.setText("Серия: " + streak);
    }

    private void updateLives() {
        livesLabel.setText("Жизни: " + lives);
    }
}
