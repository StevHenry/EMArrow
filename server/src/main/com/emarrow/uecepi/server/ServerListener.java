package com.emarrow.uecepi.server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.uecepi.emarrow.networking.ConnectionPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerListener extends Listener {

    private final static Logger LOGGER = LoggerFactory.getLogger(ServerListener.class);

    @Override
    public void received(Connection connection, Object object) {
        if (object instanceof ConnectionPacket) {
            ConnectionPacket request = (ConnectionPacket) object;

            LOGGER.info(request.getPlayerData().getUUID().toString());

        }
    }
}
