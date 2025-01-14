package KOWI2003.LaserMod.gui.manual;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import KOWI2003.LaserMod.gui.manual.data.GuiContext;
import KOWI2003.LaserMod.gui.manual.pages.MainPage;
import KOWI2003.LaserMod.gui.manual.pages.blocks.AdvancedLaserPage;
import KOWI2003.LaserMod.gui.manual.pages.blocks.InfuserPage;
import KOWI2003.LaserMod.gui.manual.pages.blocks.LaserCatcherPage;
import KOWI2003.LaserMod.gui.manual.pages.blocks.LaserControllerPage;
import KOWI2003.LaserMod.gui.manual.pages.blocks.LaserControllerSubPage;
import KOWI2003.LaserMod.gui.manual.pages.blocks.LaserPage;
import KOWI2003.LaserMod.gui.manual.pages.blocks.LaserProjectorPage;
import KOWI2003.LaserMod.gui.manual.pages.blocks.LaserProjectorSubPage;
import KOWI2003.LaserMod.gui.manual.pages.blocks.MirrorPage;
import KOWI2003.LaserMod.gui.manual.pages.blocks.ModStationPage;
import KOWI2003.LaserMod.gui.manual.pages.blocks.PrecisionAssemblerPage;
import KOWI2003.LaserMod.gui.manual.pages.integration.CCIntegrationPage;
import KOWI2003.LaserMod.gui.manual.pages.integration.cctweaked.AdvancedLaserFunctions;
import KOWI2003.LaserMod.gui.manual.pages.integration.cctweaked.BaseFunctions;
import KOWI2003.LaserMod.gui.manual.pages.integration.cctweaked.LaserFunctions;
import KOWI2003.LaserMod.gui.manual.pages.integration.cctweaked.ProjectorFunctions;
import KOWI2003.LaserMod.gui.manual.pages.integration.cctweaked.base.FunctionPage;
import KOWI2003.LaserMod.gui.manual.pages.integration.cctweaked.base.setModeFunctionPage;
import KOWI2003.LaserMod.gui.manual.pages.integration.cctweaked.projector.WidgetFunction;
import KOWI2003.LaserMod.gui.manual.pages.integration.cctweaked.projector.WidgetHeader;
import KOWI2003.LaserMod.gui.manual.pages.integration.cctweaked.projector.setTemplateFunction;
import KOWI2003.LaserMod.gui.manual.pages.items.IRGlassesPage;
import KOWI2003.LaserMod.gui.manual.pages.items.LaserArmorPage;
import KOWI2003.LaserMod.gui.manual.pages.items.LaserCrystalPage;
import KOWI2003.LaserMod.gui.manual.pages.items.LaserToolsPage;
import KOWI2003.LaserMod.gui.manual.pages.items.LinkerPage;
import KOWI2003.LaserMod.gui.manual.pages.items.upgrades.UpgradeAirtightSealPage;
import KOWI2003.LaserMod.gui.manual.pages.items.upgrades.UpgradeCapacityPage;
import KOWI2003.LaserMod.gui.manual.pages.items.upgrades.UpgradeColorPage;
import KOWI2003.LaserMod.gui.manual.pages.items.upgrades.UpgradeDamagePage;
import KOWI2003.LaserMod.gui.manual.pages.items.upgrades.UpgradeDistancePage;
import KOWI2003.LaserMod.gui.manual.pages.items.upgrades.UpgradeFirePage;
import KOWI2003.LaserMod.gui.manual.pages.items.upgrades.UpgradeMiningPage;
import KOWI2003.LaserMod.gui.manual.pages.items.upgrades.UpgradeModePage;
import KOWI2003.LaserMod.gui.manual.pages.items.upgrades.UpgradeNoDamagePage;
import KOWI2003.LaserMod.gui.manual.pages.items.upgrades.UpgradePullPage;
import KOWI2003.LaserMod.gui.manual.pages.items.upgrades.UpgradePushPage;
import KOWI2003.LaserMod.gui.manual.pages.items.upgrades.UpgradeSilencePage;
import KOWI2003.LaserMod.gui.manual.pages.items.upgrades.UpgradeSpeedPage;
import KOWI2003.LaserMod.gui.manual.pages.subpages.BlocksPage;
import KOWI2003.LaserMod.gui.manual.pages.subpages.IntegrationPage;
import KOWI2003.LaserMod.gui.manual.pages.subpages.ItemsPage;
import KOWI2003.LaserMod.gui.manual.pages.subpages.UpgradesPage;

