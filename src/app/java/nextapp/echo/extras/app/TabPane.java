/* 
 * This file is part of the Echo Extras Project.
 * Copyright (C) 2005-2007 NextApp, Inc.
 *
 * Version: MPL 1.1/GPL 2.0/LGPL 2.1
 *
 * The contents of this file are subject to the Mozilla Public License Version
 * 1.1 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 *
 * Alternatively, the contents of this file may be used under the terms of
 * either the GNU General Public License Version 2 or later (the "GPL"), or
 * the GNU Lesser General Public License Version 2.1 or later (the "LGPL"),
 * in which case the provisions of the GPL or the LGPL are applicable instead
 * of those above. If you wish to allow use of your version of this file only
 * under the terms of either the GPL or the LGPL, and not to allow others to
 * use your version of this file under the terms of the MPL, indicate your
 * decision by deleting the provisions above and replace them with the notice
 * and other provisions required by the GPL or the LGPL. If you do not delete
 * the provisions above, a recipient may use your version of this file under
 * the terms of any one of the MPL, the GPL or the LGPL.
 */

package nextapp.echo.extras.app;

import java.util.EventListener;

import nextapp.echo.app.Alignment;
import nextapp.echo.app.Border;
import nextapp.echo.app.Color;
import nextapp.echo.app.Component;
import nextapp.echo.app.Extent;
import nextapp.echo.app.FillImage;
import nextapp.echo.app.Font;
import nextapp.echo.app.ImageReference;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Pane;
import nextapp.echo.app.PaneContainer;

import nextapp.echo.extras.app.event.TabPaneEvent;
import nextapp.echo.extras.app.event.TabPaneListener;
import nextapp.echo.extras.app.layout.TabPaneLayoutData;

/**
 * A container pane which displays child components in separate tabs.
 */
