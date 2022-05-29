package net.kawinski.collecting.presentation.controllers.search;


import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.kawinski.collecting.Resources;
import net.kawinski.collecting.service.search.BaseSearchForm;
import net.kawinski.logging.NkTrace;
import net.kawinski.utils.Page;
import net.kawinski.utils.PagedResult;
import net.kawinski.utils.jee.JeeUtils;
import org.omnifaces.util.Messages;

import javax.annotation.PostConstruct;
import javax.json.bind.JsonbBuilder;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

// It's not a bean -,- You're drunk IntelliJ
// Refers to an error which tells us that beans must have no-args constructor.
// But since this is an abstract class used by other beans then it's fine
@SuppressWarnings("CdiManagedBeanInconsistencyInspection")
@Slf4j
public abstract class BaseSearchController<FORM_T extends BaseSearchForm, RESULT_T> implements Serializable {

    private final Class<FORM_T> formClass;

    @Getter
    @Setter
    private FORM_T form;

    @Getter
    private String query = "";

    @Getter
    private PagedResult<RESULT_T> results = new PagedResult<>(new ArrayList<>(), Page.EMPTY, 0);

    protected abstract void initialize() throws Exception;
    protected abstract void refreshForm(FORM_T form);
    protected abstract PagedResult<RESULT_T> findResults(Page page) throws IOException;

    public BaseSearchController(Class<FORM_T> formClass) {
        this.formClass = formClass;
    }

    @PostConstruct
    public void postConstruct() {
        try (final NkTrace trace = NkTrace.info(log)) {
            form = JeeUtils.getRequestParam("query")
                    .filter(query -> !query.trim().isEmpty())
                    .map(query -> JsonbBuilder.create().fromJson(query, formClass))
                    .orElse(formClass.newInstance());
            JeeUtils.getRequestParam("simpleQuery").ifPresent(simpleQuery -> {
                form.setName(simpleQuery);
            });

            initialize();

            refreshResults();
        } catch (Exception ex) {
            throw new RuntimeException("Failed to initialize controller", ex);
        }
    }

    private void refreshResults() throws IOException {
        try (final NkTrace trace = NkTrace.info(log, "form: {}", form)) {
            refreshForm(form);
            query = JsonbBuilder.create().toJson(form);

            int page = JeeUtils.getRequestParamInt("page").orElse(1);
            results = findResults(new Page(page, Resources.DEFAULT_PAGE_SIZE));
            Messages.addFlashGlobalInfo("Znaleziono " + results.getRecordsTotal() + " wyników");
        }
    }

    public void search() throws Exception {
        refreshResults();
    }
}
