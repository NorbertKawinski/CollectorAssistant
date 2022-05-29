package net.kawinski.collecting.presentation.controllers;

import net.kawinski.collecting.data.model.Category;
import net.kawinski.collecting.data.model.Collection;
import net.kawinski.collecting.data.model.CollectionAttribute;
import net.kawinski.collecting.data.model.CollectionAttributeTemplate;
import net.kawinski.collecting.data.model.Element;
import net.kawinski.collecting.data.model.ElementAttribute;
import net.kawinski.collecting.data.model.ElementAttributeTemplate;

import static org.omnifaces.util.Faces.getRequestContextPath;
import static org.omnifaces.util.Faces.redirect;

public class JsfRedirectHelper {

    public static void redirectIndex() {
        redirect(getRequestContextPath() + "/index.xhtml");
    }

    public static void redirectReadCollectionAttributeTemplate(CollectionAttributeTemplate entity) {
        redirectReadCollectionAttributeTemplate(entity.getId());
    }

    public static void redirectReadCollectionAttributeTemplate(long id) {
        redirect(getRequestContextPath() + "/attribute/template/readCollectionAttributeTemplate.xhtml?id=" + id);
    }

    public static void redirectReadElementAttributeTemplate(ElementAttributeTemplate entity) {
        redirectReadElementAttributeTemplate(entity.getId());
    }

    public static void redirectReadElementAttributeTemplate(long id) {
        redirect(getRequestContextPath() + "/attribute/template/readElementAttributeTemplate.xhtml?id=" + id);
    }

    public static void redirectReadCollectionAttribute(CollectionAttribute entity) {
        redirectReadCollectionAttribute(entity.getId());
    }

    public static void redirectReadCollectionAttribute(long id) {
        redirect(getRequestContextPath() + "/attribute/readCollectionAttribute.xhtml?id=" + id);
    }

    public static void redirectReadElementAttribute(ElementAttribute entity) {
        redirectReadElementAttribute(entity.getId());
    }

    public static void redirectReadElementAttribute(long id) {
        redirect(getRequestContextPath() + "/attribute/readElementAttribute.xhtml?id=" + id);
    }

    public static void redirectReadCategory(Category entity) {
        redirectReadCategory(entity.getId());
    }

    public static void redirectReadCategory(long id) {
        redirect(getRequestContextPath() + "/category/readCategory.xhtml?id=" + id);
    }

    public static void redirectManageCategory(Category entity) {
        redirectManageCategory(entity.getId());
    }

    public static void redirectManageCategory(long id) {
        redirect(getRequestContextPath() + "/category/manageCategory.xhtml?id=" + id);
    }

    public static void redirectReadCollection(Collection entity) {
        redirectReadCollection(entity.getId());
    }

    public static void redirectReadCollection(long id) {
        redirect(getRequestContextPath() + "/collection/readCollection.xhtml?id=" + id);
    }

    public static void redirectReadElement(Element entity) {
        redirectReadElement(entity.getId());
    }

    public static void redirectReadElement(long id) {
        redirect(getRequestContextPath() + "/element/readElement.xhtml?id=" + id);
    }
    
}
