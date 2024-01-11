package com.jonispatented.castle_exploration.command_parsing;

import com.jonispatented.castle_exploration.engine.Engine;

public class InputParser {

    public static void parseInput(String input, final Engine gameContext) {
        input = input.toLowerCase().strip();
        for (GameCommand command : gameContext.getCommandList()) {
            if (command.process(input, gameContext))
                return;
        }
        System.out.println("Unrecognized command. Type \"Help\" for a list of commands.");
    }

}
