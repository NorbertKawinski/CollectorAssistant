package net.kawinski.collecting.presentation.controllers.search;


import lombok.extern.slf4j.Slf4j;
import net.kawinski.collecting.data.model.User;
import net.kawinski.collecting.presentation.controllers.security.AccessController;
import net.kawinski.collecting.service.search.SearchUserForm;
import net.kawinski.collecting.service.user.UserService;
import net.kawinski.logging.NkTrace;
import net.kawinski.utils.Page;
import net.kawinski.utils.PagedResult;

import javax.enterprise.inject.Model;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

@Model
@ViewScoped
@Slf4j
public class SearchUsersController extends BaseSearchController<SearchUserForm, User> {

    @Inject
    private AccessController accessController;

    @Inject
    private UserService userService;

    public SearchUsersController() {
        super(SearchUserForm.class);
    }

    @Override
    public void initialize() {
        try (final NkTrace trace = NkTrace.info(log)) {
            accessController.permitAdmin();
        }
    }

    @Override
    protected void refreshForm(SearchUserForm form) {
    }

    @Override
    protected PagedResult<User> findResults(Page page) {
        return userService.findByFilters(getForm(), page);
    }
}
