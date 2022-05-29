package net.kawinski.collecting.presentation.rest;

import lombok.extern.slf4j.Slf4j;
import net.kawinski.collecting.Resources;
import net.kawinski.collecting.data.model.User;
import net.kawinski.collecting.service.user.UserService;
import net.kawinski.logging.NkTrace;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path(Resources.REST_URL_V1_PREFIX)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Slf4j
public class UserRest {

    @Inject
    private UserService userService;

    @GET
    @Path("/users")
    public List<User> getUsers() {
        try(final NkTrace trace = NkTrace.info(log)) {
            return trace.returning(userService.findAll());
        }
    }

    // TODO: Dodac endpoint do generacji danych;

}
