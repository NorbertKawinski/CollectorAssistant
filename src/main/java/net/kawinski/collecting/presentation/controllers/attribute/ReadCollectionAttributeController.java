package net.kawinski.collecting.presentation.controllers.attribute;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.kawinski.collecting.data.model.CollectionAttribute;
import net.kawinski.collecting.presentation.controllers.security.AccessController;
import net.kawinski.collecting.service.attribute.CollectionAttributeService;
import net.kawinski.logging.NkTrace;
import net.kawinski.utils.jee.JeeUtils;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

@Model
@Slf4j
public class ReadCollectionAttributeController {

    @Getter
    private final CollectionAttribute attribute;

    @Inject
    public ReadCollectionAttributeController(AccessController accessController,
                                             CollectionAttributeService collectionAttributeService) {
        try (final NkTrace trace = NkTrace.info(log)) {
            final long attributeId = JeeUtils.getRequestParamLongOrThrow("id");
            attribute = collectionAttributeService.findByIdOrThrow(attributeId);

            accessController.permitCanSee(attribute);
        }
    }
}
