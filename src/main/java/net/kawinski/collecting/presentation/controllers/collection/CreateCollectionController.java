package net.kawinski.collecting.presentation.controllers.collection;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.kawinski.collecting.data.model.Category;
import net.kawinski.collecting.data.model.Collection;
import net.kawinski.collecting.data.model.User;
import net.kawinski.collecting.presentation.controllers.JsfRedirectHelper;
import net.kawinski.collecting.presentation.controllers.security.AccessController;
import net.kawinski.collecting.presentation.controllers.user.LoggedInUser;
import net.kawinski.collecting.service.category.CategoryService;
import net.kawinski.collecting.service.collection.CollectionService;
import net.kawinski.collecting.service.collection.CreateCollectionForm;
import net.kawinski.logging.NkTrace;
import net.kawinski.utils.jee.JeeUtils;
import org.omnifaces.util.Messages;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

@Model
@Slf4j
public class CreateCollectionController {

    private final LoggedInUser loggedInUser;
    private final CollectionService collectionService;

    @Getter
    private final Category category;

    @Getter
    private final Collection basedOn;

    @Getter
    @Setter
    private CreateCollectionForm form = new CreateCollectionForm();

    @Inject
    public CreateCollectionController(AccessController accessController,
                                      LoggedInUser loggedInUser,
                                      CollectionService collectionService,
                                      CategoryService categoryService) {
        try (final NkTrace trace = NkTrace.info(log)) {
            this.loggedInUser = loggedInUser;
            this.collectionService = collectionService;
            accessController.permitLoggedIn();

            final Long categoryId = JeeUtils.getRequestParamLongOrThrow("category");
            category = categoryService.findByIdOrThrow(categoryId, false, false, false, false);

            basedOn = JeeUtils.getRequestParamLong("catalog")
                    .map(catalogId -> collectionService.findByIdOrThrow(catalogId, false, false))
                    .orElse(null);
            if(basedOn != null) {
                form.setName(basedOn.getName());
                form.setBasedOnId(basedOn.getId());
            }
            form.setCategoryId(categoryId);
        }
    }

    public void create() {
        try (final NkTrace trace = NkTrace.info(log, "form: {}", form)) {
            final User user = loggedInUser.getLoggedInUserOrThrow();
            final Collection created = collectionService.create(user, form);
            Messages.addFlashGlobalInfo("Pomyślnie utworzono kolekcję '" + form.getName() + "'.");
            JsfRedirectHelper.redirectReadCollection(created);
        }
    }
}
