package net.kawinski.collecting.presentation.web.layout;

import org.jboss.arquillian.graphene.page.Location;

@Location(HomePage.location)
public class HomePage extends DefaultLayout implements ICollectorAssistantPage {
    public static final String location = "index.xhtml";
    public static final String name = "Strona Główna";

    @Override
    public String getLocation() {
        return location;
    }

    @Override
    public String getName() {
        return name;
    }
}
