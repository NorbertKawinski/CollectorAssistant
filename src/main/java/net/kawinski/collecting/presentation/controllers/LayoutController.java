package net.kawinski.collecting.presentation.controllers;

import lombok.Getter;
import net.kawinski.collecting.data.search.SearchAttributeComparator;
import net.kawinski.collecting.presentation.controllers.search.SearchController;
import net.kawinski.collecting.presentation.controllers.user.LoggedInUser;
import net.kawinski.collecting.service.search.SearchAttributeForm;
import net.kawinski.collecting.service.search.SearchCollectionForm;
import net.kawinski.collecting.service.search.SearchElementForm;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.json.bind.JsonbBuilder;

/**
 * Controller for most pages, as most of them include "layout.xhtml" template.
 */
@Model
public class LayoutController {
    @Getter
    private final String marketSearchCollectionsQuery;

    @Getter
    private final String marketSearchElementsQuery;

    @Getter
    private final String searchMyCollectionsQuery;

    @Inject
    public LayoutController(LoggedInUser loggedUser) {
        SearchCollectionForm marketSearchCollectionsForm = new SearchCollectionForm();
        marketSearchCollectionsForm.addAttribute(new SearchAttributeForm("Na sprzedaż", SearchAttributeComparator.EQ, "true"));
        marketSearchCollectionsQuery = JsonbBuilder.create().toJson(marketSearchCollectionsForm);

        SearchElementForm marketSearchElementsForm = new SearchElementForm();
        marketSearchElementsForm.addAttribute(new SearchAttributeForm("Na sprzedaż", SearchAttributeComparator.EQ, "true"));
        marketSearchElementsQuery = JsonbBuilder.create().toJson(marketSearchElementsForm);

        SearchCollectionForm searchMyCollectionsForm = new SearchCollectionForm();
        loggedUser.getLoggedInUser().ifPresent(user -> {
            searchMyCollectionsForm.setOwner(user.getName());
        });
        searchMyCollectionsQuery = JsonbBuilder.create().toJson(searchMyCollectionsForm);
    }
}
