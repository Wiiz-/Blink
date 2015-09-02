package wiiz.blink.common.network;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import wiiz.blink.Blink;
import wiiz.blink.common.network.packet.PacketBlink;
import wiiz.blink.common.network.packet.PacketParticles;

public class PacketHandler
{

	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Blink.MOD_ID);

	public static void init()
	{
		int discriminator = -1;
		INSTANCE.registerMessage(PacketBlink.class, PacketBlink.class, discriminator++, Side.SERVER);
		INSTANCE.registerMessage(PacketParticles.class, PacketParticles.class, discriminator++, Side.CLIENT);
	}

	public static void sendToServer(IMessage message)
	{
		INSTANCE.sendToServer(message);
	}

	public static void sendToAll(IMessage message)
	{
		INSTANCE.sendToAll(message);
	}
}
