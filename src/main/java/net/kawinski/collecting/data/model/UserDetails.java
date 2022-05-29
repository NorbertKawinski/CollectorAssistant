package net.kawinski.collecting.data.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.omnifaces.persistence.model.GeneratedIdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Size;

@Entity(name = "UserDetails")
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true, callSuper = true)
@SuppressWarnings("java:S2160") // "override equals" --> Equals is already comparing IDs and that's fine for database entities.
public class UserDetails extends GeneratedIdEntity<Long> {

    private static final long serialVersionUID = 1L;

    public static final int ABOUT_ME_LEN = 1024;
    public static final int DEFAULT_LEN = 100;

    @Size(max = DEFAULT_LEN)
    @Column(length = DEFAULT_LEN)
    private String contactEmail;

    @Size(max = DEFAULT_LEN)
    @Column(length = DEFAULT_LEN)
    private String facebook;

    @Size(max = DEFAULT_LEN)
    @Column(length = DEFAULT_LEN)
    private String skype;

    @Size(max = ABOUT_ME_LEN)
    @Column(length = ABOUT_ME_LEN)
    private String aboutMe;
}
