import javax.swing.*;
import java.awt.*;

public class SortingGame extends JFrame {
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private MainMenu mainMenu;
    private GamePanel gamePanel;

    public SortingGame() {
        setTitle("Sorting Game");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        mainMenu = new MainMenu(this);
        gamePanel = new GamePanel(this);

        cardPanel.add(mainMenu, "Главное меню");
        cardPanel.add(gamePanel, "GamePanel");

        add(cardPanel);

        cardLayout.show(cardPanel, "MainMenu");

        setVisible(true);
    }

    public void showGamePanel() {
        gamePanel.startGame();
        cardLayout.show(cardPanel, "GamePanel");
    }

    public void showMainMenu() {
        cardLayout.show(cardPanel, "Глав");
    }

    public static void main(String[] args) {
        new SortingGame();
    }
}
