package in.sakshi.pingpong.playersapp.config;

public class Constants {
    private static final String DELIMITER = ".";
    private static final String PREFIX = "pingpongapp"+DELIMITER+"playersapp";
    public static final String DEFAULT = "NONE";
    public static final String KEY_GAME_ID = PREFIX+DELIMITER+"gameid";
    public static final String KEY_RESPONSE_ID=PREFIX+DELIMITER+"responseid";
    public static final String KEY_PLAYER_NAME = PREFIX+DELIMITER+"playerName";
    public static final String KEY_PLAYER_ID = PREFIX+DELIMITER+"playerid";
    public static final String KEY_PLAYER_GAME_ID = PREFIX+DELIMITER+"gameId";
    public static final String KEY_PLAYER_OPPONENT_ID = PREFIX+DELIMITER+"playerOpponentId";
    public static final String KEY_PLAYER_SCORE = PREFIX+DELIMITER+"playerScore";
    public static final String KEY_PLAYER_PORT = PREFIX+DELIMITER+"playerPort";
    public static final String KEY_PLAYER_IP = PREFIX+DELIMITER+"playerip";
    public static final String PLAYER_JOIN_REQUEST_URI = PREFIX+DELIMITER+"playerJoinUrl";

    //Parser Related Keys
    public static final String PARSER_KEY_PLAYER_PORT = PREFIX+DELIMITER+"player_port";
    public static final String PARSER_KEY_REFEREE_PORT = PREFIX+DELIMITER+"referee_port";
    public static final String PARSER_KEY_DEFENSE_ARRAY_LENGTH = PREFIX+DELIMITER+"defense_array_length";
    public static final String PARSER_KEY_REFEREE_URL = PREFIX+DELIMITER+"referee_url" ;
    public static final String PARSER_KEY_PLAYER_URL = PREFIX+DELIMITER+"player_url";
    public static final String PARSER_KEY_NAME = PREFIX+DELIMITER+"name";

    // Exit Request Handler
    public static final String EXIT_REQUEST_HANDLER_URI = PREFIX+DELIMITER+"exitRequestUrl";
    public static final String EXIT_REQUEST_HANDLER_SUBJECT = PREFIX+DELIMITER+"exitRequestSubject";
    public static final String  KEY_EXIT_REQUEST = PREFIX+DELIMITER+"exitKey";

    // Opponent Notification Handler
    public static final String OPPONENT_REQUEST_HANDLER_URI = PREFIX+DELIMITER+"opponent";
    public static final String OPPONENT_REQUEST_HANDLER_SUBJECT = PREFIX+DELIMITER+"opponentSubject";
    public static final String KEY_OPPONENT_ID_REQUEST = PREFIX+DELIMITER+"opponentId";
    public static final String KEY_OPPONENT_NAME_REQUEST = PREFIX+DELIMITER+"opponentName";

    // Score Update Request Handler
    public static final String SCORE_REQUEST_HANDLER_URI = PREFIX+DELIMITER+"score";
    public static final String SCORE_REQUEST_HANDLER_SUBJECT = PREFIX+DELIMITER+"scoreSubject";

    // Success Response
    public static final String RESPONSE_STATUS = PREFIX+DELIMITER+"responseStatus";
    public static final String RESPONSE_MESSAGE = PREFIX+DELIMITER+"responseMessage";

    // Chance Notification Request Handler
    public static final String PLAYER_CHANCE_RESPONSE_URI = PREFIX+DELIMITER+"playerChanceUrl";
    public static final String CHANCE_NOTIFICATION_SUBJECT = PREFIX+DELIMITER+"chanceNotificationSubject";
    public static final String  KEY_CHANCE_MOVE_VALUE = PREFIX+DELIMITER+"chanceMoveValue";
    public static final String KEY_PLAYER_CHANCE =PREFIX+DELIMITER+"chanceValue";
    public static final String KEY_FOUND_VALUE = PREFIX+DELIMITER+"foundValue";

    // Game Notification handler
    public static final String GAME_NOTIFICATION_RESPONSE_URI = PREFIX+DELIMITER+"gameUrl";
    public static final String GAME_NOTIFICATION_SUBJECT = PREFIX+DELIMITER+"gameSubject";
}
