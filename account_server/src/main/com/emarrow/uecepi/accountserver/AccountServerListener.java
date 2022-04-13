package com.emarrow.uecepi.accountserver;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.uecepi.emarrow.networking.PingPacket;
import com.uecepi.emarrow.networking.account.*;
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
    public void disconnected(Connection connection) {
        super.connected(connection);
        LOGGER.info("Disconnection of a client on {} (TCP) and {} (UDP)",
                connection.getRemoteAddressTCP(), connection.getRemoteAddressUDP());
    }

    @Override
    public void received(Connection connection, Object object) {
        super.received(connection, object);
        if (object instanceof PingPacket) {
            pingPacketReceived((PingPacket) object, connection);
        } else if (object instanceof IdentificationPacket) {
            credentialsPacketReceived((IdentificationPacket) object, connection);
        } else if (object instanceof AccountCreationPacket) {
            accountCreationPacketReceived((AccountCreationPacket) object, connection);
        }
    }

    private void pingPacketReceived(PingPacket packet, Connection connection) {
        connection.sendTCP(packet);
    }

    private void credentialsPacketReceived(IdentificationPacket packet, Connection connection) {
        LOGGER.info("Received account credentials packet!");
        boolean result = DatabaseConnector.getInstance()
                .attemptLogIn(packet.getIdentifier(), packet.getPassword());
        if (result)
            sendPlayerData(packet.getIdentifier(), connection);
        connection.sendTCP(new IdentificationResponsePacket(result));
    }

    private void accountCreationPacketReceived(AccountCreationPacket creationPacket, Connection connection) {
        LOGGER.info("Received account creation packet!");
        boolean response = DatabaseConnector.getInstance().createAccount(creationPacket.getIdentifier(),
                creationPacket.getPassword(), creationPacket.getNickname());
        if (response)
            sendPlayerData(creationPacket.getIdentifier(), connection);
        connection.sendTCP(new AccountCreationResponsePacket(response));
    }

    private void sendPlayerData(String identifier, Connection connection) {
        String[] data = DatabaseConnector.getInstance().getPlayerData(identifier);
        connection.sendTCP(new PlayerDataPacket(data[0], data[1]));
    }
}
