package net.kawinski.collecting.presentation.controllers.user;

import lombok.extern.slf4j.Slf4j;
import net.kawinski.collecting.presentation.controllers.JsfRedirectHelper;
import net.kawinski.logging.NkTrace;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import javax.enterprise.inject.Model;

import static org.omnifaces.util.Faces.invalidateSession;

@Model
@Slf4j
public class LogoutController {

    public void logout() throws Exception {
        try(final NkTrace trace = NkTrace.info(log)) {
            Faces.logout();
            invalidateSession();
            Messages.addFlashGlobalInfo("Wylogowałeś się");
            JsfRedirectHelper.redirectIndex();
        }
    }

}
