package com.emarrow.uecepi.server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.uecepi.emarrow.networking.PingPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerListener extends Listener {

    private final static Logger LOGGER = LoggerFactory.getLogger(ServerListener.class);

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
        }
    }
}
