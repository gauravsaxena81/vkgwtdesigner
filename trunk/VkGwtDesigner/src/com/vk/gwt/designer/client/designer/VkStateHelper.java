package com.vk.gwt.designer.client.designer;


public class VkStateHelper {
	private static VkStateHelper vkStateHelper = new VkStateHelper();
	private VkMenu vkMenu = VkMenu.getInstance();
	
	private boolean isDesignerMode = true;
	private boolean isLoadRunning = false;
	
	private VkEngine vkEngine = new VkEngine();
	
	private VkStateHelper(){}
	
	public static VkStateHelper getInstance(){
		return vkStateHelper;
	}
	public VkMenu getMenu() {
		return vkMenu;
	}
	/*public void setMenu(VkMenu vkMenu) {
		this.vkMenu = vkMenu;
	}*/
	public VkEngine getEngine() {
		return vkEngine;
	}
	public void setEngine(VkEngine newVkEngine) {
		this.vkEngine = newVkEngine;
	}
	
	public boolean isDesignerMode() {
		return isDesignerMode;
	}

	public void setDesignerMode(boolean isDesignerMode) {
		this.isDesignerMode = isDesignerMode;
	}

	public boolean isLoadRunning() {
		return isLoadRunning;
	}

	public void setLoadRunning(boolean isLoadRunning) {
		this.isLoadRunning = isLoadRunning;
	}
}
