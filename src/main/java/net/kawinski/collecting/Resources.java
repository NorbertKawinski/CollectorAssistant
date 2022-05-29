package net.kawinski.collecting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

public class Resources {
    public static final boolean PROD = Optional.ofNullable(System.getenv("ALQ_ENV"))
            .orElse("PROD")
            .equalsIgnoreCase("PROD");
    public static final boolean DEV = !PROD;

    public static final String CA_BASE_URL = "http://localhost:8080/ca";
    public static final String IMAGE_MATCH_BASE_URL = PROD ? "http://ca_imagematch_server:5000" : "http://localhost:9202";

    public static final String DEFAULT_ADMIN_USERNAME = "Admin";
    public static final String DEFAULT_ADMIN_MAIL = "administrator@ca.kawinski.net";
    public static final String DEFAULT_ADMIN_PASSWORD = "123456";
    public static final String DEFAULT_USER_CATALOGER_USERNAME = "cataloger";
    public static final String DEFAULT_USER_CATALOGER_MAIL = "cataloger@ca.kawinski.net";
    public static final String DEFAULT_USER_CATALOGER_PASSWORD = "123456";
    public static final String DEFAULT_USER1_USERNAME = "user1";
    public static final String DEFAULT_USER1_MAIL = "user1@ca.kawinski.net";
    public static final String DEFAULT_USER1_PASSWORD = "user1";
    public static final String DEFAULT_USER2_USERNAME = "user2";
    public static final String DEFAULT_USER2_MAIL = "user2@ca.kawinski.net";
    public static final String DEFAULT_USER2_PASSWORD = "user2";
    public static final String DEFAULT_USER3_USERNAME = "user3";
    public static final String DEFAULT_USER3_MAIL = "user3@ca.kawinski.net";
    public static final String DEFAULT_USER3_PASSWORD = "user3";
    public static final String DEFAULT_TESTUSER_PASSWORD = "password";

    public static final int DEFAULT_PAGE_SIZE = 10;

    /**
     * The "/api" part is already defined in web.xml so we're left with adding version on top of it.
     */
    public static final String REST_URL_V1_PREFIX = "/v1";


    @Produces
    public Logger produceLog(final InjectionPoint injectionPoint) {
        return LoggerFactory.getLogger(injectionPoint.getMember().getDeclaringClass());
    }

    @Produces
    @PersistenceContext
    private EntityManager em;

    @Produces
    @RequestScoped
    public FacesContext produceFacesContext() {
        return FacesContext.getCurrentInstance();
    }

}
