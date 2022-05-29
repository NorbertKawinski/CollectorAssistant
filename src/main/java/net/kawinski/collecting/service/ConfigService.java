package net.kawinski.collecting.service;

import lombok.NoArgsConstructor;
import net.kawinski.collecting.data.model.ConfigEntry;
import net.kawinski.collecting.data.repository.ConfigRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

// No-args constructor is required for EJBs; injection still works fine with the @Inject constructor though (in case if needed).
@NoArgsConstructor(onConstructor_ = {@Deprecated})
@Stateless // The @Stateless annotation eliminates the need for manual transaction demarcation
public class ConfigService {

    @Inject
    private ConfigRepository configRepository;

    public void createIfNotExists(final String key, final int defaultValue) {
        createIfNotExists(key, Integer.toString(defaultValue));
    }

    public void createIfNotExists(final String key, final String defaultValue) {
        final Optional<String> result = get(key);
        if(!result.isPresent()) {
            persist(new ConfigEntry(key, defaultValue));
        }
    }

    public void persist(final ConfigEntry entry) {
        configRepository.persist(entry);
    }

    public void update(final ConfigEntry entry) {
        configRepository.update(entry);
    }

    public Optional<ConfigEntry> findEntryByKey(final String key) {
        return configRepository.findEntryByKey(key);
    }

    public ConfigEntry findEntryByKeyOrThrow(final String key) {
        return configRepository.findEntryByKeyOrThrow(key);
    }

    public Optional<String> get(final String key) {
        return configRepository.findByKey(key);
    }

    public String getOrThrow(final String key) {
        return configRepository.findByKeyOrThrow(key);
    }

    public Optional<Integer> getInt(final String key) {
        return configRepository.findByKey(key)
            .map(Integer::valueOf);
    }

    public Integer getIntOrThrow(final String key) {
        final String value = configRepository.findByKeyOrThrow(key);
        return Integer.parseInt(value);
    }

    public Optional<Long> getLong(final String key) {
        return configRepository.findByKey(key)
            .map(Long::valueOf);
    }

    public Long getLongOrThrow(final String key) {
        final String value = configRepository.findByKeyOrThrow(key);
        return Long.parseLong(value);
    }

    public void set(final String key, final int value) {
        set(key, Integer.toString(value));
    }

    public void set(final String key, final long value) {
        set(key, Long.toString(value));
    }

    public void set(final String key, final String value) {
        final Optional<ConfigEntry> entryOpt = findEntryByKey(key);
        if(entryOpt.isPresent()) {
            final ConfigEntry entry = entryOpt.get();
            entry.setTheValue(value);
            configRepository.update(entry);
        } else {
            final ConfigEntry entry = new ConfigEntry(key, value);
            configRepository.persist(entry);
        }
    }
}
