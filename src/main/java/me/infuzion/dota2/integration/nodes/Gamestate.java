package me.infuzion.dota2.integration.nodes;

public enum Gamestate {
    Undefined("undefined"),

    /**
     * Player is disconnected
     */
    DOTA_GAMERULES_STATE_DISCONNECT("disconnected"),

    /**
     * Game is in progress
     */
    DOTA_GAMERULES_STATE_GAME_IN_PROGRESS("in progress"),

    /**
     * Players are currently selecting heroes
     */
    DOTA_GAMERULES_STATE_HERO_SELECTION("selecting heroes"),

    /**
     * Game is starting
     */
    DOTA_GAMERULES_STATE_INIT("initializing"),

    /**
     * Game is ending
     */
    DOTA_GAMERULES_STATE_LAST("ending"),

    /**
     * Game has ended, post game scoreboard
     */
    DOTA_GAMERULES_STATE_POST_GAME("scoreboard"),

    /**
     * Game has started, pre game preparations
     */
    DOTA_GAMERULES_STATE_PRE_GAME("pre game"),

    /**
     * Players are selecting/banning heroes
     */
    DOTA_GAMERULES_STATE_STRATEGY_TIME("strategy time"),

    /**
     * Waiting for everyone to connect and load
     */
    DOTA_GAMERULES_STATE_WAIT_FOR_PLAYERS_TO_LOAD("waiting for players to load"),

    /**
     * Game is a custom game
     */
    DOTA_GAMERULES_STATE_CUSTOM_GAME_SETUP("setting up custom game");

    private final String friendlyName;

    Gamestate(String friendlyName){
        this.friendlyName = friendlyName;
    }

    public String getFriendlyName() {
        return friendlyName;
    }
}
