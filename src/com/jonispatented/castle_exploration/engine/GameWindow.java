package com.jonispatented.castle_exploration.engine;

import com.jonispatented.castle_exploration.command_parsing.InputParser;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import java.awt.*;

public class GameWindow extends JFrame {

    private JTextArea gameTextOutput;
    private JTextField gameTextInput;
    private JTextField roomNameField;

    public GameWindow(Engine gameContext) {
        super("Castle Exploration");
        setPreferredSize(new Dimension(480, 700));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        add(mainPanel);

        // Make the border and font that will be used for all the panels
        Border border = BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.GRAY, Color.DARK_GRAY);
        Font gameFont = new Font("Courier New", Font.PLAIN, 12);

        // This panel will display the room name at the top of the screen
        JPanel roomNamePanel = new JPanel();
        roomNamePanel.setLayout(new BoxLayout(roomNamePanel, BoxLayout.X_AXIS));
        roomNamePanel.setBorder(border);
        roomNameField = new JTextField();
        roomNameField.setEditable(false);
        roomNameField.setFont(gameFont.deriveFont(Font.BOLD, 24f));
        roomNamePanel.add(roomNameField);
        mainPanel.add(roomNamePanel, BorderLayout.NORTH);

        // This panel represents the game text output in the center of the screen
        JPanel outputPanel = new JPanel();
        outputPanel.setLayout(new BorderLayout());
        outputPanel.setBorder(border);
        gameTextOutput = new JTextArea();
        gameTextOutput.setEditable(false);
        gameTextOutput.setFont(gameFont.deriveFont(Font.ITALIC, 14f));
        gameTextOutput.setLineWrap(true);
        gameTextOutput.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(gameTextOutput);
        outputPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(outputPanel, BorderLayout.CENTER);

        // This panel is the input field at the bottom of the screen
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());
        inputPanel.setBorder(border);
        gameTextInput = new JTextField();
        gameTextInput.setFont(gameFont.deriveFont(14f));
        inputPanel.add(gameTextInput, BorderLayout.CENTER);
        mainPanel.add(inputPanel, BorderLayout.SOUTH);

        // Send the input to the parsing engine
        gameTextInput.addActionListener(e -> {
            String input = gameTextInput.getText();
            InputParser.parseInput(input, gameContext);
            gameTextInput.setText("");
        });

        pack();
        setVisible(true);
    }

    public void writeLineToGameOutput(String message) {
        gameTextOutput.append(message + "\n\n");
    }

    public void setRoomName(String roomName) {
        roomNameField.setText(roomName);
    }
}
