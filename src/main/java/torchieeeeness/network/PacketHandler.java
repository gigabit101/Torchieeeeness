package torchieeeeness.network;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import torchieeeeness.lib.ModInfo;

public class PacketHandler 
{
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(ModInfo.MOD_ID.toLowerCase());
    
    public static void init()
    {
        INSTANCE.registerMessage(MessageSettings.class, MessageSettings.class, 0, Side.SERVER);
    }
}
