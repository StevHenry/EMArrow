package com.uecepi.emarrow.networking;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.uecepi.emarrow.Character;
import com.uecepi.emarrow.GameEngine;
import com.uecepi.emarrow.PlayerInfo;
import com.uecepi.emarrow.display.GameScreen;
import com.uecepi.emarrow.display.Screens;
import com.uecepi.emarrow.display.menus.ConnectionMenu;
import com.uecepi.emarrow.network.game.update.*;

public class GameListener extends Listener {

    @Override
    public void received(Connection connection, Object object) {
        super.received(connection, object);
        GameEngine engine = GameEngine.getInstance();

        if (object instanceof ConnectionResultPacket) {
            ConnectionResultPacket packet = (ConnectionResultPacket) object;
            ((ConnectionMenu) Screens.CONNECTION_MENU.getScreenMenu()).responseReceived(packet.getResult());
            if (packet.getResult() != ConnectionResultPacket.ResultMeaning.ACCEPTED) {
                connection.close();
            }

        } else if (object instanceof PlayerJoinedPacket) {
            PlayerJoinedPacket packet = ((PlayerJoinedPacket) object);
            Gdx.app.postRunnable(() -> {
                if (packet.getPlayerUid().equals(engine.getSelfPlayer().getUuid())) {
                    engine.getSelfPlayer().getCharacter().getAnimator().setCharacterNumber(packet.getSkinId());
                } else {
                    PlayerInfo playerInfo = new PlayerInfo(packet.getPlayerUid(), packet.getName());
                    engine.addPlayer(playerInfo);
                    playerInfo.getCharacter().getAnimator().setCharacterNumber(packet.getSkinId());
                }
            });

        } else if (object instanceof PlayerDisconnectedPacket) {
            PlayerDisconnectedPacket packet = (PlayerDisconnectedPacket) object;
            engine.getPlayerByUUID(packet.getPlayerUid()).ifPresent(engine::removePlayer);

        } else if (object instanceof GameStateChangedPacket) {
            GameStateChangedPacket packet = (GameStateChangedPacket) object;
            engine.setState(packet.getNewGameState());

        } else if (object instanceof TitleChangedPacket) {
            TitleChangedPacket packet = (TitleChangedPacket) object;
            ((GameScreen) Screens.GAME_SCREEN.getScreenMenu()).setTitle(packet.getTitle());

        } else if (object instanceof SubtitleChangedPacket) {
            SubtitleChangedPacket packet = (SubtitleChangedPacket) object;
            ((GameScreen) Screens.GAME_SCREEN.getScreenMenu()).setSubtitle(packet.getSubtitle());
        }
    }

    @Override
    public void disconnected(Connection connection) {
        super.disconnected(connection);
        Gdx.app.postRunnable(() -> {
            GameEngine.getInstance().getSelfClient().disconnect();
            //GameEngine.getInstance()
            Screens.setScreen(Screens.MAIN_MENU);
            Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
            ((GameScreen) Screens.GAME_SCREEN.getScreenMenu()).setTitle(null);
            ((GameScreen) Screens.GAME_SCREEN.getScreenMenu()).setSubtitle(null);
        });
    }
}
