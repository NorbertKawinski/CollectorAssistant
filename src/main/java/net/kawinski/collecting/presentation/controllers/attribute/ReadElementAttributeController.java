package net.kawinski.collecting.presentation.controllers.attribute;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.kawinski.collecting.data.model.ElementAttribute;
import net.kawinski.collecting.presentation.controllers.security.AccessController;
import net.kawinski.collecting.service.attribute.ElementAttributeService;
import net.kawinski.logging.NkTrace;
import net.kawinski.utils.jee.JeeUtils;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

@Model
@Slf4j
public class ReadElementAttributeController {

    @Getter
    private final ElementAttribute attribute;

    @Inject
    public ReadElementAttributeController(AccessController accessController,
                                          ElementAttributeService elementAttributeService) {
        try (final NkTrace trace = NkTrace.info(log)) {
            final long attributeId = JeeUtils.getRequestParamLongOrThrow("id");
            attribute = elementAttributeService.findByIdOrThrow(attributeId);

            accessController.permitCanSee(attribute);
        }
    }
}
