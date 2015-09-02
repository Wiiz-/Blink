package wiiz.blink;

import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import wiiz.blink.common.CommonProxy;
import wiiz.blink.common.network.PacketHandler;
import wiiz.blink.common.network.core.ConfigHandler;

@Mod(modid = Blink.MOD_ID, name = Blink.MOD_NAME, version = Blink.MOD_VER, guiFactory = Blink.GUI_FACTORY)
public class Blink
{

	public static final String MOD_NAME = "Blink";
	public static final String MOD_ID = "blink";
	public static final String MOD_VER = "1.0";

	public static final String COMMON_PROXY = "wiiz.blink.common.CommonProxy";
	public static final String CLIENT_PROXY = "wiiz.blink.client.ClientProxy";

	public static final String GUI_FACTORY = "wiiz.blink.client.gui.BlinkGuiFactory";

	@Instance(MOD_ID)
	public static Blink instance;

	@SidedProxy(serverSide = COMMON_PROXY, clientSide = CLIENT_PROXY)
	public static CommonProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		ConfigHandler.init(event.getSuggestedConfigurationFile());
		FMLCommonHandler.instance().bus().register(new ConfigHandler());
		
		proxy.registerEvents();
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		PacketHandler.init();
	}
}
