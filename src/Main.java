import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JButton;

import javax.swing.BorderFactory;
import java.awt.GridBagLayout;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JComboBox;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JSlider;
import java.awt.Dimension;

public class Main {

    public static void main(String[] args) {
        JSlider speedSlider = new JSlider(1, 100, 30);
        JLabel speedLabel = new JLabel("Speed");
        JFrame frame = new JFrame();

        frame.setTitle(
                "Pathfinding Visualizer | BFS • DFS • A*"
        );

        frame.pack();

        frame.setLocationRelativeTo(null);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        MazePanel panel = new MazePanel();

        JButton generateButton = new JButton("Generate Maze");
        JLabel algorithmLabel = new JLabel("Algorithm: ");

        JLabel visitedLabel = new JLabel("Visited: ");

        JLabel pathLabel = new JLabel("Path Length: ");

        JLabel timeLabel = new JLabel("Time: ");
        JLabel statusLabel = new JLabel("Status: Ready");
        JLabel mazeSizeLabel =
                new JLabel("Maze Size: 11 x 11");
        statusLabel.setForeground(
                new Color(52, 152, 219)
        );
        algorithmLabel.setForeground(Color.WHITE);

        visitedLabel.setForeground(Color.WHITE);

        pathLabel.setForeground(Color.WHITE);

        timeLabel.setForeground(Color.WHITE);
        statusLabel.setForeground(Color.WHITE);
        mazeSizeLabel.setForeground(Color.WHITE);

        JButton solveButton = new JButton("Solve Maze");
        JButton startButton = new JButton("Place Start");

        JButton endButton = new JButton("Place End");
        generateButton.setFocusPainted(false);
        solveButton.setFocusPainted(false);
        startButton.setFocusPainted(false);
        endButton.setFocusPainted(false);

        Font btnFont =
                new Font("Segoe UI", Font.BOLD, 13);

        generateButton.setFont(btnFont);
        solveButton.setFont(btnFont);
        startButton.setFont(btnFont);
        endButton.setFont(btnFont);
        String[] algorithms = {"BFS", "DFS", "A*"};

        JComboBox<String> algorithmBox = new JComboBox<>(algorithms);
        String[] sizes = {"11", "21"};

        JComboBox<String> sizeBox = new JComboBox<>(sizes);
        sizeBox.addActionListener(e -> {

            int size = Integer.parseInt(
                    (String) sizeBox.getSelectedItem()
            );

            panel.updateMazeSize(size);



        });

        generateButton.setFocusPainted(false);

        solveButton.setFocusPainted(false);
        startButton.addActionListener(e -> {

            panel.setPlaceStartMode(true);

            panel.setPlaceEndMode(false);
        });
        endButton.addActionListener(e -> {

            panel.setPlaceEndMode(true);

            panel.setPlaceStartMode(false);
        });

        generateButton.addActionListener(e -> {

            int size = Integer.parseInt(
                    (String) sizeBox.getSelectedItem()
            );

            mazeSizeLabel.setText(
                    "Maze Size: " +
                            size +
                            " x " +
                            size
            );
            panel.updateMazeSize(size);
            panel.generateMaze();

            panel.repaint();
        });
        solveButton.addActionListener(e -> {
            statusLabel.setForeground(
                    new Color(230,126,34)
            );

            statusLabel.setText(
                    "Status: Solving..."
            );

            int size = Integer.parseInt(
                    (String) sizeBox.getSelectedItem()
            );

            panel.updateMazeSize(size);
            panel.setAnimationDelay(
                    speedSlider.getValue()
            );

            String selected = (String) algorithmBox.getSelectedItem();
            algorithmLabel.setText(
                    "Algorithm: " + selected
            );

            new Thread(() -> {
                long startTime = System.nanoTime();

                if (selected.equals("BFS")) {

                    panel.solveMaze();
                }
                else if (selected.equals("DFS")) {

                    panel.solveDFS();
                }
                else if (selected.equals("A*")) {

                    panel.solveAStar();
                }
                long endTime = System.nanoTime();

                long duration =
                        (endTime - startTime) / 1_000_000;
                algorithmLabel.setText(
                        "Algorithm: " + selected
                );

                visitedLabel.setText(
                        "Visited: " + panel.getVisitedCount()
                );

                pathLabel.setText(
                        "Path Length: " + panel.getPathLength()
                );

                timeLabel.setText(
                        "Time: " + duration + " ms"
                );

                statusLabel.setForeground(
                        new Color(46,204,113)
                );

                statusLabel.setText(
                        "Status: Completed"
                );
            }).start();




        });

        frame.setLayout(new BorderLayout());
        JPanel buttonPanel = new JPanel();

        buttonPanel.add(generateButton);
        buttonPanel.add(startButton);

        buttonPanel.add(endButton);
        buttonPanel.add(algorithmBox);
        buttonPanel.add(sizeBox);
        buttonPanel.add(solveButton);

        buttonPanel.add(speedLabel);

        buttonPanel.add(speedSlider);


        frame.add(buttonPanel, BorderLayout.NORTH);






        JPanel centerPanel = new JPanel(
                new GridBagLayout()
        );

        centerPanel.setBackground(
                new Color(24,24,24)
        );

        centerPanel.add(panel);

        frame.add(centerPanel, BorderLayout.CENTER);
        JPanel statsPanel = new JPanel();
        JLabel statsTitle =
                new JLabel("ALGORITHM STATS");

        statsTitle.setForeground(Color.WHITE);

        statsTitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        18
                )
        );

        statsPanel.add(statsTitle);
        statsPanel.add(Box.createVerticalStrut(20));
        statsPanel.add(algorithmLabel);
        statsPanel.add(Box.createVerticalStrut(10));

        statsPanel.add(visitedLabel);
        statsPanel.add(Box.createVerticalStrut(10));

        statsPanel.add(pathLabel);
        statsPanel.add(Box.createVerticalStrut(10));

        statsPanel.add(timeLabel);
        statsPanel.setLayout(
                new BoxLayout(
                        statsPanel,
                        BoxLayout.Y_AXIS
                )
        );

        statsPanel.setPreferredSize(
                new Dimension(300,700)
        );

        statsPanel.setBackground(
                new Color(35,35,35)
        );
        statsPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        20,
                        20,
                        20,
                        20
                )
        );
        Font statsFont =
                new Font("Segoe UI",
                        Font.BOLD,
                        16);

        algorithmLabel.setFont(statsFont);
        visitedLabel.setFont(statsFont);
        pathLabel.setFont(statsFont);
        timeLabel.setFont(statsFont);

        statsPanel.add(statusLabel);
        statsPanel.add(Box.createVerticalStrut(10));

        statsPanel.add(mazeSizeLabel);

        statsPanel.add(Box.createVerticalStrut(10));
        statsPanel.add(Box.createVerticalStrut(10));

        statsPanel.add(algorithmLabel);
        statsPanel.add(Box.createVerticalStrut(10));

        statsPanel.add(visitedLabel);
        statsPanel.add(Box.createVerticalStrut(10));

        statsPanel.add(pathLabel);
        statsPanel.add(Box.createVerticalStrut(10));

        statsPanel.add(timeLabel);
        JLabel legend = new JLabel(
                "<html>" +
                        "<h2>LEGEND</h2>" +

                        "<font color='#2ecc71'>■</font> Start<br><br>" +
                        "<font color='#e74c3c'>■</font> End<br><br>" +
                        "<font color='#3498db'>■</font> Path<br><br>" +
                        "<font color='#f1c40f'>■</font> Visited<br><br>" +
                        "<font color='#9b59b6'>■</font> Frontier<br><br>" +
                        "<font color='#e67e22'>■</font> Current<br><br>" +
                        "<font color='#222222'>■</font> Wall" +

                        "</html>"
        );
        JLabel footer = new JLabel(
                "<html><br><br>Java Swing<br>BFS • DFS • A*</html>"
        );

        footer.setForeground(Color.LIGHT_GRAY);

        statsPanel.add(Box.createVerticalStrut(30));

        statsPanel.add(footer);


        legend.setForeground(Color.WHITE);

        statsPanel.add(Box.createVerticalStrut(20));

        statsPanel.add(legend);

        frame.add(statsPanel, BorderLayout.EAST);
        frame.setSize(1400, 900);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}