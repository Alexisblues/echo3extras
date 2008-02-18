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

import nextapp.echo.app.util.Context;
import nextapp.echo.extras.app.Group;
import nextapp.echo.extras.webcontainer.CommonResources;
import nextapp.echo.extras.webcontainer.service.CommonService;
import nextapp.echo.webcontainer.AbstractComponentSynchronizePeer;
import nextapp.echo.webcontainer.ContentType;
import nextapp.echo.webcontainer.ResourceRegistry;
import nextapp.echo.webcontainer.ServerMessage;
import nextapp.echo.webcontainer.Service;
import nextapp.echo.webcontainer.WebContainerServlet;
import nextapp.echo.webcontainer.service.JavaScriptService;

/**
 * Synchronization peer for <code>Group</code>s.
 * 
 * @author n.beekman
 */
public class GroupPeer extends AbstractComponentSynchronizePeer {
    
    private static final Service GROUP_SERVICE = JavaScriptService.forResources("EchoExtras.Group", 
            new String[]{"/nextapp/echo/extras/webcontainer/resource/Application.Group.js",
            "/nextapp/echo/extras/webcontainer/resource/Render.Group.js"});
    
    static {
        CommonResources.install();
        ResourceRegistry resources = WebContainerServlet.getResourceRegistry();
        resources.add("Extras", "image/GroupBorderTopLeft.png", ContentType.IMAGE_PNG);
        resources.add("Extras", "image/GroupBorderTopRight.png", ContentType.IMAGE_PNG);
        resources.add("Extras", "image/GroupBorderBottomLeft.png", ContentType.IMAGE_PNG);
        resources.add("Extras", "image/GroupBorderBottomRight.png", ContentType.IMAGE_PNG);
        resources.add("Extras", "image/GroupBorderTop.png", ContentType.IMAGE_PNG);
        resources.add("Extras", "image/GroupBorderLeft.png", ContentType.IMAGE_PNG);
        resources.add("Extras", "image/GroupBorderRight.png", ContentType.IMAGE_PNG);
        resources.add("Extras", "image/GroupBorderBottom.png", ContentType.IMAGE_PNG);
        WebContainerServlet.getServiceRegistry().add(GROUP_SERVICE);
    }
    
    /**
     * @see nextapp.echo.webcontainer.ComponentSynchronizePeer#getClientComponentType(boolean)
     */
    public String getClientComponentType(boolean shortType) {
        return "ExtrasApp.Group";
    }
    
    /**
     * @see nextapp.echo.webcontainer.ComponentSynchronizePeer#getComponentClass()
     */
    public Class getComponentClass() {
        return Group.class;
    }

    /**
     * @see nextapp.echo.webcontainer.ComponentSynchronizePeer#init(nextapp.echo.app.util.Context)
     */
    public void init(Context context) {
        super.init(context);
        ServerMessage serverMessage = (ServerMessage) context.get(ServerMessage.class);
        serverMessage.addLibrary(CommonService.INSTANCE.getId());
        serverMessage.addLibrary(GROUP_SERVICE.getId());
    }
}