package net.kawinski.collecting.data.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.omnifaces.persistence.model.GeneratedIdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@SuppressWarnings("java:S2160") // "override equals" --> Equals is already comparing IDs and that's fine for database entities.
@Entity(name = "IssTask")
@NoArgsConstructor(onConstructor_ = {@Deprecated}) // For JPA only
@ToString(onlyExplicitlyIncluded = true, callSuper = true)
public class IssTask extends GeneratedIdEntity<Long> {

    /**
     * We need to keep the ID because the referenced CaUpload might not exist at this point anymore
     */
    @Getter
    @ToString.Include
    private Long uploadId;

    @Getter
    @Enumerated(EnumType.STRING)
    @ToString.Include
    private IssTaskType type;

    @Getter
    private Long elementId;

    private IssTask(CaUpload upload, IssTaskType type, Long elementId) {
        this.uploadId = upload.getId();
        this.type = type;
        this.elementId = elementId;
    }

    public static IssTask ofTypeSynchronize(CaUpload upload, Element element) {
        return new IssTask(upload, IssTaskType.SYNCHRONIZE, element.getId());
    }

    public static IssTask ofTypeDelete(CaUpload upload) {
        return new IssTask(upload, IssTaskType.DELETE, null);
    }
}
