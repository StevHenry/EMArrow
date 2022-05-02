package com.uecepi.emarrow.network;

import com.esotericsoftware.kryo.Kryo;
import com.uecepi.emarrow.network.account.*;
import com.uecepi.emarrow.network.game.player_action.*;
import com.uecepi.emarrow.network.game.update.*;

public class PacketManager {

    public static void registerAccountPackets(Kryo kryo) {
        kryo.register(PingPacket.class);
        kryo.register(IdentificationPacket.class);
        kryo.register(IdentificationResponsePacket.class);
        kryo.register(AccountCreationPacket.class);
        kryo.register(AccountCreationResponsePacket.class);
        kryo.register(PlayerDataPacket.class);
    }

    public static void registerGamePackets(Kryo kryo) {
        kryo.register(PlayerDataPacket.class);
        kryo.register(ConnectionResultPacket.class);
        kryo.register(PlayerJoinedPacket.class);
        kryo.register(PlayerDisconnectedPacket.class);

        kryo.register(GameStateChangedPacket.class);
        kryo.register(TitleChangedPacket.class);
        kryo.register(SubtitleChangedPacket.class);

        kryo.register(TransformationPacket.class);
        kryo.register(ForceAppliedPacket.class);
        kryo.register(LinearImpulsePacket.class);
        kryo.register(ForceToCenterPacket.class);
        kryo.register(CharacterFlipPacket.class);
        kryo.register(LinearVelocityPacket.class);

        kryo.register(PlayerShootPacket.class);
        kryo.register(ProjectileCollisionPacket.class);
        kryo.register(PlayerDamagedPacket.class);
    }
}
