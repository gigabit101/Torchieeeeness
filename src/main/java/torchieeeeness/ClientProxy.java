package torchieeeeness;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;

public class ClientProxy extends CommonProxy
{
	@Override
	public void registerKeybindings()
	{
		ClientRegistry.registerKeyBinding(KeyBindings.config);
	}
}
