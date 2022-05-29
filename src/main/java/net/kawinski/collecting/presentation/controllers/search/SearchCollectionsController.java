package net.kawinski.collecting.presentation.controllers.search;

import lombok.extern.slf4j.Slf4j;
import net.kawinski.collecting.data.model.Collection;
import net.kawinski.collecting.service.attribute.AttributeTarget;
import net.kawinski.collecting.service.collection.CollectionService;
import net.kawinski.collecting.service.search.SearchCollectionForm;
import net.kawinski.utils.Page;
import net.kawinski.utils.PagedResult;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
@Slf4j
public class SearchCollectionsController extends SearchController<SearchCollectionForm, Collection> implements Serializable {

    @Inject
    private CollectionService collectionService;

    public SearchCollectionsController() {
        super(SearchCollectionForm.class, AttributeTarget.Collection);
    }

    @Override
    protected PagedResult<Collection> findResults(Page page) {
        PagedResult<Collection> results = collectionService.findByFilters(getForm(), page);
        return accessController.filterCanSeeCollections(results);
    }

}