public class TabPane extends Component 
implements Pane, PaneContainer {
    
    public static final String INPUT_TAB_INDEX = "inputTabIndex";
    public static final String INPUT_TAB_CLOSE = "inputTabClose";
    public static final String ACTIVE_TAB_INDEX_CHANGED_PROPERTY = "activeTabIndex";
    
    public static final String PROPERTY_BORDER_TYPE = "borderType";
    public static final String PROPERTY_DEFAULT_CONTENT_INSETS = "defaultContentInsets";
    public static final String PROPERTY_INSETS = "insets";
    public static final String PROPERTY_TAB_BACKGROUND_IMAGE = "tabBackgroundImage";
    public static final String PROPERTY_TAB_ACTIVE_LEFT_IMAGE = "tabActiveLeftImage";
    public static final String PROPERTY_TAB_ACTIVE_RIGHT_IMAGE = "tabActiveRightImage";
    public static final String PROPERTY_TAB_ACTIVE_BACKGROUND = "tabActiveBackground";
    public static final String PROPERTY_TAB_ACTIVE_BACKGROUND_IMAGE = "tabActiveBackgroundImage";
    public static final String PROPERTY_TAB_ACTIVE_BORDER = "tabActiveBorder";
    public static final String PROPERTY_TAB_ACTIVE_FONT = "tabActiveFont";
    public static final String PROPERTY_TAB_ACTIVE_FOREGROUND = "tabActiveForeground";
    public static final String PROPERTY_TAB_HEIGHT = "tabHeight";
    public static final String PROPERTY_TAB_ACTIVE_HEIGHT_INCREASE = "tabActiveHeightIncrease";
    public static final String PROPERTY_TAB_INACTIVE_LEFT_IMAGE = "tabInactiveLeftImage";
    public static final String PROPERTY_TAB_INACTIVE_RIGHT_IMAGE = "tabInactiveRightImage";
    public static final String PROPERTY_TAB_INACTIVE_BACKGROUND = "tabInactiveBackground";
    public static final String PROPERTY_TAB_INACTIVE_BACKGROUND_IMAGE = "tabInactiveBackgroundImage";
    public static final String PROPERTY_TAB_INACTIVE_BORDER = "tabInactiveBorder";
    public static final String PROPERTY_TAB_INACTIVE_FONT = "tabInactiveFont";
    public static final String PROPERTY_TAB_INACTIVE_FOREGROUND = "tabInactiveForeground";
    public static final String PROPERTY_TAB_ICON_TEXT_MARGIN = "tabIconTextMargin";
    public static final String PROPERTY_TAB_INSET = "tabInset";
    public static final String PROPERTY_TAB_SPACING = "tabSpacing";
    public static final String PROPERTY_TAB_POSITION = "tabPosition";
    public static final String PROPERTY_TAB_WIDTH = "tabWidth";
    public static final String PROPERTY_TAB_ALIGNMENT = "tabAlignment";
    
    public static final String PROPERTY_TAB_CLOSE_ENABLED = "tabCloseEnabled";
    public static final String PROPERTY_TAB_CLOSE_ICON_ROLLOVER_ENABLED = "tabCloseIconRolloverEnabled";
    
    public static final String PROPERTY_TAB_CLOSE_ICON = "tabCloseIcon";
    public static final String PROPERTY_TAB_DISABLED_CLOSE_ICON = "tabDisabledCloseIcon";
    public static final String PROPERTY_TAB_ROLLOVER_CLOSE_ICON = "tabRolloverCloseIcon";
    
    /**
     * Constant for the <code>borderType</code> property indicating that no 
     * border should be drawn around the content.
     */
    public static final int BORDER_TYPE_NONE = 0;

    /**
     * Constant for the <code>borderType</code> property indicating that a
     * border should be drawn immediately adjacent to the tabs only.
     * If the tabs are positioned at the top of the <code>TabPane</code> the
     * border will only be drawn directly beneath the tabs with this setting.  
     * If the tabs are positioned at the bottom of the <code>TabPane</code> the
     * border will only be drawn directly above the tabs with this setting.
     * This is the default rendering style.
     */
    public static final int BORDER_TYPE_ADJACENT_TO_TABS = 1;

    /**
     * Constant for the <code>borderType</code> property indicating that
     * borders should be drawn above and below the content, but not at its 
     * sides.
     */
    public static final int BORDER_TYPE_PARALLEL_TO_TABS = 2;
    
    /**
     * Constant for the <code>borderType</code> property indicating that
     * borders should be drawn on all sides of the content.
     */
    public static final int BORDER_TYPE_SURROUND = 3;
    
    /**
     * Constant for the <code>tabPosition</code> property indicating that the
     * tabs should be rendered beneath the content.
     */
    public static final int TAB_POSITION_BOTTOM = 1;
    
    /**
     * Constant for the <code>tabPosition</code> property indicating that the
     * tabs should be rendered above the content.
     * This is the default rendering style.
     */
    public static final int TAB_POSITION_TOP = 0;
    
    /**
     * Index of active tab.
     */ 
    private int activeTabIndex = -1;
    
    /**
     * Adds a <code>TabPaneListener</code> to receive event notifications.
     * 
     * @param l the <code>TabPaneListener</code> to add
     */
    public void addTabPaneListener(TabPaneListener l) {
        getEventListenerList().addListener(TabPaneListener.class, l);
    }
    
    /**
     * Removes a <code>TabPaneListener</code> from receiving event notifications.
     * 
     * @param l the <code>TabPaneListener</code> to remove
     */
    public void removeTabPaneListener(TabPaneListener l) {
        if (!hasEventListenerList()) {
            return;
        }
        getEventListenerList().removeListener(TabPaneListener.class, l);
    }

    /**
	 * Notifies <code>TabPaneListener</code>s that the user has requested to
	 * close a tab.
	 */
    protected void fireTabClosing(int tabIndex) {
        if (!hasEventListenerList()) {
            return;
        }
        EventListener[] listeners = getEventListenerList().getListeners(TabPaneListener.class);
        if (listeners.length == 0) {
            return;
        }
        TabPaneEvent e = new TabPaneEvent(this, tabIndex);
        for (int i = 0; i < listeners.length; ++i) {
            ((TabPaneListener) listeners[i]).tabClosing(e);
        }
    }
    
    /**
     * Notifies <code>TabPaneListener</code>s that the user has requested to
     * activate a tab.
     */
    protected void fireTabActivating(int tabIndex) {
    	if (!hasEventListenerList()) {
    		return;
    	}
    	EventListener[] listeners = getEventListenerList().getListeners(TabPaneListener.class);
    	if (listeners.length == 0) {
    		return;
    	}
    	TabPaneEvent e = new TabPaneEvent(this, tabIndex);
    	for (int i = 0; i < listeners.length; ++i) {
    		((TabPaneListener) listeners[i]).tabActivating(e);
    	}
    }
    
    /**
     * Returns the index of the active tab.
     * 
     * @return the active tab index
     */
    public int getActiveTabIndex() {
        return activeTabIndex;
    }
    
    /**
     * Returns the mode in which the border will be drawn around the 
     * <code>TabPane</code>.
     * 
     * @return the border border type, one of the following values:
     *         <ul>
     *          <li><code>BORDER_TYPE_NONE</code></li>
     *          <li><code>BORDER_TYPE_ADJACENT_TO_TABS</code> (the default)</li>
     *          <li><code>BORDER_TYPE_PARALLEL_TO_TABS</code></li>
     *          <li><code>BORDER_TYPE_SURROUND</code></li>
     *         </ul>
     */
    public int getBorderType() {
        Integer value = (Integer) getProperty(PROPERTY_BORDER_TYPE);
        if (value == null) {
            return BORDER_TYPE_ADJACENT_TO_TABS;
        } else {
            return value.intValue();
        }
    }

    /**
     * Returns the default content inset margin.  This margin is applied by
     * default to each child component.
     *
     * @return the default content inset margin
     */
    public Insets getDefaultContentInsets() {
        return (Insets) getProperty(PROPERTY_DEFAULT_CONTENT_INSETS);
    }
    
    /**
     * Returns the margin size between the icon and the text.
     * The margin will only be displayed if a tab has both icon
     * and text set.
     * This property only supports <code>Extent</code>s with
     * fixed (i.e., not percent) units.
     * 
     * @return the margin size 
     */
    public Extent getTabIconTextMargin() {
        return (Extent) getProperty(PROPERTY_TAB_ICON_TEXT_MARGIN);
    }
    
    /**
     * Returns the <code>Insets</code> around the entire <code>TabPane</code>.
     * Insets will only be drawn on sides of the <code>TabPane</code> which have
     * a border (i.e., based on the value of the <code>borderType</code> 
     * property).
     * Values for this property must be in pixel units.
     * 
     * @return the insets
     */
    public Insets getInsets() {
    	return (Insets) getProperty(PROPERTY_INSETS);
    }
    
    /**
     * Returns the left image used to render active tabs.
     * 
     * @return the active tab left image
     */
    public ImageReference getTabActiveLeftImage() {
    	return (ImageReference) getProperty(PROPERTY_TAB_ACTIVE_LEFT_IMAGE);
    }
    
    /**
     * Returns the right image used to render active tabs.
     * 
     * @return the active tab right image
     */
    public ImageReference getTabActiveRightImage() {
        return (ImageReference) getProperty(PROPERTY_TAB_ACTIVE_RIGHT_IMAGE);
    }
    
    /**
     * Returns the background color used to render active tabs.
     * 
     * @return the active tab background
     */
    public Color getTabActiveBackground() {
        return (Color) getProperty(PROPERTY_TAB_ACTIVE_BACKGROUND);
    }
    
    /**
     * Returns the background image used to render active tabs.
     * 
     * @return the active tab background image
     */
    public FillImage getTabActiveBackgroundImage() {
        return (FillImage) getProperty(PROPERTY_TAB_ACTIVE_BACKGROUND_IMAGE);
    }
    
    /**
     * Returns the <code>Border</code> used to draw the active tab and 
     * surround the content of the <code>TabPane</code>.
     * 
     * @return the border
     */
    public Border getTabActiveBorder() {
        return (Border) getProperty(PROPERTY_TAB_ACTIVE_BORDER);
    }
    
    /**
     * Returns the font used to render active tabs.
     * 
     * @return the active tab font
     */
    public Font getTabActiveFont() {
        return (Font) getProperty(PROPERTY_TAB_ACTIVE_FONT);
    }
    
    /**
     * Returns the foreground color used to render active tabs.
     * 
     * @return the active tab foreground
     */
    public Color getTabActiveForeground() {
        return (Color) getProperty(PROPERTY_TAB_ACTIVE_FOREGROUND);
    }
    
    /**
     * Returns the background image used to render behind tabs.
     * 
     * @return the background image
     */
    public FillImage getTabBackgroundImage() {
        return (FillImage) getProperty(PROPERTY_TAB_BACKGROUND_IMAGE);
    }

    /**
     * Returns the height of an individual tab.
     * <code>Extent</code> values for this property must be in pixel units.
     * 
     * @return the tab height
     */
    public Extent getTabHeight() {
        return (Extent) getProperty(PROPERTY_TAB_HEIGHT);
    }
    
    /**
     * Returns the height increase of active tabs.
     * <code>Extent</code> values for this property must be in pixel units.
     * 
     * @return the active tab height increase
     */
    public Extent getTabActiveHeightIncrease() {
    	return (Extent) getProperty(PROPERTY_TAB_ACTIVE_HEIGHT_INCREASE);
    }
    
    /**
     * Returns the left image used to render inactive tabs.
     * 
     * @return the inactive tab left image
     */
    public ImageReference getTabInactiveLeftImage() {
    	return (ImageReference) getProperty(PROPERTY_TAB_INACTIVE_LEFT_IMAGE);
    }
    
    /**
     * Returns the right image used to render inactive tabs.
     * 
     * @return the inactive tab right image
     */
    public ImageReference getTabInactiveRightImage() {
        return (ImageReference) getProperty(PROPERTY_TAB_INACTIVE_RIGHT_IMAGE);
    }
    
    /**
     * Returns the background color used to render inactive tabs.
     * 
     * @return the inactive tab background
     */
    public Color getTabInactiveBackground() {
        return (Color) getProperty(PROPERTY_TAB_INACTIVE_BACKGROUND);
    }
    
    /**
     * Returns the background image used to render inactive tabs.
     * 
     * @return the inactive tab background image
     */
    public FillImage getTabInactiveBackgroundImage() {
        return (FillImage) getProperty(PROPERTY_TAB_INACTIVE_BACKGROUND_IMAGE);
    }

    /**
     * Returns the <code>Border</code> used to draw inactive tabs.
     * 
     * @return the border
     */
    public Border getTabInactiveBorder() {
        return (Border) getProperty(PROPERTY_TAB_INACTIVE_BORDER);
    }
    
    /**
     * Returns the font used to render inactive tabs.
     * 
     * @return the inactive tab font
     */
    public Font getTabInactiveFont() {
        return (Font) getProperty(PROPERTY_TAB_INACTIVE_FONT);
    }
    
    /**
     * Returns the foreground color used to render inactive tabs.
     * 
     * @return the inactive tab foreground
     */
    public Color getTabInactiveForeground() {
        return (Color) getProperty(PROPERTY_TAB_INACTIVE_FOREGROUND);
    }
    
    /**
     * Returns the horizontal distance from which all tabs are inset from 
     * the edge of the <code>TabPane</code>.
     * 
     * @return the tab inset
     */
    public Extent getTabInset() {
        return (Extent) getProperty(PROPERTY_TAB_INSET);
    }
    
    /**
     * Returns the position where the tabs are located relative to the pane 
     * content.
     * 
     * @return the tab position, one of the following values:
     *         <ul>
     *          <li><code>TAB_POSITION_TOP</code></li>
     *          <li><code>TAB_POSITION_BOTTOM</code></li>
     *         </ul>
     */
    public int getTabPosition() {
        Integer tabPosition = (Integer) getProperty(PROPERTY_TAB_POSITION);
        return tabPosition == null ? TAB_POSITION_TOP : tabPosition.intValue();
    }
    
    /**
     * Returns the horizontal space between individual tabs.
     * 
     * @return the tab spacing
     */
    public Extent getTabSpacing() {
        return (Extent) getProperty(PROPERTY_TAB_SPACING);
    }
    
    /**
     * Returns the width of an individual tab.
     * 
     * @return the tab width
     */
    public Extent getTabWidth() {
        return (Extent) getProperty(PROPERTY_TAB_WIDTH);
    }
    
    /**
     * Returns the alignment within an individual tab.
     * 
     * @return the tab alignment.
     */
    public Alignment getTabAlignment() {
    	return (Alignment) getProperty(PROPERTY_TAB_ALIGNMENT);
    }
    
    /**
     * @see nextapp.echo.app.Component#isValidParent(nextapp.echo.app.Component)
     */
    public boolean isValidParent(Component c) {
        if (!super.isValidParent(c)) {
            return false;
        }
        // Ensure parent is a PaneContainer.
        return c instanceof PaneContainer;
    }
    
    /**
     * @see nextapp.echo.app.Component#processInput(java.lang.String, java.lang.Object)
     */
    public void processInput(String inputName, Object inputValue) {
        super.processInput(inputName, inputValue);
        if (INPUT_TAB_INDEX.equals(inputName)) {
            setActiveTabIndex(((Integer) inputValue).intValue());
        } else if (INPUT_TAB_CLOSE.equals(inputName)) {
        	remove(getVisibleComponent(((Integer)inputValue).intValue()));
        }
    }
    
    /**
     * Sets the active tab index.
     * 
     * @param newValue the index of the child <code>Component</code> whose tab
     *        should be displayed
     */
    public void setActiveTabIndex(int newValue) {
        int oldValue = activeTabIndex;
        activeTabIndex = newValue;
        // keep property change event for backwards compatibility
        firePropertyChange(ACTIVE_TAB_INDEX_CHANGED_PROPERTY, new Integer(oldValue), new Integer(newValue));
        fireTabActivating(newValue);
    }

    /**
     * @see nextapp.echo.app.Component#remove(nextapp.echo.app.Component)
     */
    public void remove(Component c) {
    	if (c.isVisible()) {
    		fireTabClosing(visibleIndexOf(c));
    	}
    	super.remove(c);
    }
    
    /**
     * Sets the mode in which the border will be drawn around the 
     * <code>TabPane</code>.
     * 
     * @param newValue the new border type one of the following values:
     *        <ul>
     *         <li><code>BORDER_TYPE_NONE</code></li>
     *         <li><code>BORDER_TYPE_ADJACENT_TO_TABS</code> (the default)</li>
     *         <li><code>BORDER_TYPE_PARALLEL_TO_TABS</code></li>
     *         <li><code>BORDER_TYPE_SURROUND</code></li>
     *        </ul>
     */
    public void setBorderType(int newValue) {
        setProperty(PROPERTY_BORDER_TYPE, new Integer(newValue));
    }

    /**
     * Sets the default content inset margin.  This margin is applied by 
     * default to each child component.
     *
     * @param newValue the new default content inset margin
     */
    public void setDefaultContentInsets(Insets newValue) {
        setProperty(PROPERTY_DEFAULT_CONTENT_INSETS, newValue);
    }

    /**
     * Sets the margin size between the icon and the text.
     * The margin will only be displayed if a tab has both icon
     * and text set.
     * This property only supports <code>Extent</code>s with
     * fixed (i.e., not percent) units.
     * 
     * @param newValue the margin size 
     */
    public void setTabIconTextMargin(Extent newValue) {
        setProperty(PROPERTY_TAB_ICON_TEXT_MARGIN, newValue);
    }

    /**
     * Sets the <code>Insets</code> around the entire <code>TabPane</code>.
     * Insets will only be drawn on sides of the <code>TabPane</code> which have
     * a border (i.e., based on the value of the <code>borderType</code> 
     * property).
     * Values for this property must be in pixel units.
     * 
     * @return newValue the new inset
     */
    public void setInsets(Insets newValue) {
    	setProperty(PROPERTY_INSETS, newValue);
    }
    
    /**
     * Sets the left image used to render active tabs. The width of the image
     * must be set, and has to be a pixel value.
     * 
     * @param newValue the active tab left image
     */
    public void setTabActiveLeftImage(ImageReference newValue) {
    	setProperty(PROPERTY_TAB_ACTIVE_LEFT_IMAGE, newValue);
    }
    
    /**
     * Sets the right image used to render active tabs. The width of the image
     * must be set, and has to be a pixel value.
     * 
     * @param newValue the active tab right image
     */
    public void setTabActiveRightImage(ImageReference newValue) {
        setProperty(PROPERTY_TAB_ACTIVE_RIGHT_IMAGE, newValue);
    }
    
    /**
     * Sets the background color used to render active tabs.
     * 
     * @param newValue the new active tab background
     */
    public void setTabActiveBackground(Color newValue) {
        setProperty(PROPERTY_TAB_ACTIVE_BACKGROUND, newValue);
    }
    
    /**
     * Sets the background image used to render active tabs.
     * 
     * @param newValue the new active tab background image
     */
    public void setTabActiveBackgroundImage(FillImage newValue) {
        setProperty(PROPERTY_TAB_ACTIVE_BACKGROUND_IMAGE, newValue);
    }
    
    /**
     * Sets the <code>Border</code> used to draw the active tab and 
     * surround the content of the <code>TabPane</code>.
     * 
     * @param newValue the new border
     */
    public void setTabActiveBorder(Border newValue) {
        setProperty(PROPERTY_TAB_ACTIVE_BORDER, newValue);
    }
    
    /**
     * Sets the font used to render active tabs.
     * 
     * @param newValue the new active tab font
     */
    public void setTabActiveFont(Font newValue) {
        setProperty(PROPERTY_TAB_ACTIVE_FONT, newValue);
    }
    
    /**
     * Sets the foreground color used to render active tabs.
     * 
     * @param newValue the new active tab foreground
     */
    public void setTabActiveForeground(Color newValue) {
        setProperty(PROPERTY_TAB_ACTIVE_FOREGROUND, newValue);
    }
    
    /**
     * Sets the background image used to render behind tabs.
     * 
     * @param newValue the background image
     */
    public void setTabBackgroundImage(FillImage newValue) {
       setProperty(PROPERTY_TAB_BACKGROUND_IMAGE, newValue);
    }
    
    /**
     * Sets the height of an individual tab.
     * <code>Extent</code> values for this property must be in pixel units.
     * 
     * @param newValue the new tab height
     */
    public void setTabHeight(Extent newValue) {
        setProperty(PROPERTY_TAB_HEIGHT, newValue);
    }
    
    /**
     * Sets the height increase of active tabs.
     * <code>Extent</code> values for this property must be in pixel units.
     * 
     * @param newValue the new active tab height increase
     */
    public void setTabActiveHeightIncrease(Extent newValue) {
    	setProperty(PROPERTY_TAB_ACTIVE_HEIGHT_INCREASE, newValue);
    }
    
    /**
     * Sets the left image used to render inactive tabs. The width of the image
     * must be set, and has to be a pixel value.
     * 
     * @param newValue the inactive tab left image
     */
    public void setTabInactiveLeftImage(ImageReference newValue) {
    	setProperty(PROPERTY_TAB_INACTIVE_LEFT_IMAGE, newValue);
    }
    
    /**
     * Sets the right image used to render inactive tabs. The width of the image
     * must be set, and has to be a pixel value.
     * 
     * @param newValue the inactive tab right image
     */
    public void setTabInactiveRightImage(ImageReference newValue) {
        setProperty(PROPERTY_TAB_INACTIVE_RIGHT_IMAGE, newValue);
    }
    
    /**
     * Sets the background color used to render inactive tabs.
     * 
     * @param newValue the new inactive tab background
     */
    public void setTabInactiveBackground(Color newValue) {
        setProperty(PROPERTY_TAB_INACTIVE_BACKGROUND, newValue);
    }
    
    /**
     * Sets the background image used to render inactive tabs.
     * 
     * @param newValue the new inactive tab background image
     */
    public void setTabInactiveBackgroundImage(FillImage newValue) {
        setProperty(PROPERTY_TAB_INACTIVE_BACKGROUND_IMAGE, newValue);
    }
    
    /**
     * Sets the <code>Border</code> used to draw inactive tabs in the 
     * <code>TabPane</code>.
     * 
     * @param newValue the new border
     */
    public void setTabInactiveBorder(Border newValue) {
        setProperty(PROPERTY_TAB_INACTIVE_BORDER, newValue);
    }
    
    /**
     * Sets the font used to render inactive tabs.
     * 
     * @param newValue the new inactive tab font
     */
    public void setTabInactiveFont(Font newValue) {
        setProperty(PROPERTY_TAB_INACTIVE_FONT, newValue);
    }
    
    /**
     * Sets the foreground color used to render inactive tabs.
     * 
     * @param newValue the new inactive tab foreground
     */
    public void setTabInactiveForeground(Color newValue) {
        setProperty(PROPERTY_TAB_INACTIVE_FOREGROUND, newValue);
    }
    
    /**
     * Sets the horizontal distance from which all tabs are inset from 
     * the edge of the <code>TabPane</code>.
     * 
     * @param newValue the new tab inset
     */
    public void setTabInset(Extent newValue) {
        setProperty(PROPERTY_TAB_INSET, newValue);
    }
    
    /**
     * Sets the position where the tabs are located relative to the pane 
     * content.
     * 
     * @param newValue the new tab position, one of the following values:
     *        <ul>
     *         <li><code>TAB_POSITION_TOP</code></li>
     *         <li><code>TAB_POSITION_BOTTOM</code></li>
     *        </ul>
     */
    public void setTabPosition(int newValue) {
        setProperty(PROPERTY_TAB_POSITION, new Integer(newValue));
    }
    
    /**
     * Sets the horizontal space between individual tabs.
     * 
     * @param newValue the new tab spacing
     */
    public void setTabSpacing(Extent newValue) {
        setProperty(PROPERTY_TAB_SPACING, newValue);
    }
    
    /**
     * Sets the width of an individual tab.
     * 
     * @param newValue the new tab width
     */
    public void setTabWidth(Extent newValue) {
        setProperty(PROPERTY_TAB_WIDTH, newValue);
    }
    
    /**
     * Sets the alignment within an individual tab.
     * 
     * @param newValue the new tab alignment
     */
    public void setTabAlignment(Alignment newValue) {
    	setProperty(PROPERTY_TAB_ALIGNMENT, newValue);
    }
    
    /**
	 * Determines if tab close is enabled in general. Individual tabs can
	 * explicitly disable close support using
	 * {@link TabPaneLayoutData#setCloseEnabled(boolean)}.
	 * 
	 * @return true if tab close is enabled
	 */
    public boolean isTabCloseEnabled() {
    	Boolean value = (Boolean) getProperty(PROPERTY_TAB_CLOSE_ENABLED);
    	return value == null ? false : value.booleanValue();
    }
    
    /**
     * Determines if close icon rollover effects are enabled.
     * 
     * @return true if close icon rollover effects are enabled
     */
    public boolean isTabCloseIconRolloverEnabled() {
        Boolean value = (Boolean) getProperty(PROPERTY_TAB_CLOSE_ICON_ROLLOVER_ENABLED);
        return value == null ? false : value.booleanValue();
    }

    /**
     * Returns the close icon displayed in the tabs.
     * 
     * @return the icon
     */
    public ImageReference getTabCloseIcon() {
        return (ImageReference) getProperty(PROPERTY_TAB_CLOSE_ICON);
    }

    /**
	 * Returns the close icon that is displayed when tab close is disabled.
	 * 
	 * @return the icon
	 */
    public ImageReference getTabDisabledCloseIcon() {
        return (ImageReference) getProperty(PROPERTY_TAB_DISABLED_CLOSE_ICON);
    }

    /**
	 * Returns the close icon that is displayed when the mouse cursor is inside
	 * its bounds.
	 * 
	 * @return the icon
	 */
    public ImageReference getTabRolloverCloseIcon() {
        return (ImageReference) getProperty(PROPERTY_TAB_ROLLOVER_CLOSE_ICON);
    }

    /**
	 * Sets if tab close is enabled in general. Individual tabs can explicitly
	 * disable close support using
	 * {@link TabPaneLayoutData#setCloseEnabled(boolean)}.
	 * 
	 * @param newValue the new state
	 */
    public void setTabCloseEnabled(boolean newValue) {
    	setProperty(PROPERTY_TAB_CLOSE_ENABLED, new Boolean(newValue));
    }
    
    /**
     * Sets if close icon rollover effects are enabled.
     * 
	 * @param newValue true if close icon rollover effects should be enabled
     */
    public void setTabCloseIconRolloverEnabled(boolean newValue) {
    	setProperty(PROPERTY_TAB_CLOSE_ICON_ROLLOVER_ENABLED, new Boolean(newValue));
    }

    /**
     * Sets the close icon displayed in the tabs.
     * 
     * @param newValue the new icon
     */
    public void setTabCloseIcon(ImageReference newValue) {
    	setProperty(PROPERTY_TAB_CLOSE_ICON, newValue);
    }

    /**
	 * Sets the close icon that is displayed when tab close is disabled.
	 * 
     * @param newValue the new icon
	 */
    public void setTabDisabledCloseIcon(ImageReference newValue) {
    	setProperty(PROPERTY_TAB_DISABLED_CLOSE_ICON, newValue);
    }

    /**
	 * Sets the close icon that is displayed when the mouse cursor is inside
	 * its bounds.
	 * 
     * @param newValue the new icon
	 */
    public void setTabRolloverCloseIcon(ImageReference newValue) {
    	setProperty(PROPERTY_TAB_ROLLOVER_CLOSE_ICON, newValue);
    }
}