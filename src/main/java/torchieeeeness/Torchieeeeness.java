package torchieeeeness;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.common.MinecraftForge;
import torchieeeeness.lib.ModInfo;
import torchieeeeness.network.PacketHandler;

@Mod(name = ModInfo.MOD_NAME, modid = ModInfo.MOD_ID, version = ModInfo.MOD_VERSION)
public class Torchieeeeness 
{
	@Instance(ModInfo.MOD_ID)
	public static Torchieeeeness instance;

	@SidedProxy(clientSide = ModInfo.MOD_CLIENT_PROXY, serverSide = ModInfo.MOD_COMMON_PROXY)
	public static CommonProxy proxy;
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{
		// Register the place torch event
		MinecraftForge.EVENT_BUS.register(new TorchEventHandler());
		// Register keybindings on the client side only
		proxy.registerKeybindings();
		// Register packet handler
		PacketHandler.init();
	}
}
