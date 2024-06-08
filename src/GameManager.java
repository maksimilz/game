import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class GameManager {
    private SortingGame sortingGame;
    private int score;
    private int streak;
    private int lives;
    private int timeElapsed;
    private Timer timer;

    public GameManager(SortingGame sortingGame) {
        this.sortingGame = sortingGame;
        score = 0;
        streak = 0;
        lives = 3;
        timeElapsed = 0;
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
        // Генерация случайного предмета
    }

    private void checkItemPlacement() {
        // Проверка правильности размещения предмета
    }

    private void updateScore() {
        // Обновление счета
    }

    private void updateTime() {
        // Обновление времени
    }

    private void updateStreak() {
        // Обновление серии
    }

    private void updateLives() {
        // Обновление жизней
    }
}
