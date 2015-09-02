package wiiz.blink.common.network.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import wiiz.blink.Blink;

public class PacketParticles implements IMessage, IMessageHandler<PacketParticles, IMessage>
{

	private double posX, posY, posZ;
	private double targetX, targetY, targetZ;

	public PacketParticles()
	{
	}

	public PacketParticles(double posX, double posY, double posZ, double targetX, double targetY, double targetZ)
	{
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
		this.targetX = targetX;
		this.targetY = targetY;
		this.targetZ = targetZ;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		posX = buf.readDouble();
		posY = buf.readDouble();
		posZ = buf.readDouble();
		targetX = buf.readDouble();
		targetY = buf.readDouble();
		targetZ = buf.readDouble();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeDouble(posX);
		buf.writeDouble(posY);
		buf.writeDouble(posZ);
		buf.writeDouble(targetX);
		buf.writeDouble(targetY);
		buf.writeDouble(targetZ);
	}

	@Override
	public IMessage onMessage(final PacketParticles message, MessageContext ctx)
	{
		if (ctx.side == Side.CLIENT)
		{

			Minecraft minecraft = Minecraft.getMinecraft();
			final WorldClient worldClient = minecraft.theWorld;
			minecraft.addScheduledTask(new Runnable()
			{
				public void run()
				{
					Blink.proxy.spawnParticles(worldClient, message.posX, message.posY, message.posZ, message.targetX, message.targetY, message.targetZ);
				}
			});
		}

		return null;
	}

}
