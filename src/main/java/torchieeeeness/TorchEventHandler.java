package torchieeeeness;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.network.play.server.S2FPacketSetSlot;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;
import net.minecraftforge.event.entity.item.ItemEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;

import static net.minecraftforge.event.entity.player.PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class TorchEventHandler 
{
    private int[] slots = {8, 2, 3, 4, 5, 6, 7, 8, -1};
	public static TorchieeeenessSettings torchieeeenessSettings;
	private static File TorchieeeenessSettingsFile = new File(Minecraft.getMinecraft().mcDataDir, "TorchieeeenessSettings");
	public static KeyBindings key;
	
    @SubscribeEvent
    public void playerInteractEventHandler(PlayerInteractEvent event)
    {
    	load();
        ItemStack heldItem = event.entityPlayer.inventory.getCurrentItem();
        																
    	if (key.config.isPressed() && event.action == Action.RIGHT_CLICK_AIR)
		{
    		load();
    		if(torchieeeenessSettings.settings != 3)
    			torchieeeenessSettings.settings++;
    		if(torchieeeenessSettings.settings >= 3)
    			torchieeeenessSettings.settings = 0;
    		event.entityPlayer.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("torchieeeeness_key" + torchieeeenessSettings.settings)));
		
			save();
		}
    	if(torchieeeenessSettings.settings != 0)
    	{
    		if(torchieeeenessSettings.settings == 1)
    			placeAnything(event);
    		if(torchieeeenessSettings.settings == 2)
        		placeOnlyTorchs(event);
    	}
    }
    
    public void placeOnlyTorchs(PlayerInteractEvent event)
    {
        ItemStack heldItem = event.entityPlayer.inventory.getCurrentItem();
        if (heldItem != null && event.action == RIGHT_CLICK_BLOCK && (heldItem.getItem() instanceof ItemTool))
        {
    		TorchHelper.placeTorch(heldItem, event.entityPlayer, event.world, event.x, event.y, event.z, event.face, 0.5f, 0.5f, 0.5f);
        }
    }
    
    public void placeAnything(PlayerInteractEvent event)
    {
        // Server side and on block only.
        if (event.isCanceled() || event.world.isRemote || event.action != RIGHT_CLICK_BLOCK) return;
        ItemStack heldItem = event.entityPlayer.inventory.getCurrentItem();
        // Only tools, not null
        if (heldItem == null || !(heldItem.getItem() instanceof ItemTool)) return;
        if (event.world.getTileEntity(event.x, event.y, event.z) != null) return;
        // old slot
        int oldSlot = event.entityPlayer.inventory.currentItem;
        // Avoid invalid array indexes
        if (oldSlot < 0 || oldSlot > 8) return;
        // new slot
        int newSlot = slots[oldSlot];
        // Avoid invalid slots indexes
        if (newSlot < 0 || newSlot > 8) return;
        // Get new item
        ItemStack slotStack = event.entityPlayer.inventory.getStackInSlot(newSlot);
        // No null please
        if (slotStack == null) return;
        // Set current slot to new slot to fool Minecraft
        event.entityPlayer.inventory.currentItem = newSlot;
        // Place block
        boolean b = ((EntityPlayerMP) event.entityPlayer).theItemInWorldManager.activateBlockOrUseItem(event.entityPlayer, event.world, slotStack, event.x, event.y, event.z, event.face, 0.5f, 0.5f, 0.5f);
        // Remove empty stacks
        if (slotStack.stackSize <= 0) slotStack = null;
        // Set old slot back properly
        event.entityPlayer.inventory.currentItem = oldSlot;
        // Update client
        event.entityPlayer.inventory.setInventorySlotContents(newSlot, slotStack);
        ((EntityPlayerMP) event.entityPlayer).playerNetServerHandler.sendPacket(new S2FPacketSetSlot(0, newSlot + 36, slotStack));
        // Prevent derpy doors
        event.setCanceled(true);
    }
    
	public static class TorchieeeenessSettings
	{
		public boolean placeOnlyTorchs = false;
		public boolean placeAnything = true;
		public boolean disable = false;
		public int settings = 1;
	}
	
	// load form Json
	public static void load()
	{
		if(!TorchieeeenessSettingsFile.exists())
		{
			torchieeeenessSettings = new TorchieeeenessSettings();
		} else 
		{
			try 
			{
				Gson gson = new Gson();
				BufferedReader reader = new BufferedReader(new FileReader(TorchieeeenessSettingsFile));
				torchieeeenessSettings = gson.fromJson(reader, TorchieeeenessSettings.class);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				torchieeeenessSettings = new TorchieeeenessSettings();
			}
		}
	}
	
	// Save to Json
	public static void save()
	{
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(torchieeeenessSettings);
		try 
		{
			FileWriter writer = new FileWriter(TorchieeeenessSettingsFile);
			writer.write(json);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}