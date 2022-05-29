package net.kawinski.collecting.data.repository;

import lombok.NoArgsConstructor;
import net.kawinski.collecting.data.model.CaUpload;
import net.kawinski.collecting.data.model.Collection;
import net.kawinski.collecting.data.model.IssTask;
import net.kawinski.utils.jee.NkBaseRepository;

import javax.ejb.Stateless;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

// No-args constructor is required for EJBs; injection still works fine with the @Inject constructor though (in case if needed).
@NoArgsConstructor(onConstructor_ = {@Deprecated})
@Stateless // The @Stateless annotation eliminates the need for manual transaction demarcation
public class IssTaskRepository extends NkBaseRepository<Long, IssTask> {

    public List<IssTask> findNextBatch() {
        return getEntityManager().createQuery(
                "select isst " +
                        "from IssTask isst " +
                        "order by isst.id", IssTask.class)
                .setMaxResults(100)
                .getResultList();
    }

    public void deleteBatch(List<IssTask> tasks) {
        if(tasks.isEmpty()) {
            return;
        }

        // TODO: Use Collectors.teeing()
        Long minId = tasks.stream()
                .mapToLong(IssTask::getId)
                .min()
                .orElse(0);
        Long maxId = tasks.stream()
                .mapToLong(IssTask::getId)
                .max()
                .orElse(0);

        getEntityManager().createQuery(
                "delete from IssTask " +
                        "where id >= :minId and id <= :maxId")
                .setParameter("minId", minId)
                .setParameter("maxId", maxId)
                .executeUpdate();
    }
}
