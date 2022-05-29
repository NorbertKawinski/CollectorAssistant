package net.kawinski.collecting.presentation.controllers.attribute.template;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.kawinski.collecting.data.model.CollectionAttributeTemplate;
import net.kawinski.collecting.presentation.controllers.JsfRedirectHelper;
import net.kawinski.collecting.presentation.controllers.security.AccessController;
import net.kawinski.collecting.service.attribute.CollectionAttributeTemplateService;
import net.kawinski.logging.NkTrace;
import net.kawinski.utils.jee.JeeUtils;
import org.omnifaces.util.Messages;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

@Model
@Slf4j
public class UpdateCollectionAttributeTemplateController {

    private final CollectionAttributeTemplateService collectionAttributeTemplateService;

    @Getter
    private final CollectionAttributeTemplate attribute;

    @Inject
    public UpdateCollectionAttributeTemplateController(AccessController accessController,
                                                       CollectionAttributeTemplateService collectionAttributeTemplateService) {
        try (final NkTrace trace = NkTrace.info(log)) {
            this.collectionAttributeTemplateService = collectionAttributeTemplateService;
            accessController.permitAdmin();

            final long attributeId = JeeUtils.getRequestParamLongOrThrow("id");
            attribute = collectionAttributeTemplateService.findByIdOrThrow(attributeId);
        }
    }

    public void update() {
        try (final NkTrace ignored = NkTrace.info(log)) {
            collectionAttributeTemplateService.update(attribute);
            Messages.addFlashGlobalInfo("Pomyślnie zaktualizowano atrybut");
            JsfRedirectHelper.redirectReadCollectionAttributeTemplate(attribute);
        }
    }
}
