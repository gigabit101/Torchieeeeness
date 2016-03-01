package torchieeeeness.core;

import org.lwjgl.input.Mouse;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import static net.minecraftforge.event.entity.player.PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK;
import torchieeeeness.util.TorchHelper;

public class TorchEventHandler 
{
    public Minecraft mc = Minecraft.getMinecraft();
	
	@SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void playerInteractEventHandler(PlayerInteractEvent event)
    {
		if(event.entityPlayer != null)
		{
			EntityPlayer player = event.entityPlayer;
			if(player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() instanceof ItemPickaxe && event.action == RIGHT_CLICK_BLOCK)
			{
				ItemStack helditem = player.getCurrentEquippedItem();
				TorchHelper.placeTorch(helditem, player, event.world, event.pos, event.face, 0.5f, 0.5f, 0.5f);
			}
		}
    }
}