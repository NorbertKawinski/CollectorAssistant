package net.kawinski.collecting.service.imgsearch;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.kawinski.collecting.data.model.CaUpload;
import net.kawinski.collecting.data.model.Element;
import net.kawinski.collecting.data.model.IssTask;
import net.kawinski.collecting.data.model.User;
import net.kawinski.collecting.data.repository.CaUploadRepository;
import net.kawinski.collecting.data.repository.IssTaskRepository;
import net.kawinski.collecting.service.user.UserService;
import net.kawinski.logging.NkTrace;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

// No-args constructor is required for EJBs; injection still works fine with the @Inject constructor though (in case if needed).
@NoArgsConstructor(onConstructor_ = {@Deprecated})
@Stateless // The @Stateless annotation eliminates the need for manual transaction demarcation
@Slf4j
public class IssTaskService {

    @Inject
    private IssTaskRepository issTaskRepository;

    @Deprecated
    public Long persist(IssTask entity) {
        return issTaskRepository.persist(entity);
    }

    public void newSynchronizeTask(CaUpload upload, Element element) {
        issTaskRepository.persist(IssTask.ofTypeSynchronize(upload, element));
    }

    public void newDeleteTask(CaUpload upload) {
        issTaskRepository.persist(IssTask.ofTypeDelete(upload));
    }

    public List<IssTask> findNextBatch() {
        return issTaskRepository.findNextBatch();
    }

    public void deleteBatch(List<IssTask> tasks) {
        issTaskRepository.deleteBatch(tasks);
    }
}
