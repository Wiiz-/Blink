package wiiz.blink.common.network.core;

import java.io.File;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wiiz.blink.Blink;

public class ConfigHandler
{

	public static Configuration cfg;

	public static int blinkDistance = 20;
	public static int hungerUsage = 5;

	public static void init(File file)
	{
		if (cfg == null)
		{
			cfg = new Configuration(file);

			syncCfg();
		}
	}

	private static void syncCfg()
	{
		blinkDistance = cfg.getInt("blinkDistance", Configuration.CATEGORY_GENERAL, blinkDistance, 10, 40, "The distance in blocks the player can blink");
		hungerUsage = cfg.getInt("hungerUsage", Configuration.CATEGORY_GENERAL, hungerUsage, 3, 7, "The amount of hunger a single blink uses");
		
		if (cfg.hasChanged())
			cfg.save();
	}

	@SubscribeEvent
	public void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event)
	{
		if (event.modID.equalsIgnoreCase(Blink.MOD_ID))
		{
			syncCfg();
		}
	}
}
