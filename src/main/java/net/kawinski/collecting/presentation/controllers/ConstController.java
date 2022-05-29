package net.kawinski.collecting.presentation.controllers;

import net.kawinski.collecting.data.model.CollectionVisibility;
import net.kawinski.collecting.data.model.Group;
import net.kawinski.collecting.service.search.SearchCollectionVisibility;
import org.omnifaces.persistence.criteria.Bool;

import javax.enterprise.inject.Model;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

@Model
public class ConstController {

    public static final String SHORT_PROJECT_NAME = "CA";
    public static final String PROJECT_NAME = "Collector Assistant";
    public static final Group GROUP_USER = Group.USER;
    public static final Group GROUP_ADMIN = Group.ADMIN;

    public String getShortProjectName() {
        return SHORT_PROJECT_NAME;
    }

    public String getProjectName() {
        return PROJECT_NAME;
    }

    public Group getGroupUser() {
        return GROUP_USER;
    }

    public Group getGroupAdmin() {
        return GROUP_ADMIN;
    }

    public CollectionVisibility[] getCollectionVisibilities() {
        return CollectionVisibility.values();
    }

    public SearchCollectionVisibility[] getSearchCollectionVisibilities() {
        return SearchCollectionVisibility.values();
    }
}
