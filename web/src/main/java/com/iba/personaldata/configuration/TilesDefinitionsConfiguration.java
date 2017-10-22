package com.iba.personaldata.configuration;

import org.apache.tiles.Attribute;
import org.apache.tiles.Definition;
import org.apache.tiles.definition.DefinitionsFactory;
import org.apache.tiles.request.Request;

import java.util.HashMap;
import java.util.Map;

public class TilesDefinitionsConfiguration implements DefinitionsFactory {
    private static final Map<String, Definition> tilesDefinitions = new HashMap<>();
    private static final Attribute BASE_TEMPLATE = new Attribute("/WEB-INF/view/layout/defaultLayout.jsp");

    @Override
    public Definition getDefinition(String name, Request tilesContext) {
        return tilesDefinitions.get(name);
    }

    /**
     * @param name Name of the view
     * @param title Page title
     * @param body Body JSP file path
     * Adds default layout definitions
     */
    private static void addDefaultLayoutDef(String name, String title, String body) {
        Map<String, Attribute> attributes = new HashMap<>();
        attributes.put("title", new Attribute(title));
        attributes.put("header", new Attribute("/WEB-INF/view/layout/header.jsp"));
        attributes.put("body", new Attribute(body));
        attributes.put("footer", new Attribute("/WEB-INF/view/layout/footer.jsp"));
        tilesDefinitions.put(name, new Definition(name, BASE_TEMPLATE, attributes));
    }

    /**
     * Adds Apache tiles definitions
     */
    public static void addDefinitions(){
        addDefaultLayoutDef("login", "Login", "/WEB-INF/view/login.jsp");
        addDefaultLayoutDef("user", "User", "/WEB-INF/view/user.jsp");
        addDefaultLayoutDef("admin", "Admin", "/WEB-INF/view/admin.jsp");
        addDefaultLayoutDef("edit", "Edit Profile", "/WEB-INF/view/edit.jsp");
        addDefaultLayoutDef("error", "Error", "/WEB-INF/view/error/error.jsp");
    }

}
