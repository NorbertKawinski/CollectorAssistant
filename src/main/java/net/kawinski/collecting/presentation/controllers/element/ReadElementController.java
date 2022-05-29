package net.kawinski.collecting.presentation.controllers.element;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.kawinski.collecting.data.model.Element;
import net.kawinski.collecting.data.model.ElementAttributeTemplate;
import net.kawinski.collecting.presentation.controllers.security.AccessController;
import net.kawinski.collecting.service.category.CategoryService;
import net.kawinski.collecting.service.element.ElementService;
import net.kawinski.logging.NkTrace;
import net.kawinski.utils.jee.JeeUtils;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import java.util.List;

@Model
@Slf4j
public class ReadElementController {

    @Getter
    private final Element element;

    @Getter
    private final List<ElementAttributeTemplate> attributeTemplates;

    @Getter
    @Setter
    private long selectedAddAttributeTemplateId = 0;

    @Inject
    public ReadElementController(AccessController accessController,
                                 ElementService elementService,
                                 CategoryService categoryService) {
        try (final NkTrace ignored = NkTrace.info(log)) {
            final long elementId = JeeUtils.getRequestParamLongOrThrow("id");
            element = elementService.findByIdOrThrow(elementId, true);
            attributeTemplates = categoryService.getElementAttributeTemplates(element.getCategory());

            accessController.permitCanSee(element);
        }
    }
}
