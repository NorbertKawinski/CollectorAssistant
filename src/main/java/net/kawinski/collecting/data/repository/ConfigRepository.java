package net.kawinski.collecting.data.repository;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.kawinski.collecting.data.model.ConfigEntry;
import net.kawinski.utils.jee.NkBaseRepository;

import javax.ejb.Stateless;
import java.util.Optional;

// No-args constructor is required for EJBs; injection still works fine with the @Inject constructor though (in case if needed).
@NoArgsConstructor(onConstructor_ = {@Deprecated})
@Stateless // The @Stateless annotation eliminates the need for manual transaction demarcation
@Slf4j
public class ConfigRepository extends NkBaseRepository<Long, ConfigEntry> {

    public Optional<ConfigEntry> findEntryByKey(final String key) {
        return getEntityManager().createQuery("select entry from ConfigEntry entry " +
            "where entry.theKey = :key", ConfigEntry.class)
            .setParameter("key", key)
            .getResultStream()
            .findFirst();
    }

    public ConfigEntry findEntryByKeyOrThrow(final String key) {
        return findEntryByKey(key)
            .orElseThrow(() -> new IllegalStateException("Couldn't find " + eClass + " with key: " + key));
    }

    public Optional<String> findByKey(final String key) {
        return findEntryByKey(key)
            .map(ConfigEntry::getTheValue);
    }

    public String findByKeyOrThrow(final String key) {
        return findByKey(key)
            .orElseThrow(() -> new IllegalStateException("Couldn't find " + eClass + " with key: " + key));
    }
}
