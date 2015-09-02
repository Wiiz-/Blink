package wiiz.blink.client.event;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.MouseInputEvent;
import wiiz.blink.common.network.PacketHandler;
import wiiz.blink.common.network.core.ConfigHandler;
import wiiz.blink.common.network.packet.PacketBlink;

public class ClientEventHandler
{

	public static KeyBinding keyBlink = new KeyBinding("key.blink.desc", Keyboard.KEY_B, "key.blink.cat");

	private Minecraft mc = Minecraft.getMinecraft();

	private boolean canBlink = false;
	private boolean buttonRelease = true;

	@SubscribeEvent
	public void onKeyPress(KeyInputEvent event)
	{
		if (keyBlink.isKeyDown())
		{
			if (!canBlink)
				canBlink = true;
		}
		else
		{
			if (canBlink)
				canBlink = false;
		}
	}

	@SubscribeEvent
	public void onMouseClick(MouseInputEvent event)
	{
		if (Mouse.isButtonDown(0))
		{
			if (buttonRelease)
				if (canBlink)
					blinkPlayer();

			buttonRelease = false;
		}
		else
		{
			buttonRelease = true;
		}
	}

	private void blinkPlayer()
	{
		MovingObjectPosition mop = mc.thePlayer.rayTrace(ConfigHandler.blinkDistance, 1.0F);

		if (mop != null)
		{
			if (mop.typeOfHit == MovingObjectType.BLOCK)
			{
				BlockPos pos = mop.getBlockPos();

				double posX = mc.thePlayer.posX;
				double posY = mc.thePlayer.posX;
				double posZ = mc.thePlayer.posX;
				double targetX = pos.getX();
				double targetY = pos.getY();
				double targetZ = pos.getZ();

				System.out.println("PosX: " + posX + " PosY: " + posY + " PosZ: " + posZ);
				System.out.println("TargetX: " + targetX + " TargetY: " + targetY + " TargetZ: " + targetZ);
				PacketHandler.sendToServer(new PacketBlink(pos.getX(), pos.getY() + 1, pos.getZ()));
			}
		}
	}

}
