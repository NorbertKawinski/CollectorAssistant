package net.kawinski.collecting.presentation.controllers.attribute.template;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.kawinski.collecting.data.model.CollectionAttributeTemplate;
import net.kawinski.collecting.service.attribute.CollectionAttributeTemplateService;
import net.kawinski.logging.NkTrace;
import net.kawinski.utils.jee.JeeUtils;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

@Model
@Slf4j
public class ReadCollectionAttributeTemplateController {

    @Getter
    private final CollectionAttributeTemplate attribute;

    @Inject
    public ReadCollectionAttributeTemplateController(CollectionAttributeTemplateService collectionAttributeTemplateService) {
        try (final NkTrace trace = NkTrace.info(log)) {
            final long attributeId = JeeUtils.getRequestParamLongOrThrow("id");
            attribute = collectionAttributeTemplateService.findByIdOrThrow(attributeId);
        }
    }
}
