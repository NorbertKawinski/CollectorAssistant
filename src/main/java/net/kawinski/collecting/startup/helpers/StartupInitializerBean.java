package net.kawinski.collecting.startup.helpers;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.kawinski.collecting.data.repository.IssTaskRepository;
import net.kawinski.collecting.service.imgsearch.IssTaskService;
import net.kawinski.collecting.service.search.SearchUploadUser;
import net.kawinski.collecting.service.attribute.AttributeService;
import net.kawinski.collecting.service.attribute.CollectionAttributeService;
import net.kawinski.collecting.service.attribute.ElementAttributeService;
import net.kawinski.collecting.service.category.CategoryService;
import net.kawinski.collecting.service.collection.CollectionService;
import net.kawinski.collecting.service.element.ElementService;
import net.kawinski.collecting.service.imgsearch.ImageSynchronizationTask;
import net.kawinski.collecting.service.media.MediaService;
import net.kawinski.collecting.service.user.UserService;
import net.kawinski.collecting.startup.helpers.base.StartupObject;
import net.kawinski.collecting.startup.helpers.model.StartupCA;
import net.kawinski.logging.NkTrace;
import net.kawinski.utils.LoggingUtils;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

@SuppressWarnings({"java:S1192", "java:S1199", "java:S1481", "java:S1700", "java:S2696", "unused", "MagicNumber", "ClassHasNoToStringMethod"})
@Singleton
@Startup
@Slf4j
public class StartupInitializerBean {

    @Inject
    @Getter
    private UserService userService;

    @Inject
    @Getter
    private CategoryService categoryService;

    @Inject
    @Getter
    private CollectionService collectionService;

    @Inject
    @Getter
    private CollectionAttributeService collectionAttributeService;

    @Inject
    @Getter
    private ElementService elementService;

    @Inject
    @Getter
    private ElementAttributeService elementAttributeService;

    @Inject
    @Getter
    private AttributeService attributeService;

    @Inject
    @Getter
    private MediaService mediaService;

    @Inject
    @Getter
    private IssTaskService issTaskService;

    // Not really used but the bean should auto-register the user if it doesn't already exist
    @Inject
    @Getter
    private SearchUploadUser searchUploadUser;

    @PostConstruct
    private void startup() {

        try(final NkTrace trace = NkTrace.info(log, "testing startup")) {

            log.info("Fixing JBoss stdout");
            LoggingUtils.redirectStdoutToLogger(log);

            if(userService.findByNameOrEmail("Admin").isPresent()) {
                log.info("Database already initialized");
                return;
            }

            log.info("Initializing database");
            // I know it's bad. But it's one-time initialization only
            //noinspection AssignmentToStaticFieldFromInstanceMethod
            StartupObject.srv = this;
            final StartupCA newCA = new StartupCA();

            log.info("startup logic done, commmiting!");

        } catch(final Exception ex) {
            log.error("test startup failed with exception", ex);
        }
    }
}