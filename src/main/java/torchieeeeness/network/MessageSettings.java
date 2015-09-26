package torchieeeeness.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import torchieeeeness.TorchEventHandler;

public class MessageSettings implements IMessage, IMessageHandler<MessageSettings, IMessage>
{
	private int setting;
	//This has to be here or crash ??
	public MessageSettings() 
	{
		
	}
	
	public MessageSettings(int clientSetting) 
	{
		this.setting = clientSetting;
	}

	@Override
	public void fromBytes(ByteBuf buf) 
	{
		this.setting = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(setting);
	}
	
	@Override
	public IMessage onMessage(MessageSettings message, MessageContext ctx) 
	{
		TorchEventHandler.serverSetting = message.setting;
		System.out.println(message.setting);
		return null;
	}
}
