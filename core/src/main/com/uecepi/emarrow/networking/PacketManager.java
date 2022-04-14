package com.uecepi.emarrow.networking;

import com.esotericsoftware.kryo.Kryo;
import com.uecepi.emarrow.networking.account.*;
import com.uecepi.emarrow.networking.game.PlayerDisconnectedPacket;
import com.uecepi.emarrow.networking.game.actions.*;

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
        kryo.register(PlayerShootPacket.class);
        kryo.register(ForceAppliedPacket.class);
        kryo.register(SkinIndexPacket.class);
        kryo.register(TransformationPacket.class);
        kryo.register(LinearImpulsePacket.class);
        kryo.register(ForceToCenterPacket.class);
        kryo.register(CharacterFlipPacket.class);
        kryo.register(PlayerDisconnectedPacket.class);
    }
}
