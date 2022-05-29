package net.kawinski.collecting.presentation.controllers.attribute.template;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.kawinski.collecting.data.model.CollectionAttributeTemplate;
import net.kawinski.collecting.presentation.controllers.JsfRedirectHelper;
import net.kawinski.collecting.presentation.controllers.security.AccessController;
import net.kawinski.collecting.service.attribute.CollectionAttributeTemplateService;
import net.kawinski.collecting.service.common.DeleteByIdForm;
import net.kawinski.logging.NkTrace;
import net.kawinski.utils.jee.JeeUtils;
import org.omnifaces.util.Messages;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

@Model
@Slf4j
public class DeleteCollectionAttributeTemplateController {

    private final CollectionAttributeTemplateService collectionAttributeTemplateService;

    @Getter
    private final CollectionAttributeTemplate attribute;

    @Getter
    @Setter
    private DeleteByIdForm form = new DeleteByIdForm();

    @Inject
    public DeleteCollectionAttributeTemplateController(AccessController accessController,
                                                       CollectionAttributeTemplateService collectionAttributeTemplateService) {
        try (final NkTrace trace = NkTrace.info(log)) {
            this.collectionAttributeTemplateService = collectionAttributeTemplateService;
            accessController.permitAdmin();

            form.setId(JeeUtils.getRequestParamLongOrThrow("id"));
            attribute = collectionAttributeTemplateService.findByIdOrThrow(form.getId());
            trace.setExitMsg("form: {}, attribute: {}", form, attribute);
        }
    }

    public void confirm() {
        try (final NkTrace trace = NkTrace.info(log)) {
            collectionAttributeTemplateService.delete(attribute);

            Messages.addFlashGlobalInfo("Pomyślnie usunięto szablon atrybutu kolekcji '" + attribute.getName() + "'.");
            JsfRedirectHelper.redirectManageCategory(attribute.getCategory());
        }
    }
}
