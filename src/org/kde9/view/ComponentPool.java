package org.kde9.view;

import org.kde9.view.component.brower.BrowerComponent;
import org.kde9.view.component.brower.GroupComponent;
import org.kde9.view.component.brower.NameComponent;
import org.kde9.view.component.brower.ViewerComponent;
import org.kde9.view.component.toolbar.ToolbarComponent;

public class ComponentPool {
	static Component component;
	
	static GroupComponent groupComponent;
	
	static NameComponent nameComponent;
	
	static ViewerComponent viewerComponent;
	
	static BrowerComponent browerComponent;
	
	/**
	 * @return the browerComponent
	 */
	public static BrowerComponent getBrowerComponent() {
		return browerComponent;
	}

	/**
	 * @param browerComponent the browerComponent to set
	 */
	public static void setBrowerComponent(BrowerComponent browerComponent) {
		ComponentPool.browerComponent = browerComponent;
	}

	/**
	 * @return the toolbarComponent
	 */
	public static ToolbarComponent getToolbarComponent() {
		return toolbarComponent;
	}

	/**
	 * @param toolbarComponent the toolbarComponent to set
	 */
	public static void setToolbarComponent(ToolbarComponent toolbarComponent) {
		ComponentPool.toolbarComponent = toolbarComponent;
	}

	static ToolbarComponent toolbarComponent;

	/**
	 * @return the component
	 */
	public static Component getComponent() {
		return component;
	}

	/**
	 * @param component the component to set
	 */
	public static void setComponent(Component component) {
		ComponentPool.component = component;
	}

	/**
	 * @return the groupComponent
	 */
	public static GroupComponent getGroupComponent() {
		return groupComponent;
	}

	/**
	 * @param groupComponent the groupComponent to set
	 */
	public static void setGroupComponent(GroupComponent groupComponent) {
		ComponentPool.groupComponent = groupComponent;
	}

	/**
	 * @return the nameComponent
	 */
	public static NameComponent getNameComponent() {
		return nameComponent;
	}

	/**
	 * @param nameComponent the nameComponent to set
	 */
	public static void setNameComponent(NameComponent nameComponent) {
		ComponentPool.nameComponent = nameComponent;
	}

	/**
	 * @return the viewerComponent
	 */
	public static ViewerComponent getViewerComponent() {
		return viewerComponent;
	}

	/**
	 * @param viewerComponent the viewerComponent to set
	 */
	public static void setViewerComponent(ViewerComponent viewerComponent) {
		ComponentPool.viewerComponent = viewerComponent;
	}
	
	
}
