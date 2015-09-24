package torchieeeeness;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.settings.KeyBinding;
import torchieeeeness.lib.ModInfo;

public class KeyBindings 
{
	public static KeyBinding config = new KeyBinding(ModInfo.Keys.CONFIG,
			Keyboard.KEY_NUMPAD8, ModInfo.Keys.CATEGORY);
}
