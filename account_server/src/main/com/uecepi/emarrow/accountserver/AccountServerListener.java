package com.uecepi.emarrow.accountserver;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.uecepi.emarrow.network.PingPacket;
import com.uecepi.emarrow.network.account.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class AccountServerListener extends Listener {

    private final static Logger LOGGER = LoggerFactory.getLogger(AccountServerListener.class);

    @Override
    public void connected(Connection connection) {
        super.connected(connection);
        LOGGER.info("Connection of a client on {} ({} (TCP) and {} (UDP))", connection.getRemoteAddressTCP().getAddress(),
                connection.getRemoteAddressTCP(), connection.getRemoteAddressUDP());
    }

    @Override
    public void disconnected(Connection connection) {
        super.connected(connection);
        LOGGER.info("Disconnection of a client");
    }

    @Override
    public void received(Connection connection, Object object) {
        super.received(connection, object);
        if (!DatabaseConnector.getInstance().isConnected())
            DatabaseConnector.getInstance().connect();
        if (object instanceof PingPacket) {
            connection.sendTCP(object);
        } else if (object instanceof IdentificationPacket) {
            credentialsPacketReceived((IdentificationPacket) object, connection);
        } else if (object instanceof AccountCreationPacket) {
            accountCreationPacketReceived((AccountCreationPacket) object, connection);
        }
    }

    /**
     * Action performed when a credentials' packet is received
     */
    private void credentialsPacketReceived(IdentificationPacket packet, Connection connection) {
        LOGGER.info("Received account credentials packet!");
        boolean result = DatabaseConnector.getInstance().attemptLogIn(packet.getIdentifier(), packet.getPassword());
        if (result) sendPlayerData(packet.getIdentifier(), connection);
        connection.sendTCP(new IdentificationResponsePacket(result));
    }

    /**
     * Action performed when an account creation packet is received
     */
    private void accountCreationPacketReceived(AccountCreationPacket creationPacket, Connection connection) {
        LOGGER.info("Received account creation packet!");
        boolean response = DatabaseConnector.getInstance().createAccount(creationPacket.getIdentifier(), creationPacket.getPassword(), creationPacket.getNickname());
        if (response) sendPlayerData(creationPacket.getIdentifier(), connection);
        connection.sendTCP(new AccountCreationResponsePacket(response));
    }

    /**
     * Sends players' data to the connection
     */
    private void sendPlayerData(String identifier, Connection connection) {
        String[] data = DatabaseConnector.getInstance().getPlayerData(identifier);
        connection.sendTCP(new PlayerDataPacket(UUID.fromString(data[0]), data[1]));
    }
}
