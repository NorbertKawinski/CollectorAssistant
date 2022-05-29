package net.kawinski.collecting.presentation.controllers.attribute.template;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.kawinski.collecting.data.model.ElementAttributeTemplate;
import net.kawinski.collecting.service.attribute.ElementAttributeTemplateService;
import net.kawinski.logging.NkTrace;
import net.kawinski.utils.jee.JeeUtils;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

@Model
@Slf4j
public class ReadElementAttributeTemplateController {

    @Getter
    private final ElementAttributeTemplate attribute;

    @Inject
    public ReadElementAttributeTemplateController(ElementAttributeTemplateService elementAttributeTemplateService) {
        try (final NkTrace trace = NkTrace.info(log)) {
            final long attributeId = JeeUtils.getRequestParamLongOrThrow("id");
            attribute = elementAttributeTemplateService.findByIdOrThrow(attributeId);
        }
    }
}
