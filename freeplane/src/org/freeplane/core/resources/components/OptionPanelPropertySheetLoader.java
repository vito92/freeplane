/*
 *  Freeplane - mind map editor
 *  Copyright (C) 2014 home
 *
 *  This file author is home
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.freeplane.core.resources.components;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.layout.StackPane;

import org.controlsfx.control.PropertySheet;
import org.controlsfx.control.PropertySheet.Item;
import org.controlsfx.control.PropertySheet.Mode;
import org.freeplane.core.util.TextUtils;

public final class OptionPanelPropertySheetLoader extends Task<Void> {
	private ArrayList<ArrayList<IPropertyControl>> controls;
	private StackPane stackPane;
	int count = 0;

	OptionPanelPropertySheetLoader(ArrayList<ArrayList<IPropertyControl>> controls, StackPane stackPane) {
		this.controls = controls;
		this.stackPane = stackPane;
	}

	@Override
	protected Void call() throws Exception {
		PropertySheet propertySheet = (PropertySheet) stackPane.getChildren().get(0);
		propertySheet.modeSwitcherVisibleProperty().setValue(false);
		propertySheet.setMode(Mode.CATEGORY);
		addPropertySheetItems(propertySheet);
		return null;
	}
	private void addPropertySheetItems(PropertySheet propertySheet) {
		ObservableList<Item> list = FXCollections.observableArrayList();
		for (ArrayList<IPropertyControl> tabGroup : controls) {
			String tabName = buildTabName(tabGroup);
			createTabPane(list, tabGroup, tabName);
		}
		propertySheet.getItems().setAll(list);
	}
	private String buildTabName(ArrayList<IPropertyControl> tabGroup) {
		TabProperty tab = (TabProperty) tabGroup.get(0);
		String tabName = TextUtils.getOptionalText(tab.getLabel());
		return tabName;
	}
	private void createTabPane(ObservableList<Item> list,
			ArrayList<IPropertyControl> tabGroup, String tabName) {
		for (IPropertyControl control : tabGroup) {
			addControlIfApplicable(list, tabName, control);
		}
	}
	private void addControlIfApplicable(ObservableList<Item> list,
			String tabName, IPropertyControl control) {
		// Implement adapter for number property, then remove this if block
		if (control instanceof NumberProperty) {
			return;
		}
		if (control instanceof PropertyBean) {
			addControl(list, tabName, control);
		}
	}
	private void addControl(ObservableList<Item> list, String tabName,
			IPropertyControl control) {
		PropertyBean propertyBean = (PropertyBean) control;
		PropertyBeanFXAdapter propertyBeanFX = new PropertyBeanFXAdapter(propertyBean, tabName);  
		list.add(propertyBeanFX);
	}

}