package com.thevoxelbox.voxelsniper.brush.type;

import java.util.HashSet;
import java.util.Set;
import com.thevoxelbox.voxelsniper.Messages;
import com.thevoxelbox.voxelsniper.sniper.snipe.SnipeData;
import com.thevoxelbox.voxelsniper.sniper.Sniper;
import com.thevoxelbox.voxelsniper.sniper.Undo;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.util.NumberConversions;
import org.bukkit.util.Vector;

/**
 * http://www.voxelwiki.com/minecraft/Voxelsniper#Dome_Brush
 *
 * @author Gavjenks
 * @author MikeMatrix
 */
public class DomeBrush extends AbstractBrush {

	public DomeBrush() {
		super("Dome");
	}

	@Override
	public final void info(Messages messages) {
		messages.brushName(this.getName());
		messages.size();
		messages.blockDataType();
		messages.height();
	}

	private void generateDome(SnipeData snipeData, Block targetBlock) {
		if (snipeData.getVoxelHeight() == 0) {
			snipeData.sendMessage("VoxelHeight must not be 0.");
			return;
		}
		int absoluteHeight = Math.abs(snipeData.getVoxelHeight());
		boolean negative = snipeData.getVoxelHeight() < 0;
		Set<Vector> changeablePositions = new HashSet<>();
		Undo undo = new Undo();
		int brushSizeTimesVoxelHeight = snipeData.getBrushSize() * absoluteHeight;
		double stepScale = (snipeData.getBrushSize() * snipeData.getBrushSize() + brushSizeTimesVoxelHeight + brushSizeTimesVoxelHeight) / 5.0;
		double stepSize = 1.0 / stepScale;
		for (double u = 0; u <= Math.PI / 2; u += stepSize) {
			double y = absoluteHeight * Math.sin(u);
			for (double stepV = -Math.PI; stepV <= -(Math.PI / 2); stepV += stepSize) {
				double x = snipeData.getBrushSize() * Math.cos(u) * Math.cos(stepV);
				double z = snipeData.getBrushSize() * Math.cos(u) * Math.sin(stepV);
				double targetBlockX = targetBlock.getX() + 0.5;
				double targetBlockZ = targetBlock.getZ() + 0.5;
				int targetY = NumberConversions.floor(targetBlock.getY() + (negative ? -y : y));
				int currentBlockXAdd = NumberConversions.floor(targetBlockX + x);
				int currentBlockZAdd = NumberConversions.floor(targetBlockZ + z);
				int currentBlockXSubtract = NumberConversions.floor(targetBlockX - x);
				int currentBlockZSubtract = NumberConversions.floor(targetBlockZ - z);
				changeablePositions.add(new Vector(currentBlockXAdd, targetY, currentBlockZAdd));
				changeablePositions.add(new Vector(currentBlockXSubtract, targetY, currentBlockZAdd));
				changeablePositions.add(new Vector(currentBlockXAdd, targetY, currentBlockZSubtract));
				changeablePositions.add(new Vector(currentBlockXSubtract, targetY, currentBlockZSubtract));
			}
		}
		World world = getWorld();
		for (Vector vector : changeablePositions) {
			Location location = vector.toLocation(world);
			Block currentTargetBlock = location.getBlock();
			BlockData currentTargetBlockBlockData = currentTargetBlock.getBlockData();
			BlockData snipeBlockData = snipeData.getBlockData();
			if (!currentTargetBlockBlockData.equals(snipeBlockData)) {
				undo.put(currentTargetBlock);
				currentTargetBlock.setBlockData(snipeBlockData);
			}
		}
		Sniper owner = snipeData.getOwner();
		owner.storeUndo(undo);
	}

	@Override
	public final void arrow(SnipeData snipeData) {
		this.generateDome(snipeData, this.getTargetBlock());
	}

	@Override
	public final void powder(SnipeData snipeData) {
		Block lastBlock = this.getLastBlock();
		if (lastBlock == null) {
			return;
		}
		this.generateDome(snipeData, lastBlock);
	}

	@Override
	public String getPermissionNode() {
		return "voxelsniper.brush.dome";
	}
}