package wiiz.blink.client;

import java.util.Random;

import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import wiiz.blink.client.event.ClientEventHandler;
import wiiz.blink.common.CommonProxy;

public class ClientProxy extends CommonProxy
{

	@Override
	public void registerEvents()
	{
		ClientRegistry.registerKeyBinding(ClientEventHandler.keyBlink);
		FMLCommonHandler.instance().bus().register(new ClientEventHandler());
	}

	public void spawnParticles(World world, double posX, double posY, double posZ, double targetX, double targetY, double targetZ)
	{
		short amount = 96;

		System.out.println("PosX: " + posX + " PosY: " + posY + " PosZ: " + posZ);
		System.out.println("TargetX: " + targetX + " TargetY: " + targetY + " TargetZ: " + targetZ);
		
        for (int i = 0; i < amount; ++i)
        {
            double d9 = (double)i / ((double)amount - 1.0D);
            float offX = (new Random().nextFloat() - 0.5F) * 0.2F;
            float offY = (new Random().nextFloat() - 0.5F) * 0.2F;
            float offZ = (new Random().nextFloat() - 0.5F) * 0.2F;
            double xPos = posX + (targetX - posX) * d9 + (new Random().nextDouble() - 0.5D) * (double)1.9 * 2.0D;
            double yPos = posY + (targetY - posY) * d9 + new Random().nextDouble() * (double)0.9;
            double zPos = posZ + (targetZ - posZ) * d9 + (new Random().nextDouble() - 0.5D) * (double)1.9 * 2.0D;
            world.spawnParticle(EnumParticleTypes.PORTAL, xPos, yPos, zPos, (double)offX, (double)offY, (double)offZ, new int[0]);
        }
	}

}
