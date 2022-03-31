package com.uecepi.emarrow.networking;

import java.util.Optional;
import java.util.stream.Stream;

public class ConnectionResponsePacket {

    private final byte responseValue;

    public ConnectionResponsePacket(byte response) {
        this.responseValue = response;
    }

    public ConnectionResponsePacket(ResponseMeaning meaning) {
        this.responseValue = meaning.uniqueIndex;
    }

    public enum ResponseMeaning {

        CONNECTION_SUCCESS((byte) 0, "Connection is validated."),
        CONNECTION_FAILED((byte) 1, "The provided password doesn't match.");

        private final byte uniqueIndex;
        private final String explaination;

        ResponseMeaning(byte uniqueIndex, String explaination) {
            this.uniqueIndex = uniqueIndex;
            this.explaination = explaination;
        }

        public static Optional<ResponseMeaning> getMeaningByIndex(byte index){
            return Stream.of(values()).filter(meaning -> meaning.uniqueIndex == index).findFirst();
        }
    }
}