public class ManualHandler {

	static boolean isInitialized = false;
	
	static final Map<Integer, String> idMappings = new HashMap<>();
	static final Map<String, GuiContext> GUIs = new HashMap<>();
	
	public static GuiContext MAIN;
	
	//Header
	public static GuiContext BlocksHeader;
	
	//Blocks Content
	public static GuiContext Laser;
	public static GuiContext LaserCatcher;
	public static GuiContext Infuser;
	public static GuiContext ModStation;
	public static GuiContext LaserProjector;
	public static GuiContext LaserProjectorSub;
	public static GuiContext LaserController;
	public static GuiContext LaserControllerSub;
	public static GuiContext Mirror;
	public static GuiContext PrecisionAssembler;
	public static GuiContext AdvancedLaser;
	public static GuiContext LaserCrafter;
	
	
	//Header
	public static GuiContext ItemsHeader;
	
	//Items Content
	public static GuiContext LaserCrystal;
	public static GuiContext LaserTools;
	public static GuiContext LaserArmor;
	public static GuiContext IRGlasses;
	public static GuiContext Linker;
	
	//Header
	public static GuiContext UpgradesHeader;
	//Upgrades Content
	public static GuiContext UpgradeSpeed;
	public static GuiContext UpgradeMining; 
	public static GuiContext UpgradeFire; 
	public static GuiContext UpgradeColor;
	public static GuiContext UpgradeMode;
	public static GuiContext UpgradeDamage;
	public static GuiContext UpgradeSilence;
	public static GuiContext UpgradeNoDamage;
	public static GuiContext UpgradePush;
	public static GuiContext UpgradePull;
	public static GuiContext UpgradeDistance;
	public static GuiContext UpgradeAirtightSeal;
	public static GuiContext UpgradeCapacity;
	
	
	//Header
	public static GuiContext IntegrationHeader;
	//Integration Headers
	public static GuiContext CCIntegrationHeader;
		public static GuiContext BaseFunctionsHeader;
			public static GuiContext connectFunction;
			public static GuiContext disconnectFunction;
			public static GuiContext isConnectedFunction;
			public static GuiContext getDeviceTypeFunction;
			public static GuiContext getDeviceNameFunction;
			public static GuiContext setActiveFunction;

		public static GuiContext LaserFunctionsHeader;
			public static GuiContext setColorFunction;
			public static GuiContext setModeFunction;
			public static GuiContext canBeColoredFunction;
		
		public static GuiContext AdvancedLaserFunctionsHeader;
			public static GuiContext setDirectionFunction;

		public static GuiContext ProjectorFunctionsHeader;
			public static GuiContext setTemplateFunction;
			public static GuiContext getWidgetsFunction;
			public static GuiContext getWidgetsOfTypeFunction;
			public static GuiContext createWidgetFunction;
			public static GuiContext removeWidgetFunction;
			
			public static GuiContext WidgetsHeader;
				public static GuiContext getXFunction;
				public static GuiContext getYFunction;
				public static GuiContext getZFunction;
				public static GuiContext getWidthFunction;
				public static GuiContext getHeightFunction;
				public static GuiContext getDepthFunction;
				public static GuiContext getRotationFunction;
				public static GuiContext getScaleFunction;
				public static GuiContext getAlphaFunction;
				public static GuiContext getTypeFunction;
				public static GuiContext getIdFunction;
				public static GuiContext setXFunction;
				public static GuiContext setYFunction;
				public static GuiContext setZFunction;
				public static GuiContext setWidthFunction;
				public static GuiContext setHeightFunction;
				public static GuiContext setDepthFunction;
				public static GuiContext setRotationFunction;
				public static GuiContext setScaleFunction;
				public static GuiContext setAlphaFunction;
	
