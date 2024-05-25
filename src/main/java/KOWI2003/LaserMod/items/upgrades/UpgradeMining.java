package KOWI2003.LaserMod.items.upgrades;

import KOWI2003.LaserMod.init.ModBlocks;
import KOWI2003.LaserMod.items.ItemUpgradeBase;
import KOWI2003.LaserMod.tileentities.ILaserAccess;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

public class UpgradeMining extends ItemUpgradeBase {

	public UpgradeMining(String name) {
		super(name);
		AbilityNames = new String[] {"Hammering"};
		abilityNameColor = new float[] {.8f,  0.8f, 0.8f};
		setCanBeUsedForLaser(true);
		setCanBeUsedForLaserTools(LaserTools.PICKAXE, LaserTools.AXE, LaserTools.SHOVEL);
	}
	
	@Override
	@SuppressWarnings("null")
	public void runLaserBlock(ILaserAccess te, BlockPos pos) {
		BlockState state = te.getTileEntity().getLevel().getBlockState(pos);
		BlockEntity tile = te.getTileEntity().getLevel().getBlockEntity(pos);
		if(state.getDestroySpeed(te.getTileEntity().getLevel(), pos) >= 0)
			if(state.getMaterial() == Material.STONE) {
				System.out.println(state.getMaterial());
				if(state.getBlock() == ModBlocks.LaserCatcher.get() || state.getBlock() == Blocks.BEDROCK || state.getBlock() == ModBlocks.Laser.get() || tile instanceof ILaserAccess)
					return;
				te.getTileEntity().getLevel().removeBlock(pos, true);
				Block.dropResources(state, te.getTileEntity().getLevel(), pos);
			}
	}
	
}
