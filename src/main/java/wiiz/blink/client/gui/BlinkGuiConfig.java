package wiiz.blink.client.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.GuiConfig;
import wiiz.blink.Blink;
import wiiz.blink.common.network.core.ConfigHandler;

public class BlinkGuiConfig extends GuiConfig
{

	 public BlinkGuiConfig(GuiScreen guiScreen)
	    {
	        super(guiScreen,
	                new ConfigElement(ConfigHandler.cfg.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(),
	                Blink.MOD_ID,
	                false,
	                false,
	                GuiConfig.getAbridgedConfigPath(ConfigHandler.cfg.toString()));
	    }
	
}
