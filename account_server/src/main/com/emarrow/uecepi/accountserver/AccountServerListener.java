package com.emarrow.uecepi.accountserver;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.uecepi.emarrow.networking.ConnectionResponsePacket;
import com.uecepi.emarrow.networking.CredentialsPacket;
import com.uecepi.emarrow.networking.PingPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AccountServerListener extends Listener {

    private final static Logger LOGGER = LoggerFactory.getLogger(AccountServerListener.class);

    @Override
    public void connected(Connection connection) {
        super.connected(connection);
        LOGGER.info("Connection of a client on {} (TCP) and {} (UDP)",
                connection.getRemoteAddressTCP(), connection.getRemoteAddressUDP());
    }

    @Override
    public void received(Connection connection, Object object) {
        super.received(connection, object);
        LOGGER.debug("Received a packet from a client!");
        if (object instanceof PingPacket) {
            PingPacket request = (PingPacket) object;
            LOGGER.info("Received a ping!");
            connection.sendTCP(request);
        } else if (object instanceof CredentialsPacket) {
            CredentialsPacket credentialsPacket = (CredentialsPacket) object;
            boolean result = DatabaseConnector.getInstance().attemptLogIn(credentialsPacket.getIdentifier(), credentialsPacket.getPassword());
            connection.sendTCP(new ConnectionResponsePacket(result ?
                    ConnectionResponsePacket.ResponseMeaning.CONNECTION_SUCCESS :
                    ConnectionResponsePacket.ResponseMeaning.CONNECTION_FAILED));
        }
    }
}