	public static void initContext() {
		GUIs.clear();
		idMappings.clear();
		pages = 1;
		
		MAIN = registerGuiContext(new MainPage("main"), 0);

		BlocksHeader = registerGuiContext(new BlocksPage("blocks_header"));
		Laser = registerGuiContext(new LaserPage("laser"));
		LaserCatcher = registerGuiContext(new LaserCatcherPage("laser_catcher"));
		Infuser = registerGuiContext(new InfuserPage("infuser"));
		ModStation = registerGuiContext(new ModStationPage("mod_station"));
		LaserProjector = registerGuiContext(new LaserProjectorPage("laser_project"));
		LaserProjectorSub = registerGuiContext(new LaserProjectorSubPage("laser_project_sub"));
		LaserController = registerGuiContext(new LaserControllerPage("laser_controller"));
		LaserControllerSub = registerGuiContext(new LaserControllerSubPage("laser_controller_sub"));
		Mirror = registerGuiContext(new MirrorPage("mirror"));
		PrecisionAssembler = registerGuiContext(new PrecisionAssemblerPage("precision_assembler"));
		AdvancedLaser = registerGuiContext(new AdvancedLaserPage("advanced_laser"));
//		LaserCrafter = registerGuiContext(new LaserCrafterPage("laser_crafter"));
		
		ItemsHeader = registerGuiContext(new ItemsPage("items_header"));
		
		LaserCrystal = registerGuiContext(new LaserCrystalPage("laser_crystal"));
		LaserTools = registerGuiContext(new LaserToolsPage("laser_tools"));
		LaserArmor = registerGuiContext(new LaserArmorPage("laser_armor"));
		IRGlasses = registerGuiContext(new IRGlassesPage("ir_glasses"));
		Linker = registerGuiContext(new LinkerPage("linker"));
		
		UpgradesHeader = registerGuiContext(new UpgradesPage("upgrades_header"));
		
		UpgradeSpeed = registerGuiContext(new UpgradeSpeedPage("upgrade_speed"));
		UpgradeMining = registerGuiContext(new UpgradeMiningPage("upgrade_mining"));
		UpgradeFire = registerGuiContext(new UpgradeFirePage("upgrade_fire"));
		UpgradeColor = registerGuiContext(new UpgradeColorPage("upgrade_color"));
		UpgradeMode = registerGuiContext(new UpgradeModePage("upgrade_mode"));
		UpgradeDamage = registerGuiContext(new UpgradeDamagePage("upgrade_damage"));
		UpgradeSilence = registerGuiContext(new UpgradeSilencePage("upgrade_silence"));
		UpgradeNoDamage = registerGuiContext(new UpgradeNoDamagePage("upgrade_no_damage"));
		UpgradePush = registerGuiContext(new UpgradePushPage("upgrade_push"));
		UpgradePull = registerGuiContext(new UpgradePullPage("upgrade_pull"));
		UpgradeDistance = registerGuiContext(new UpgradeDistancePage("upgrade_distance"));
		UpgradeAirtightSeal = registerGuiContext(new UpgradeAirtightSealPage("upgrade_airtight_seal"));
		UpgradeCapacity = registerGuiContext(new UpgradeCapacityPage("upgrade_capacity"));
		
		IntegrationHeader = registerGuiContext(new IntegrationPage("integration_header"));
		CCIntegrationHeader = registerGuiContext(new CCIntegrationPage("cc_integration_header"));
		BaseFunctionsHeader = registerGuiContext(new BaseFunctions("integration.cc.base"));
		connectFunction = registerGuiContext(new FunctionPage("connect", BaseFunctionsHeader));
		disconnectFunction = registerGuiContext(new FunctionPage("disconnect", BaseFunctionsHeader));
		isConnectedFunction = registerGuiContext(new FunctionPage("isConnected", BaseFunctionsHeader));
		getDeviceTypeFunction = registerGuiContext(new FunctionPage("getDeviceType", BaseFunctionsHeader));
		getDeviceNameFunction = registerGuiContext(new FunctionPage("getDeviceName", BaseFunctionsHeader));
		setActiveFunction = registerGuiContext(new FunctionPage("setActive", BaseFunctionsHeader));

		LaserFunctionsHeader = registerGuiContext(new LaserFunctions("integration.cc.laser"));
		setColorFunction = registerGuiContext(new FunctionPage("setColor", LaserFunctionsHeader));
		setModeFunction = registerGuiContext(new setModeFunctionPage("setMode", LaserFunctionsHeader));
		canBeColoredFunction = registerGuiContext(new FunctionPage("canBeColored", LaserFunctionsHeader));

		AdvancedLaserFunctionsHeader = registerGuiContext(new AdvancedLaserFunctions("integration.cc.advanced_laser"));
		setDirectionFunction = registerGuiContext(new FunctionPage("setDirection", AdvancedLaserFunctionsHeader));
		
		ProjectorFunctionsHeader = registerGuiContext(new ProjectorFunctions("integration.cc.projector"));
		setTemplateFunction = registerGuiContext(new setTemplateFunction("setTemplate", ProjectorFunctionsHeader));
		getWidgetsFunction = registerGuiContext(new WidgetFunction("getWidgets", ProjectorFunctionsHeader));
		getWidgetsOfTypeFunction = registerGuiContext(new WidgetFunction("getWidgetsOfType", ProjectorFunctionsHeader));
		createWidgetFunction = registerGuiContext(new WidgetFunction("createWidget", ProjectorFunctionsHeader));
		removeWidgetFunction = registerGuiContext(new WidgetFunction("removeWidget", ProjectorFunctionsHeader));

		WidgetsHeader = registerGuiContext(new WidgetHeader("integration.cc.widget"));
		getXFunction = registerGuiContext(new FunctionPage("getX", WidgetsHeader));
		getYFunction = registerGuiContext(new FunctionPage("getY", WidgetsHeader));
		getZFunction = registerGuiContext(new FunctionPage("getZ", WidgetsHeader));
		getWidgetsFunction = registerGuiContext(new FunctionPage("getWidth", WidgetsHeader));
		getHeightFunction = registerGuiContext(new FunctionPage("getHeight", WidgetsHeader));
		getDepthFunction = registerGuiContext(new FunctionPage("getDepth", WidgetsHeader));
		getRotationFunction = registerGuiContext(new FunctionPage("getRotation", WidgetsHeader));
		getScaleFunction = registerGuiContext(new FunctionPage("getScale", WidgetsHeader));
		getAlphaFunction = registerGuiContext(new FunctionPage("getAlpha", WidgetsHeader));
		getTypeFunction = registerGuiContext(new FunctionPage("getType", WidgetsHeader));
		getIdFunction = registerGuiContext(new FunctionPage("getId", WidgetsHeader));
		setXFunction = registerGuiContext(new FunctionPage("setX", WidgetsHeader));
		setYFunction = registerGuiContext(new FunctionPage("setY", WidgetsHeader));
		setZFunction = registerGuiContext(new FunctionPage("setZ", WidgetsHeader));
		setWidthFunction = registerGuiContext(new FunctionPage("setWidth", WidgetsHeader));
		setHeightFunction = registerGuiContext(new FunctionPage("setHeight", WidgetsHeader));
		setDepthFunction = registerGuiContext(new FunctionPage("setDepth", WidgetsHeader));
		setRotationFunction = registerGuiContext(new FunctionPage("setRotation", WidgetsHeader));
		setScaleFunction = registerGuiContext(new FunctionPage("setScale", WidgetsHeader));
		setAlphaFunction = registerGuiContext(new FunctionPage("setAlpha", WidgetsHeader));

		for (int i = pages; i >= 0; i--) {
			if(idMappings.containsKey(i))
				getGui(idMappings.get(i)).init();
		}
		
		isInitialized = true;
	}
	
	private static int pages = 1;
	
	private static GuiContext registerGuiContext(GuiContext gui) {
		gui.page = pages;
		idMappings.put(gui.page, gui.getId());
		GUIs.put(gui.getId(), gui);
		pages++;
		return gui;
	}
	
	private static GuiContext registerGuiContext(GuiContext gui, int page) {
		idMappings.put(0, gui.getId());
		GUIs.put(gui.getId(), gui);
		return gui;
	}
	
	public static GuiContext getGui(String id) {
		return GUIs.get(id);
	}
	
	public static Collection<GuiContext> getAllGuis() {
		return GUIs.values();
	}
	
	public static boolean hasGuiId(String id) {
		return GUIs.containsKey(id);
	}
	
	public static GuiContext getByPage(int page) {
		if(page < pages && page >= 0) {
			return GUIs.get(idMappings.get(page));
		}
		return null;
	}
	
	public static boolean hasNext(int page) {
		return page < pages-1;
	}
	
	public static boolean hasPrev(int page) {
		return page > 0;
	}
	
	public static boolean isInitialized() {
		return isInitialized;
	}
	
}
