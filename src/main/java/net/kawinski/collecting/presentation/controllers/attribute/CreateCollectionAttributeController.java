package net.kawinski.collecting.presentation.controllers.attribute;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.kawinski.collecting.data.model.Collection;
import net.kawinski.collecting.data.model.CollectionAttribute;
import net.kawinski.collecting.data.model.CollectionAttributeTemplate;
import net.kawinski.collecting.presentation.controllers.JsfRedirectHelper;
import net.kawinski.collecting.presentation.controllers.security.AccessController;
import net.kawinski.collecting.service.attribute.CollectionAttributeService;
import net.kawinski.collecting.service.collection.CollectionService;
import net.kawinski.logging.NkTrace;
import net.kawinski.utils.jee.JeeUtils;
import org.omnifaces.util.Messages;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

@Model
@Slf4j
public class CreateCollectionAttributeController {

    private final CollectionAttributeService collectionAttributeService;

    @Getter
    private final CollectionAttributeTemplate attributeTemplate;

    @Getter
    private final Collection collection;

    @Getter
    private final EditAttributeBaseController editAttributeBaseController;

    @Inject
    public CreateCollectionAttributeController(AccessController accessController,
                                               CollectionService collectionService,
                                               CollectionAttributeService collectionAttributeService,
                                               EditAttributeBaseController editAttributeBaseController) {
        try (final NkTrace trace = NkTrace.info(log)) {
            this.collectionAttributeService = collectionAttributeService;
            this.editAttributeBaseController = editAttributeBaseController;

            final long collectionId = JeeUtils.getRequestParamLongOrThrow("collection");
            final long templateId = JeeUtils.getRequestParamLongOrThrow("template");
            collection = collectionService.findByIdOrThrow(collectionId, false, false);
            attributeTemplate = collectionAttributeService.findTemplateByIdOrThrow(templateId);

            accessController.permitCanEdit(collection);
        }
    }

    public void create() {
        try (final NkTrace trace = NkTrace.info(log)) {
            final String rawValue = editAttributeBaseController.convertToRawValue(attributeTemplate.getType());
            final CollectionAttribute collectionAttribute = new CollectionAttribute(collection, attributeTemplate, rawValue);
            collectionAttributeService.create(collectionAttribute);

            Messages.addFlashGlobalInfo("Pomyślnie dodano atrybut '" + attributeTemplate.getName() + "'.");
            JsfRedirectHelper.redirectReadCollection(collection);
        }
    }
}
