package com.jonispatented.castle_exploration.command_parsing;

import com.jonispatented.castle_exploration.engine.Engine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameCommand {

    private final List<Token> formatTokens;
    private final CommandRoutine commandRoutine;
    private final String formatString;

    public GameCommand(String formatString, CommandRoutine commandRoutine) {
        this.formatTokens = new ArrayList<>();
        this.commandRoutine = commandRoutine;
        this.formatString = formatString;

        String[] tokenStrings = formatString.split(" ");
        for (String tokenString : tokenStrings) {
            String[] keywords = tokenString.substring(1, tokenString.length() - 1).split(",");
            if (tokenString.startsWith("[")) {
                for (int i = 0; i < keywords.length; i++)
                    keywords[i] = keywords[i].replaceAll("_", " ");
                formatTokens.add(new KeywordToken(keywords));
                continue;
            }
            if (tokenString.startsWith("<")) {
                formatTokens.add(new FreeToken());
                continue;
            }
            if (tokenString.startsWith("(")) {
                for (int i = 0; i < keywords.length; i++)
                    keywords[i] = keywords[i].replaceAll("_", " ");
                formatTokens.add(new OptionalKeywordToken(keywords));
            }
        }
    }

    public boolean process(String input, Engine gameContext) {

        List<String> capturedWords = new ArrayList<>();

        for (Token token : formatTokens) {
            input = token.consumeMatch(input, capturedWords);
            if (input == null)
                return false;
        }

        if (input.length() != 0)
            return false;

        commandRoutine.execute(capturedWords, gameContext);

        return true;
    }

    public String getFormatString() {
        return formatString;
    }

    private interface Token {
        String consumeMatch(String input, List<String> capturedWords);
    }

    private static class KeywordToken implements Token {

        private final List<String> acceptedTerms;

        public KeywordToken(String[] keywords) {
            acceptedTerms = Arrays.asList(keywords);
        }

        public String consumeMatch(String input, List<String> capturedWords) {
            for (String term : acceptedTerms) {
                if (input.startsWith(term)
                        && input.length() == term.length())
                    return "";
                if (input.startsWith(term + " "))
                    return input.substring(term.length() + 1);
            }
            return null;
        }

    }

    private static class OptionalKeywordToken implements Token {

        private final List<String> acceptedTerms;

        public OptionalKeywordToken(String[] keywords) {
            acceptedTerms = Arrays.asList(keywords);
        }

        public String consumeMatch(String input, List<String> capturedWords) {
            for (String term : acceptedTerms) {
                if (input.startsWith(term)
                        && input.length() == term.length())
                    return "";
                if (input.startsWith(term + " "))
                    return input.substring(term.length() + 1);
            }
            return input;
        }

    }

    private static class FreeToken implements Token {

        public String consumeMatch(String input, List<String> capturedWords) {
            int tokenEnd = input.indexOf(' ');
            if (tokenEnd == 0)
                return null;
            if (tokenEnd == -1) {
                if (input.trim().equals(""))
                    return null;
                capturedWords.add(input);
                return "";
            }
            capturedWords.add(input.substring(0, tokenEnd));
            return input.substring(tokenEnd + 1);
        }

    }

    public interface CommandRoutine {
        void execute(List<String> keyTerms, Engine gameContext);
    }

}
