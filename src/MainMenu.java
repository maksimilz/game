import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JPanel {
    private SortingGame sortingGame;

    public MainMenu(SortingGame sortingGame) {
        this.sortingGame = sortingGame;
        setLayout(new GridBagLayout());

        JLabel titleLabel = new JLabel("Sorting Game");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 36));

        JButton startButton = new JButton("Start Game");
        startButton.setFont(new Font("Serif", Font.BOLD, 24));
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sortingGame.showGamePanel();
            }
        });

        JButton exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Serif", Font.BOLD, 24));
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(titleLabel, gbc);

        gbc.gridy = 1;
        add(startButton, gbc);

        gbc.gridy = 2;
        add(exitButton, gbc);
    }
}
