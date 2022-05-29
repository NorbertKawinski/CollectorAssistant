package net.kawinski.collecting.presentation.controllers.search;


import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.kawinski.collecting.data.model.BaseAttributeTemplate;
import net.kawinski.collecting.data.model.Category;
import net.kawinski.collecting.presentation.controllers.GlobalLayoutController;
import net.kawinski.collecting.presentation.controllers.attribute.EditAttributeBaseController;
import net.kawinski.collecting.presentation.controllers.security.AccessController;
import net.kawinski.collecting.service.attribute.AttributeTarget;
import net.kawinski.collecting.service.category.CategoryService;
import net.kawinski.collecting.service.search.SearchAttributeForm;
import net.kawinski.collecting.service.search.SearchForm;
import net.kawinski.collecting.service.search.SearchUploadUser;
import net.kawinski.logging.NkTrace;
import net.kawinski.utils.jee.JeeUtils;

import javax.inject.Inject;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

// It's not a bean -,- You're drunk IntelliJ
// Refers to an error which tells us that beans must have no-args constructor.
// But since this is an abstract class used by other beans then it's fine
@SuppressWarnings("CdiManagedBeanInconsistencyInspection")
@Slf4j
public abstract class SearchController<FORM_T extends SearchForm, RESULT_T> extends BaseSearchController<FORM_T, RESULT_T> {

    private final AttributeTarget attributeTarget;

    @Inject
    protected AccessController accessController;

    @Inject
    private GlobalLayoutController globalLayoutController;

    @Inject
    private CategoryService categoryService;

    @Inject
    private SearchUploadUser searchUploadUser;

    @Inject
    private EditAttributeBaseController editAttributeBaseControllerForCloning;

    @Getter
    private Category category;

    @Getter
    @Setter
    private List<SearchAttributeFormJsf> attributesToSearch;

    public SearchController(Class<FORM_T> formClass, AttributeTarget target) {
        super(formClass);
        this.attributeTarget = target;
    }

    @Override
    public void initialize() throws Exception {
        try (final NkTrace trace = NkTrace.info(log)) {
            getForm().setCategoryId(JeeUtils.getRequestParamLong("id")
                    .orElse(globalLayoutController.getRootCategory().getId()));
            category = categoryService.findByIdOrThrow(getForm().getCategoryId(), false, false, true, false);

            final List<? extends BaseAttributeTemplate> attributeTemplates = categoryService.getAttributeTemplates(category, attributeTarget);
            this.attributesToSearch = attributeTemplates.stream()
                    .filter(BaseAttributeTemplate::isSearchable)
                    .map(attributeTemplate -> new SearchAttributeFormJsf(
                            attributeTemplate.getId(),
                            attributeTemplate.getName(),
                            attributeTemplate.getType(),
                            editAttributeBaseControllerForCloning
                    ))
                    .collect(Collectors.toList());

            updateAttributesJsf(getForm(), attributesToSearch);
        }
    }

    public void updateAttributesJsf(FORM_T form, List<SearchAttributeFormJsf> attributesToSearch) {
        for (SearchAttributeFormJsf attrJsf : attributesToSearch) {
            form.getAttributes().stream()
                    .filter(attr -> attr.getName().equals(attrJsf.getName()))
                    .findFirst()
                    .ifPresent(attr -> {
                        attrJsf.setComparator(attr.getComparator());
                        attrJsf.setValue(attr);
                    });
        }
    }

    public void updateAttributes(FORM_T form, List<SearchAttributeFormJsf> attributesToSearch) {
        List<SearchAttributeForm> searchAttributes = attributesToSearch.stream()
                .map(saf -> saf.getForm(searchUploadUser.getUser()))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        form.setAttributes(searchAttributes);
    }

    @Override
    protected void refreshForm(FORM_T form) {
        try (final NkTrace trace = NkTrace.info(log, "form: {}", form)) {
            updateAttributes(form, attributesToSearch);
        }
    }
}
