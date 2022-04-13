package com.uecepi.emarrow.networking.game;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.uecepi.emarrow.Character;
import com.uecepi.emarrow.GameEngine;
import com.uecepi.emarrow.PlayerInfo;
import com.uecepi.emarrow.networking.SkinIndexPacket;
import com.uecepi.emarrow.networking.account.PlayerDataPacket;
import com.uecepi.emarrow.networking.game.actions.ForceAppliedPacket;

import java.util.ArrayList;

public class GameListener extends Listener {

    @Override
    public void received(Connection connection, Object object) {
        super.received(connection, object);
        if (object instanceof PlayerDataPacket) {
            Gdx.app.log("game_listener", "received a playerDataPacket from the server");
            PlayerDataPacket packet = (PlayerDataPacket) object;

            Gdx.app.postRunnable(() -> {
                PlayerInfo info = new PlayerInfo(
                        new Character(findNextAvailableIndex()),
                        packet.getUUID(), packet.getNickname());
                GameEngine.getInstance().addPlayer(info);
            });
        } else if (object instanceof ForceAppliedPacket) {
            ForceAppliedPacket packet = (ForceAppliedPacket) object;
            GameEngine.getInstance().getPlayers().stream()
                    .filter(pl -> pl.getUuid().toString().equals(packet.getPlayerUid()))
                    .forEach(pl -> pl.getCharacter().getBody()
                            .applyForce(packet.getForce(), packet.getApplicationPoint(), packet.isWake()));
        } else if (object instanceof SkinIndexPacket) {
            GameEngine.getInstance().getPlayers().get(0)
                    .getCharacter().getAnimator().setCharacterNumber(((SkinIndexPacket) object).getSkinIndex());
        }
    }

    private int findNextAvailableIndex() {
        int i = 1;
        ArrayList<Integer> unavailable = new ArrayList<>();
        for (PlayerInfo player : GameEngine.getInstance().getPlayers()) {
            unavailable.add(player.getCharacter().getAnimator().getCharacterNumber());
        }
        while (unavailable.contains(i)) {
            i++;
        }
        return i;
    }
}
