package torchieeeeness;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import torchieeeeness.core.TorchEventHandler;
import torchieeeeness.lib.ModInfo;

@Mod(name = ModInfo.MOD_NAME, modid = ModInfo.MOD_ID, version = ModInfo.MOD_VERSION)
public class Torchieeeeness 
{
	@Instance(ModInfo.MOD_ID)
	public static Torchieeeeness instance;
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{
		// Register the place torch event
		MinecraftForge.EVENT_BUS.register(new TorchEventHandler());
	}
}
