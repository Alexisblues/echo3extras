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

package nextapp.echo.extras.webcontainer.sync.component;

import nextapp.echo.app.Component;
import nextapp.echo.app.util.Context;
import nextapp.echo.extras.app.MenuBarPane;
import nextapp.echo.webcontainer.ComponentSynchronizePeer;

/**
 * Synchronization peer for <code>MenuBarPane</code>s.
 * 
 * @author n.beekman
 */
public class MenuBarPanePeer extends AbstractMenuPeer {

    public Class getEventDataClass(String eventType) {
        if ("select".equals(eventType)) {
            return String.class;
        } else {
            return super.getEventDataClass(eventType);
        }
    }

    public void processEvent(Context context, Component component, String eventType, Object eventData) {
        if ("select".equals(eventType)) {
            System.err.println("Select event:" + eventData);
        } else {
            super.processEvent(context, component, eventType, eventData);
        }
    }

    public MenuBarPanePeer() {
        super();
    }
    
    /**
     * @see nextapp.echo.webcontainer.ComponentSynchronizePeer#getComponentClass()
     */
    public Class getComponentClass() {
        return MenuBarPane.class;
    }
    
    /**
     * @see ComponentSynchronizePeer#getPropertyClass(String)
     */
    public Class getPropertyClass(String propertyName) {
//        if (PROPERTY_SELECTION.equals(propertyName)) {
//            return String.class;
//        }
        return super.getPropertyClass(propertyName);
    }
    
    /**
     * @see ComponentSynchronizePeer#getOutputProperty(Context, Component, String, int)
     */
    public Object getOutputProperty(Context context, Component component, String propertyName, int propertyIndex) {
//        Table table = (Table)component;
//        if (PROPERTY_COLUMN_COUNT.equals(propertyName)) {
//            return new Integer(table.getModel().getColumnCount());
//        } else if (PROPERTY_HEADER_VISIBLE.equals(propertyName)) {
//            return Boolean.valueOf(table.isHeaderVisible());
//        } else if (PROPERTY_ROW_COUNT.equals(propertyName)) {
//            return new Integer(table.getModel().getRowCount());
//        } else if (PROPERTY_SELECTION.equals(propertyName)) {
//            return getSelectionString(table.getSelectionModel(), table.getModel());
//        } else if (PROPERTY_SELECTION_MODE.equals(propertyName)) {
//            return new Integer(table.getSelectionModel().getSelectionMode());
//        }
        return super.getOutputProperty(context, component, propertyName, propertyIndex);
    }
    
    /**
     * @see ComponentSynchronizePeer#storeInputProperty(Context, Component, String, int, Object)
     */
    public void storeInputProperty(Context context, Component component, String propertyName, int index, Object newValue) {
        super.storeInputProperty(context, component, propertyName, index, newValue);
//        if (PROPERTY_SELECTION.equals(propertyName)) {
//            String[] tokens = ((String)newValue).split(",");
//            int[] selectedIndices = new int[tokens.length];
//            for (int i = 0; i < tokens.length; ++i) {
//                selectedIndices[i] = Integer.parseInt(tokens[i]);
//            }
//            ClientUpdateManager clientUpdateManager = (ClientUpdateManager) context.get(ClientUpdateManager.class);
//            clientUpdateManager.setComponentProperty(component, Table.SELECTION_CHANGED_PROPERTY, selectedIndices);
//        }
    }
}