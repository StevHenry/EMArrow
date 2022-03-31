package java.com.emarrow.uecepi;

import com.emarrow.uecepi.accountserver.AccountServerStarter;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.uecepi.emarrow.networking.PingPacket;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ServerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerTest.class);

    @Test
    public void testServer() throws IOException, InterruptedException {
        new AccountServerStarter();
        final boolean[] valid = {false};

        Client client = new Client();
        client.start();
        client.getKryo().register(PingPacket.class);

        client.addListener(new Listener() {
            @Override
            public void received(Connection connection, Object obj) {
                super.received(connection, obj);
                LOGGER.debug("Received a packet from the server!");
                if (obj instanceof PingPacket) {
                    valid[0] = true;
                    LOGGER.info("Received a pong!");
                }
            }
        });

        client.connect(5000, "127.0.0.1", 54555, 54777);

        client.sendTCP(new PingPacket());
        for (int i = 0; i < 50; i++) {
            if (!valid[0]) {
                Thread.sleep(100);
            } else
                return;
        }
        Assertions.fail();
    }
}
