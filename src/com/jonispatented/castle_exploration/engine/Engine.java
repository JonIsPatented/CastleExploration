package com.jonispatented.castle_exploration.engine;

import com.jonispatented.castle_exploration.command_parsing.GameCommand;
import com.jonispatented.castle_exploration.command_parsing.InputParser;
import com.jonispatented.castle_exploration.creatures.player.Player;

import java.util.List;

public class Engine {

    private final Player player;
    private boolean shouldContinue;
    private List<GameCommand> commandList;
    private GameWindow gameWindow;

    public Engine() {
        player = new Player();
        gameWindow = new GameWindow(this);
    }

    public void start() {
        System.out.println("Welcome to Castle Exploration!");

        GameState.loadGame(this);

        shouldContinue = true;
        while (shouldContinue);
    }

    public void stop() {
        shouldContinue = false;
    }

    public Player getPlayer() {
        return player;
    }

    public List<GameCommand> getCommandList() {
        return commandList;
    }

    public void setCommandList(List<GameCommand> commandList) {
        this.commandList = commandList;
    }

    public GameWindow getGameWindow() {
        return gameWindow;
    }

    public static void main(String[] args) {
        Engine engine = new Engine();
        engine.start();
    }

}
