package com.thevoxelbox.voxelsniper;

import java.util.List;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * Configuration storage defining global configurations for VoxelSniper.
 */
public class VoxelSniperConfig {

	private static final String UNDO_CACHE_SIZE = "undo-cache-size";
	private static final String MESSAGE_ON_LOGIN_ENABLED = "message-on-login-enabled";
	private static final String LITESNIPER_MAX_BRUSH_SIZE = "litesniper-max-brush-size";
	private static final String LITESNIPER_RESTRICTED_ITEMS = "litesniper-restricted-items";
	private static final int DEFAULT_UNDO_CACHE_SIZE = 20;
	private static final boolean DEFAULT_MESSAGE_ON_LOGIN_ENABLED = false;
	private static final int DEFAULT_LITESNIPER_MAX_BRUSH_SIZE = 5;

	private FileConfiguration config;

	/**
	 * @param config Configuration that is going to be used.
	 */
	public VoxelSniperConfig(FileConfiguration config) {
		this.config = config;
	}

	/**
	 * Returns the maximum amount of snipes stored in the undo cache of snipers.
	 *
	 * @return the maximum amount of snipes stored in the undo cache of snipers
	 */
	public int getUndoCacheSize() {
		return this.config.getInt(UNDO_CACHE_SIZE, DEFAULT_UNDO_CACHE_SIZE);
	}

	/**
	 * Set the maximum amount of snipes stored in the undo cache of snipers.
	 *
	 * @param size size of undo cache
	 */
	public void setUndoCacheSize(int size) {
		this.config.set(UNDO_CACHE_SIZE, size);
	}

	/**
	 * Returns if the login message is enabled.
	 *
	 * @return true if message on login is enabled, false otherwise.
	 */
	public boolean isMessageOnLoginEnabled() {
		return this.config.getBoolean(MESSAGE_ON_LOGIN_ENABLED, DEFAULT_MESSAGE_ON_LOGIN_ENABLED);
	}

	/**
	 * Set the message on login to be enabled or disabled.
	 *
	 * @param enabled Message on Login enabled
	 */
	public void setMessageOnLoginEnabled(boolean enabled) {
		this.config.set(MESSAGE_ON_LOGIN_ENABLED, enabled);
	}

	/**
	 * Returns maximum size of brushes that LiteSnipers can use.
	 *
	 * @return maximum size
	 */
	public int getLiteSniperMaxBrushSize() {
		return this.config.getInt(LITESNIPER_MAX_BRUSH_SIZE, DEFAULT_LITESNIPER_MAX_BRUSH_SIZE);
	}

	/**
	 * Set maximum size of brushes that LiteSnipers can use.
	 *
	 * @param size maximum size
	 */
	public void setLiteSniperMaxBrushSize(int size) {
		this.config.set(LITESNIPER_MAX_BRUSH_SIZE, size);
	}

	/**
	 * Returns List of restricted Litesniper Items.
	 *
	 * @return List of restricted Litesniper Items
	 */
	public List<String> getLiteSniperRestrictedItems() {
		return this.config.getStringList(LITESNIPER_RESTRICTED_ITEMS);
	}

	/**
	 * Set new list of restricted Litesniper Items.
	 *
	 * @param restrictedItems List of restricted Litesniper Items
	 */
	public void setLitesniperRestrictedItems(List<String> restrictedItems) {
		this.config.set(LITESNIPER_RESTRICTED_ITEMS, restrictedItems);
	}
}
