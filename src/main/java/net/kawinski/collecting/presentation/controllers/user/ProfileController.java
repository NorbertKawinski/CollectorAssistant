package net.kawinski.collecting.presentation.controllers.user;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.kawinski.collecting.data.model.Collection;
import net.kawinski.collecting.data.model.User;
import net.kawinski.collecting.presentation.controllers.security.AccessController;
import net.kawinski.collecting.service.user.UserService;
import net.kawinski.logging.NkTrace;
import net.kawinski.utils.jee.JeeUtils;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import java.util.List;

@Model
@Slf4j
public class ProfileController {

    @Getter
    private final User user;

    @Getter
    private final List<Collection> collections;

    @Inject
    public ProfileController(AccessController accessController,
                             UserService userService) {
        try (final NkTrace trace = NkTrace.info(log)) {
            final long userId = JeeUtils.getRequestParamLongOrThrow("id");
            user = userService.findByIdOrThrow(userId, true, true);
            collections = accessController.filterCanSeeCollections(user.getCollections());
        }
    }
}
