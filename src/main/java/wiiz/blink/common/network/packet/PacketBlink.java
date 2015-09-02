package wiiz.blink.common.network.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import wiiz.blink.common.network.PacketHandler;
import wiiz.blink.common.network.core.ConfigHandler;

public class PacketBlink implements IMessage, IMessageHandler<PacketBlink, IMessage>
{

	private double targetX, targetY, targetZ;

	public PacketBlink()
	{
	}

	public PacketBlink(double targetX, double targetY, double targetZ)
	{
		this.targetX = targetX;
		this.targetY = targetY;
		this.targetZ = targetZ;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		targetX = buf.readDouble();
		targetY = buf.readDouble();
		targetZ = buf.readDouble();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeDouble(targetX);
		buf.writeDouble(targetY);
		buf.writeDouble(targetZ);
	}

	@Override
	public IMessage onMessage(final PacketBlink message, MessageContext ctx)
	{

		IThreadListener thread = (WorldServer) ctx.getServerHandler().playerEntity.worldObj;

		final EntityPlayer player = ctx.getServerHandler().playerEntity;

		thread.addScheduledTask(new Runnable()
		{
			@Override
			public void run()
			{
				if (!player.capabilities.isCreativeMode)
				{
					int currentHunger = player.getFoodStats().getFoodLevel();
					int hungerUsage = ConfigHandler.hungerUsage;

					if ((currentHunger - hungerUsage) >= hungerUsage)
					{
						player.getFoodStats().setFoodLevel(currentHunger - hungerUsage);
						PacketHandler.sendToAll(new PacketParticles(player.posX, player.posY, player.posZ, message.targetX, message.targetY, message.targetZ));
						player.setPositionAndUpdate(message.targetX, message.targetY, message.targetZ);
						player.worldObj.playSoundEffect(message.targetX, message.targetY, message.targetZ, "mob.endermen.portal", 1.0F, 1.0F);
					}
					else if (currentHunger == hungerUsage)
					{
						player.getFoodStats().setFoodLevel(1);
						PacketHandler.sendToAll(new PacketParticles(player.posX, player.posY, player.posZ, message.targetX, message.targetY, message.targetZ));
						player.setPositionAndUpdate(message.targetX, message.targetY, message.targetZ);
						player.worldObj.playSoundEffect(message.targetX, message.targetY, message.targetZ, "mob.endermen.portal", 1.0F, 1.0F);
					}
					else
					{
						player.worldObj.playSoundEffect(message.targetX, message.targetY, message.targetZ, "mob.irongolem.hit", 1.0F, 1.0F);
					}
				}
				else
				{
					PacketHandler.sendToAll(new PacketParticles(player.posX, player.posY, player.posZ, message.targetX, message.targetY, message.targetZ));
					player.setPositionAndUpdate(message.targetX, message.targetY, message.targetZ);
					player.worldObj.playSoundEffect(message.targetX, message.targetY, message.targetZ, "mob.endermen.portal", 1.0F, 1.0F);
				}
			}
		});

		return null;
	}
	

}
