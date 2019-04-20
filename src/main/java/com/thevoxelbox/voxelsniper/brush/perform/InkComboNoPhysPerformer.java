package com.thevoxelbox.voxelsniper.brush.perform;

import com.thevoxelbox.voxelsniper.Message;
import org.bukkit.block.Block;

/**
 * @author Voxel
 */
public class InkComboNoPhysPerformer extends AbstractPerformer {

	private byte d;
	private byte dr;
	private int ir;

	public InkComboNoPhysPerformer() {
		super("Ink-Combo, No Physics");
	}

	@Override
	public void init(com.thevoxelbox.voxelsniper.SnipeData snipeData) {
		this.world = snipeData.getWorld();
		this.d = snipeData.getData();
		this.dr = snipeData.getReplaceData();
		this.ir = snipeData.getReplaceId();
	}

	@Override
	public void info(Message message) {
		message.performerName(this.getName());
		message.replaceBlockDataType();
		message.blockData();
		message.replaceBlockData();
	}

	@Override
	public void perform(Block block) {
		if (block.getTypeId() == this.ir && block.getData() == this.dr) {
			this.undo.put(block);
			block.setData(this.d, false);
		}
	}

	@Override
	public boolean isUsingReplaceMaterial() {
		return true;
	}
}