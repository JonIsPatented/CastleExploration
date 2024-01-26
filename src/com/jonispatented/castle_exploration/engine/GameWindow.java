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

        Border border = BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.GRAY, Color.DARK_GRAY);

        JPanel roomNamePanel = new JPanel();
        roomNamePanel.setLayout(new BoxLayout(roomNamePanel, BoxLayout.X_AXIS));
        roomNamePanel.setBorder(border);
        roomNameField = new JTextField();
        roomNameField.setEditable(false);
        roomNamePanel.add(roomNameField);
        mainPanel.add(roomNamePanel, BorderLayout.NORTH);

        JPanel outputPanel = new JPanel();
        outputPanel.setLayout(new BorderLayout());
        outputPanel.setBorder(border);
        gameTextOutput = new JTextArea();
        gameTextOutput.setEditable(false);
        gameTextOutput.setLineWrap(true);
        gameTextOutput.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(gameTextOutput);
        outputPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(outputPanel, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());
        inputPanel.setBorder(border);
        gameTextInput = new JTextField();
        inputPanel.add(gameTextInput, BorderLayout.CENTER);
        mainPanel.add(inputPanel, BorderLayout.SOUTH);

        gameTextInput.addActionListener(e -> {
            String input = gameTextInput.getText();
            InputParser.parseInput(input, gameContext);
            gameTextInput.setText("");
        });

        pack();
        setVisible(true);
    }

    public void writeLineToGameOutput(String message) {
        gameTextOutput.append(message + "\n");
    }

    public void setRoomName(String roomName) {
        roomNameField.setText(roomName);
    }
}
