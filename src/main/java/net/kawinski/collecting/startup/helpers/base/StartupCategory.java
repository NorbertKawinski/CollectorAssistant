package net.kawinski.collecting.startup.helpers.base;

import lombok.Getter;
import net.kawinski.collecting.data.model.AttributeType;
import net.kawinski.collecting.data.model.CaUpload;
import net.kawinski.collecting.data.model.Category;
import net.kawinski.collecting.data.model.Collection;
import net.kawinski.collecting.data.model.CollectionAttributeTemplate;
import net.kawinski.collecting.data.model.ElementAttributeTemplate;
import net.kawinski.collecting.data.model.User;
import net.kawinski.collecting.service.attribute.AttributeTarget;
import net.kawinski.collecting.service.attribute.CreateAttributeTemplateForm;
import net.kawinski.collecting.service.category.CreateCategoryForm;
import net.kawinski.collecting.service.collection.CreateCollectionForm;
import org.omnifaces.persistence.model.GeneratedIdEntity;

import javax.ws.rs.core.Form;
import java.util.Optional;

public abstract class StartupCategory<PARENT extends StartupCategory> extends StartupObject {

    @Getter
    private final PARENT parent;

    @Getter
    private final Category model;

    public final int baseDispOrder;
    public final int baseDispOrderEnd;

    public StartupCategory(final PARENT parent, final String name, final boolean canContainCollections) {
        this.parent = parent;
        this.model = newCategory(parent, name, canContainCollections);
        if(parent == null) {
            this.baseDispOrder = 1000;
            this.baseDispOrderEnd = 1000*1000;
        } else {
            this.baseDispOrder = parent.baseDispOrder + 1000;
            this.baseDispOrderEnd = parent.baseDispOrderEnd - 1000;
        }
    }

    private Category newCategory(final PARENT parent, final String name, final boolean canContainCollections) {
        final Long parentId = Optional.ofNullable(parent)
                .map(PARENT::getModel)
                .map(GeneratedIdEntity::getId)
                .orElse(null);
        return srv.getCategoryService().create(new CreateCategoryForm(parentId, name, canContainCollections));
    }

    public final CollectionAttributeTemplate newColAttr(final String name, final AttributeType attributeType, final int displayOrder, final boolean searchable) {
        return srv.getAttributeService().createForCollection(
                new CreateAttributeTemplateForm(model, AttributeTarget.Collection, name, attributeType, displayOrder, searchable)
        );
    }

    public final ElementAttributeTemplate newEleAttr(final String name, final AttributeType attributeType, final int displayOrder, final boolean searchable) {
        return srv.getAttributeService().createForElement(
                new CreateAttributeTemplateForm(model, AttributeTarget.Element, name, attributeType, displayOrder, searchable)
        );
    }

    public Collection newCollection(final User owner, final Collection basedOn, final String name, final boolean catalog) {
        return srv.getCollectionService().create(owner, new CreateCollectionForm(model.getId(), basedOn, name, catalog));
    }

    public final CaUpload newUpload(final String path) {
        return newUpload(ca.usrAdmin, path);
    }

    public final void setImage(final String path) {
        model.getImage().setImage(newUpload(path));
    }

    public final void setIcon(final String path) {
        model.getImage().setIcon(newUpload(path));
    }

}
