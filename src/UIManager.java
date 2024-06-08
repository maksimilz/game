import javax.swing.*;
import java.awt.*;

public class UIManager {
    private JPanel backgroundPanel;
    private JLabel itemLabel;
    private JButton healingShelfButton;
    private JButton poisonShelfButton;
    private JButton herbShelfButton;
    private JLabel scoreLabel;
    private JLabel timeLabel;
    private JLabel streakLabel;
    private JLabel livesLabel;
    private JButton returnToMenuButton;

    public UIManager(SortingGame sortingGame) {
        initUIComponents();
    }

    private void initUIComponents() {
        backgroundPanel = new JPanel();
        itemLabel = new JLabel();
        healingShelfButton = new JButton("Полка с лечебными зельями");
        poisonShelfButton = new JButton("Полка с ядами");
        herbShelfButton = new JButton("Полка с травами");
        scoreLabel = new JLabel("Счет: 0");
        timeLabel = new JLabel("Время: 0");
        streakLabel = new JLabel("Серия: 0");
        livesLabel = new JLabel("Жизни: 3");
        returnToMenuButton = new JButton("Вернуться в меню");
    }

    // Дополнительные методы для управления компонентами UI, если необходимо
}
