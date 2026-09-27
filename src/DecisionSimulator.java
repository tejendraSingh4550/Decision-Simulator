import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class DecisionSimulator extends JFrame {

    private JComboBox<String> situationBox;
    private JSlider energySlider;
    private JSlider timeSlider;
    private JLabel energyValue;
    private JLabel timeValue;
    private JTextArea resultArea;
    private JProgressBar scoreBar;

    private final Map<String, Integer> baseScores = new HashMap<>();

    public DecisionSimulator() {

        setTitle("Decision Simulator");
        setSize(620, 620);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        baseScores.put("Java Interview Tomorrow", 80);
        baseScores.put("Accept a New Job", 75);
        baseScores.put("Start a New Project", 70);
        baseScores.put("Learn a New Technology", 65);
        baseScores.put("Buy a New Laptop", 55);
        baseScores.put("Take a Break", 90);

        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        main.setBorder(new EmptyBorder(22, 28, 22, 28));
        main.setBackground(new Color(18, 25, 45));

        // TITLE
        JLabel title = new JLabel("⚡ DECISION SIMULATOR");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(new Color(60, 210, 255));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("Make a smarter decision in seconds");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        subtitle.setForeground(Color.LIGHT_GRAY);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        main.add(title);
        main.add(Box.createVerticalStrut(5));
        main.add(subtitle);
        main.add(Box.createVerticalStrut(25));

        // SITUATION
        JLabel situationLabel = label("Choose Situation");

        situationBox = new JComboBox<>(baseScores.keySet().toArray(new String[0]));
        situationBox.setFont(new Font("Segoe UI", Font.BOLD, 15));
        situationBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));

        main.add(situationLabel);
        main.add(Box.createVerticalStrut(7));
        main.add(situationBox);
        main.add(Box.createVerticalStrut(20));

        // ENERGY
        JLabel energyLabel = label("Energy Level");

        energySlider = new JSlider(0, 100, 50);
        energySlider.setBackground(new Color(18, 25, 45));
        energySlider.setMajorTickSpacing(25);
        energySlider.setPaintTicks(true);

        energyValue = valueLabel("50%");

        JPanel energyPanel = new JPanel(new BorderLayout());
        energyPanel.setBackground(new Color(18, 25, 45));
        energyPanel.add(energySlider, BorderLayout.CENTER);
        energyPanel.add(energyValue, BorderLayout.EAST);

        main.add(energyLabel);
        main.add(energyPanel);
        main.add(Box.createVerticalStrut(15));

        // TIME
        JLabel timeLabel = label("Available Time");

        timeSlider = new JSlider(1, 10, 3);
        timeSlider.setBackground(new Color(18, 25, 45));
        timeSlider.setMajorTickSpacing(1);
        timeSlider.setPaintTicks(true);

        timeValue = valueLabel("3 hrs");

        JPanel timePanel = new JPanel(new BorderLayout());
        timePanel.setBackground(new Color(18, 25, 45));
        timePanel.add(timeSlider, BorderLayout.CENTER);
        timePanel.add(timeValue, BorderLayout.EAST);

        main.add(timeLabel);
        main.add(timePanel);
        main.add(Box.createVerticalStrut(20));

        // BUTTON
        JButton analyzeButton = new JButton("⚡ ANALYZE DECISION");
        analyzeButton.setFont(new Font("Segoe UI", Font.BOLD, 17));
        analyzeButton.setForeground(Color.WHITE);
        analyzeButton.setBackground(new Color(0, 180, 120));
        analyzeButton.setFocusPainted(false);
        analyzeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        analyzeButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        analyzeButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 48));

        main.add(analyzeButton);
        main.add(Box.createVerticalStrut(22));

        // RESULT TITLE
        JLabel resultTitle = new JLabel("SIMULATION RESULT");
        resultTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        resultTitle.setForeground(new Color(255, 190, 70));
        resultTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        main.add(resultTitle);
        main.add(Box.createVerticalStrut(10));

        // RESULT AREA
        resultArea = new JTextArea(
                "Select a situation and click ANALYZE DECISION."
        );

        resultArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        resultArea.setForeground(Color.WHITE);
        resultArea.setBackground(new Color(28, 38, 62));
        resultArea.setEditable(false);
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        resultArea.setBorder(new EmptyBorder(12, 12, 12, 12));

        main.add(resultArea);
        main.add(Box.createVerticalStrut(15));

        // SCORE BAR
        scoreBar = new JProgressBar(0, 100);
        scoreBar.setValue(0);
        scoreBar.setString("0%");
        scoreBar.setStringPainted(true);
        scoreBar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        scoreBar.setForeground(new Color(0, 210, 140));
        scoreBar.setBackground(new Color(40, 45, 60));

        main.add(scoreBar);

        // SLIDER EVENTS
        energySlider.addChangeListener(e ->
                energyValue.setText(energySlider.getValue() + "%")
        );

        timeSlider.addChangeListener(e ->
                timeValue.setText(timeSlider.getValue() + " hrs")
        );

        // BUTTON EVENT
        analyzeButton.addActionListener(e -> analyzeDecision());

        add(main);
    }

    private void analyzeDecision() {

        String situation = (String) situationBox.getSelectedItem();

        int energy = energySlider.getValue();
        int hours = timeSlider.getValue();

        int score = baseScores.get(situation);

        // Energy impact
        if (energy >= 70) {
            score += 10;
        } else if (energy < 30) {
            score -= 15;
        }

        // Time impact
        if (hours >= 6) {
            score += 10;
        } else if (hours <= 2) {
            score -= 5;
        }

        score = Math.max(0, Math.min(100, score));

        scoreBar.setValue(score);
        scoreBar.setString(score + "%");

        String recommendation;

        if (score >= 80) {
            recommendation = "🚀 GO FOR IT!";
        } else if (score >= 60) {
            recommendation = "👍 GOOD OPTION";
        } else if (score >= 40) {
            recommendation = "⚠️ THINK AGAIN";
        } else {
            recommendation = "🛑 BETTER WAIT";
        }

        String advice;

        if (energy < 30) {
            advice = "Your energy is low. Take some rest before deciding.";
        } else if (hours < 3) {
            advice = "You have limited time. Keep the decision simple.";
        } else if (energy >= 70 && hours >= 6) {
            advice = "Your current conditions are highly favorable.";
        } else {
            advice = "Your situation is manageable. Plan before taking action.";
        }

        resultArea.setText(
                "━━━━━━━━━━━━━━━━━━━━━━━━━━\n" +
                "   DECISION ANALYSIS\n" +
                "━━━━━━━━━━━━━━━━━━━━━━━━━━\n\n" +
                "Situation : " + situation + "\n" +
                "Energy    : " + energy + "%\n" +
                "Time      : " + hours + " hours\n\n" +
                "Score     : " + score + "/100\n" +
                "Decision  : " + recommendation + "\n\n" +
                "💡 Advice:\n" +
                advice
        );
    }

    private JLabel label(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(Color.WHITE);
        return label;
    }

    private JLabel valueLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(new Color(60, 210, 255));
        label.setPreferredSize(new Dimension(60, 25));
        return label;
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(
                        UIManager.getSystemLookAndFeelClassName()
                );
            } catch (Exception ignored) {
            }

            new DecisionSimulator().setVisible(true);
        });
    }
}