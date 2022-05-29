package net.kawinski.collecting.startup.helpers.base;

import lombok.Getter;
import net.kawinski.collecting.data.model.CaUpload;
import net.kawinski.collecting.data.model.Category;
import net.kawinski.collecting.data.model.Collection;
import net.kawinski.collecting.data.model.CollectionAttribute;
import net.kawinski.collecting.data.model.CollectionAttributeTemplate;
import net.kawinski.collecting.data.model.Element;
import net.kawinski.collecting.data.model.User;
import net.kawinski.collecting.service.element.CreateElementForm;

public abstract class StartupCollection<CATEGORY extends StartupCategory> extends StartupObject {

    @Getter
    private final User ownerModel;

    @Getter
    private final CATEGORY category;

    @Getter
    private final Category categoryModel;

    @Getter
    private final Collection model;

    protected StartupCollection(final User ownerModel, final CATEGORY category, final String name) {
        this(ownerModel, category, null, name, false);
    }

    protected StartupCollection(final User ownerModel, final CATEGORY category, final String name, final Collection basedOn) {
        this(ownerModel, category, basedOn, name, false);
    }

    protected StartupCollection(final User ownerModel, final CATEGORY category, final String name, final boolean catalog) {
        this(ownerModel, category, null, name, catalog);
    }

    protected StartupCollection(final User ownerModel, final CATEGORY category, final Collection basedOn, final String name, final boolean catalog) {
        this.ownerModel = ownerModel;
        this.category = category;
        this.categoryModel = category.getModel();
        this.model = category.newCollection(ownerModel, basedOn, name, catalog);
    }

    public final void addAttribute(final CollectionAttributeTemplate template, final String value) {
        srv.getCollectionAttributeService().create(new CollectionAttribute(model, template, value));
    }

    public final CaUpload newUpload(final String path) {
        return newUpload(ownerModel, path);
    }

    public final Element newElement(final String name, final Element basedOn) {
        return srv.getElementService().create(new CreateElementForm(model.getId(), basedOn, name));
    }

    public final void setImage(final String path) {
        if(path == null)
            model.getImage().setImage(null);
        else
            model.getImage().setImage(newUpload(path));
    }

    public final void setIcon(final String path) {
        if(path == null)
            model.getImage().setIcon(null);
        else
            model.getImage().setIcon(newUpload(path));
    }
}
