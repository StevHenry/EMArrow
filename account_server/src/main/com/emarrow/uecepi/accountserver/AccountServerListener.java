package com.emarrow.uecepi.accountserver;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.uecepi.emarrow.networking.*;
import com.uecepi.emarrow.networking.account.AccountCreationPacket;
import com.uecepi.emarrow.networking.account.AccountCreationResponsePacket;
import com.uecepi.emarrow.networking.account.ConnectionResponsePacket;
import com.uecepi.emarrow.networking.account.CredentialsPacket;
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
        } else if (object instanceof CredentialsPacket) {
            credentialsPacketReceived((CredentialsPacket) object, connection);
        } else if (object instanceof AccountCreationPacket) {
            accountCreationPacketReceived((AccountCreationPacket) object, connection);
        }
    }

    private void pingPacketReceived(PingPacket packet, Connection connection) {
        connection.sendTCP(packet);
    }

    private void credentialsPacketReceived(CredentialsPacket packet, Connection connection) {
        LOGGER.info("Received account credentials packet!");
        boolean result = DatabaseConnector.getInstance()
                .attemptLogIn(packet.getIdentifier(), packet.getPassword());
        connection.sendTCP(new ConnectionResponsePacket(result));
    }

    private void accountCreationPacketReceived(AccountCreationPacket creationPacket, Connection connection) {
        LOGGER.info("Received account creation packet!");
        boolean response = DatabaseConnector.getInstance().createAccount(creationPacket.getIdentifier(),
                creationPacket.getPass(), creationPacket.getNickname());
        AccountCreationResponsePacket responsePacket = new AccountCreationResponsePacket(response);
        connection.sendTCP(responsePacket);
    }
}
