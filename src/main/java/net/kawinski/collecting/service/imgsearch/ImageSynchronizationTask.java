package net.kawinski.collecting.service.imgsearch;

import lombok.extern.slf4j.Slf4j;
import net.kawinski.collecting.Resources;
import net.kawinski.collecting.data.model.CaUpload;
import net.kawinski.collecting.data.model.Element;
import net.kawinski.collecting.data.model.IssTask;
import net.kawinski.collecting.data.repository.CaUploadRepository;
import net.kawinski.collecting.data.repository.IssTaskRepository;
import net.kawinski.collecting.service.ConfigService;
import net.kawinski.collecting.service.element.ElementService;
import net.kawinski.collecting.service.media.MediaService;
import net.kawinski.logging.NkTrace;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.concurrent.ManagedScheduledExecutorService;
import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Singleton
@Startup
@Slf4j
public class ImageSynchronizationTask {

    @Resource
    private ManagedScheduledExecutorService scheduledExecutorService;

    @Inject
    private MediaService mediaService;

    @Inject
    private ImageSearchService imageSearchService;

    @Inject
    private IssTaskService issTaskService;

    private ScheduledFuture<?> synchronizationTask;
    private boolean destroyed = false;

    @PostConstruct
    public void setup() {
        synchronizationTask = scheduledExecutorService.scheduleAtFixedRate(this::synchronizeImages, 100, 100, TimeUnit.SECONDS);
    }

    @PreDestroy
    public void destroy() {
        if(synchronizationTask != null) {
            synchronizationTask.cancel(true);
        }
        destroyed = true;
    }

    public void synchronizeImages() {
        if(destroyed) {
            // It looks like WildFly sucks at redeploying. It won't close executor,
            // resulting in this method being called from old deployment
            // and because of that the old method using detached database connection (in simple words: throws exceptions when used)
            // I want to be able to redeploy for development so this workaround (to ignore such executions) has been made.
//                log.warn("THIS TASK IS DESTROYED. SHOULDN'T BE CALLED!");
            return;
        }

        try(NkTrace trace = NkTrace.info(log, "ISS synchronization tick")) {
            List<IssTask> toProcess = issTaskService.findNextBatch();
            if(toProcess.isEmpty()) {
                trace.setExitMsg("ISS synchronization no records to synchronize");
                return;
            }

            List<IssTask> processedTasks = new ArrayList<>();
            for(IssTask task : toProcess) {
                if(destroyed) {
                    log.info("Scheduler is cancelled. Aborting further processing");
                    break;
                }

                try {
                    process(task);
                    processedTasks.add(task);
                } catch (Exception ex) {
                    log.error("Failed to process task: " + task, ex);
                    break;
                }
            }

            issTaskService.deleteBatch(processedTasks);
        } catch (final Exception ex) {
            log.error("Synchronization failed. Will try again next time", ex);
        }
    }

    private void process(IssTask task) throws IOException {
        switch (task.getType()) {
            case SYNCHRONIZE:
                synchronize(task.getUploadId(), task.getElementId());
                break;
            case DELETE:
                delete(task.getUploadId());
                break;
        }
    }

    private void synchronize(Long uploadId, Long elementId) throws IOException {
        Optional<CaUpload> uploadOpt = mediaService.findById(uploadId);
        if(!uploadOpt.isPresent()) {
            // Upload already deleted, nothing to synchronize
            return;
        }
        CaUpload upload = uploadOpt.get();

        final File imageFile = mediaService.getUploadFile(upload);
        final String imgUrl = Resources.CA_BASE_URL + mediaService.getUrlFor(upload);
        imageSearchService.upload(imageFile, uploadId, elementId);
    }

    private void delete(Long uploadId) throws IOException {
        imageSearchService.delete(uploadId);
    }

}
