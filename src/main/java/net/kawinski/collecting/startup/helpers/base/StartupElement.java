package net.kawinski.collecting.startup.helpers.base;

import lombok.Getter;
import net.kawinski.collecting.data.model.CaUpload;
import net.kawinski.collecting.data.model.Element;
import net.kawinski.collecting.data.model.ElementAttribute;
import net.kawinski.collecting.data.model.ElementAttributeTemplate;
import net.kawinski.collecting.data.model.IssTask;

public class StartupElement<COLLECTION extends StartupCollection> extends StartupObject {

    @Getter
    private final COLLECTION collection;

    @Getter
    private final Element model;

    public StartupElement(final COLLECTION collection, final String name) {
        this(collection, name, null);
    }

    public StartupElement(final COLLECTION collection, final String name, Element basedOn) {
        this.collection = collection;
        this.model = collection.newElement(name, basedOn);
    }

    public final void addAttribute(final ElementAttributeTemplate template, final String value) {
        srv.getElementAttributeService().create(new ElementAttribute(model, template, value));
    }

    public final void addAttribute(final ElementAttributeTemplate template, final String... values) {
        for(final String value : values)
            addAttribute(template, value);
    }

    public final CaUpload newUpload(final String path) {
        return newUpload(collection.getOwnerModel(), path);
    }

    public final void setImage(final String path) {
        CaUpload upload = newUpload(path);
        srv.getElementService().updateImage(model, upload);
    }

    public final void setIcon(final String path) {
        model.getImage().setIcon(newUpload(path));
    }
}
