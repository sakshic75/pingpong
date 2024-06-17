package in.sakshi.pingpong.refereeapp.config;

public final class Constants {
    public static final String DEFAULT="NONE";
    private static final String DELIMITER = ".";
    private static final String PREFIX="pingpongapp"+DELIMITER+"refereeapp";
    public static final String JOIN_GAME_REQUEST_URI=PREFIX+DELIMITER+"joingame";
    public static final String JOIN_GAME_REQUEST_ID = PREFIX+DELIMITER+"joingameid";
    public static final String KEY_GAME_ID = PREFIX+DELIMITER+"gameid";
    public static final String KEY_PLAYER_NAME = PREFIX+DELIMITER+"playerName";
    public static final String KEY_PLAYER_ID = PREFIX+DELIMITER+"playerid";
    public static final String KEY_PLAYER_SCORE = PREFIX+DELIMITER+"playerScore";
    public static final String KEY_PLAYER_PORT = PREFIX+DELIMITER+"playerPort";
    public static final String KEY_PLAYER_IP = PREFIX+DELIMITER+"playerip";
    public static final String SENTINEL_SCORE = PREFIX+DELIMITER+"sentinel_score";
    public static final String GAME_REPORT_FILENAME = PREFIX+DELIMITER+"filename";

    // Command Line Argument Parser
    public static final String PARSER_KEY_PORT = PREFIX+DELIMITER+"referee_port";
    public static final String PARSER_KEY_PLAYER_COUNT = PREFIX+DELIMITER+"players_count";
    public static final String PARSER_KEY_SERVER_URL = PREFIX+DELIMITER+"referee_url";

    // Opponent Notification Request
    public static final String OPPONENT_REQUEST_HANDLER_URI = PREFIX+DELIMITER+"opponent";
    public static final String KEY_OPPONENT_ID_REQUEST = PREFIX+DELIMITER+"opponentId";
    public static final String KEY_OPPONENT_NAME_REQUEST = PREFIX+DELIMITER+"opponentName";

    // Score Update Request
    public static final String SCORE_REQUEST_HANDLER_URI = PREFIX+DELIMITER+"score";

    // Player Chance Notification Request
    public static final String PLAYER_CHANCE_REQUEST_URI = PREFIX+DELIMITER+"playerChanceUrl";
    public static final String KEY_PLAYER_CHANCE = PREFIX+DELIMITER+"chanceValue";
    public static final String  KEY_CHANCE_MOVE_VALUE = PREFIX+DELIMITER+"chanceMoveValue";
    public static final String KEY_FOUND_VALUE = PREFIX+DELIMITER+"foundValue";

    // Exit Request
    public static final String EXIT_REQUEST_HANDLER_URI = PREFIX+DELIMITER+"exitRequestUrl";
    public static final String  KEY_EXIT_REQUEST = PREFIX+DELIMITER+"exitKey";

    // Game Notification handler
    public static final String GAME_NOTIFICATION_RESPONSE_URI = PREFIX+DELIMITER+"gameUrl";

    // Success Response
    public static final String RESPONSE_STATUS = PREFIX+DELIMITER+"responseStatus";
    public static final String RESPONSE_MESSAGE = PREFIX+DELIMITER+"responseMessage";


}
