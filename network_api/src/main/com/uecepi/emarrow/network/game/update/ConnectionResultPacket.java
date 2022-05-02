package com.uecepi.emarrow.network.game.update;

import java.util.Optional;
import java.util.stream.Stream;

public class ConnectionResultPacket {

    private int result;

    public ConnectionResultPacket() {
    }

    /**
     * @param meaning Result meaning of the connection attempt
     *                The value depends on the {@link ResultMeaning} enum
     */
    public ConnectionResultPacket(ResultMeaning meaning) {
        this.result = meaning.resultNumber;
    }

    public ResultMeaning getResult() {
        return ResultMeaning.getResultByNumber(result).orElse(ResultMeaning.UNKNOWN_ERROR);
    }

    public enum ResultMeaning {

        ACCEPTED(0),
        GAME_FULL(1),
        GAME_STARTED(2),
        ALREADY_CONNECTED(3),
        UNKNOWN_ERROR(4);

        private final int resultNumber;

        ResultMeaning(int resultNumber) {
            this.resultNumber = resultNumber;
        }

        public static Optional<ResultMeaning> getResultByNumber(int number) {
            return Stream.of(values()).filter(meaning -> meaning.resultNumber == number).findAny();
        }
    }
}
