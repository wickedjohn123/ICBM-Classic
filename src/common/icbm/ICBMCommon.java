package icbm;

import icbm.jiqi.TCiGuiPao;
import icbm.jiqi.TDianCiQi;
import icbm.jiqi.TFaSheDi;
import icbm.jiqi.TFaSheJia;
import icbm.jiqi.TFaSheShiMuo;
import icbm.jiqi.TLeiDaTai;
import icbm.jiqi.TXiaoFaSheQi;
import icbm.rongqi.CCiGuiPao;
import icbm.rongqi.CFaShiDi;
import icbm.rongqi.CXiaoFaSheQi;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.registry.GameRegistry;

public class ICBMCommon implements IGuiHandler
{
	//GUI IDs
	public static final int GUI_RAIL_GUN = 0;
	public static final int GUI_CRUISE_LAUNCHER = 1;
	public static final int GUI_LAUNCHER_SCREEN = 2;
	public static final int GUI_RADAR_STATION = 3;
	public static final int GUI_DETECTOR = 4;
	public static final int GUI_FREQUENCY = 5;
	public static final int GUI_EMP_TOWER = 6;
	public static final int GUI_LAUNCHER_BASE = 7;
	public static final int GUI_LASER_TURRET = 8;

	public void preInit() { }
	
	public void init()
	{
		GameRegistry.registerTileEntity(TCiGuiPao.class, "ICBMRailgun");
		GameRegistry.registerTileEntity(TXiaoFaSheQi.class, "ICBMCruiseLauncher");
		GameRegistry.registerTileEntity(TFaSheDi.class, "ICBMLauncherBase");
		GameRegistry.registerTileEntity(TFaSheShiMuo.class, "ICBMLauncherScreen");
		GameRegistry.registerTileEntity(TFaSheJia.class, "ICBMTileEntityLauncherFrame");
		GameRegistry.registerTileEntity(TLeiDaTai.class, "ICBMRadar");
		GameRegistry.registerTileEntity(TDianCiQi.class, "ICBMEMPTower");
	}
	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		return null;
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) 
	{
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		
		if (tileEntity != null)
        {
			switch(ID)
			{
				case ICBMCommon.GUI_RAIL_GUN: return new CCiGuiPao(player.inventory, (TCiGuiPao) tileEntity);
				case ICBMCommon.GUI_CRUISE_LAUNCHER: return new CXiaoFaSheQi(player.inventory, (TXiaoFaSheQi) tileEntity);
				case ICBMCommon.GUI_LAUNCHER_BASE: return new CFaShiDi(player.inventory, (TFaSheDi) tileEntity);
			}
        }
		
		return null;
	}
}