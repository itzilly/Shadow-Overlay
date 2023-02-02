package com.itzilly.shadowOverlay.parsers;

public enum GameLineParser {
    BEDWARS(new BedwarsLineParser());

    private final LineParser lineParser;

    GameLineParser(LineParser lineParser) {
        this.lineParser = lineParser;
    }

    public LineParser getLineParser() {
        return lineParser;
    }
}
