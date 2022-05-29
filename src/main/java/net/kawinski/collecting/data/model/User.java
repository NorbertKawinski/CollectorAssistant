package net.kawinski.collecting.data.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.omnifaces.persistence.model.GeneratedIdEntity;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.EAGER;

@Entity(name = "User")
@NoArgsConstructor(onConstructor_ = {@Deprecated}) // For JPA only
@ToString(onlyExplicitlyIncluded = true, callSuper = true)
@SuppressWarnings("java:S2160") // "override equals" --> Equals is already comparing IDs and that's fine for database entities.
public class User extends GeneratedIdEntity<Long> {

    private static final long serialVersionUID = 1L;

    public static final int MIN_NAME_LENGTH = 3; // TODO: Przeniesc w odpowiednie miejsce (biznesowe)?
    public static final int MAX_NAME_LENGTH = 32;
    public static final String NAME_PATTERN = "[^\\s]+";

    public static final int MIN_PASSWORD_LENGTH = 3; // This is password policy, adjust if needed. It should be placed somewhere else though. But for the sake of POC it's good enough
    public static final int MAX_PASSWORD_LENGTH = 72; // should be enough. bcrypt does not allow longer passwords anyway

    // 254 seems to be the official limit for email length
    // But MySQL doesn't like long indexes (over 767 bytes), so we have to make it a bit shorter.
    // Hopefully nobody with such long address will use this app
    public static final int MAX_EMAIL_LENGTH = 96;

    @NotEmpty
    @Size(min = MIN_NAME_LENGTH, max = MAX_NAME_LENGTH)
    @Pattern(regexp = NAME_PATTERN)
    @Column(length = MAX_NAME_LENGTH, nullable = false, unique = true)
    @Getter
    @Setter
    @ToString.Include
    private String name;

    @NotEmpty
    @Email
    @Column(length = MAX_EMAIL_LENGTH, nullable = false, unique = true)
    @Getter
    @Setter
    @ToString.Include
    private String email;

    @NotNull
    @Column(nullable = false)
    @Getter
    @Setter
    @ToString.Include
    private boolean emailVerified;

    @JsonbTransient
    @NotNull
    @Embedded
    @Getter
    @Setter
    private Credentials credentials;

    @JsonbTransient
    @OneToMany(mappedBy = "user", cascade = ALL, orphanRemoval = true)
    @Getter
    private List<LoginToken> loginTokens = new ArrayList<>();

    @ElementCollection(fetch = EAGER)
    @Enumerated(STRING)
    @Getter
    @ToString.Include
    private final Set<Group> groups = new HashSet<>();

    @JsonbTransient
    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    @Getter
    private List<Collection> collections;

    @JsonbTransient
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Getter
    private UserDetails userDetails = new UserDetails();

    @JsonbTransient
    @Embedded
    @Getter
    @Setter
    private ImageModel image = new ImageModel();

    public User(final String name, final String email, final String password) {
        this(name, email, new Credentials(password));
    }

    public User(final String name, final String email, final Credentials credentials) {
        this.name = name;
        this.email = email;
        this.credentials = credentials;
        this.emailVerified = true; // TODO: false
    }

    public void addLoginToken(final LoginToken loginToken) {
        loginTokens.add(loginToken);
    }

    public void removeLoginToken(final LoginToken loginToken) {
        loginTokens.remove(loginToken);
    }

    public void addGroups(final Group... newGroups) {
        getGroups().addAll(Arrays.asList(newGroups));
    }

    public void removeGroup(final Group group) {
        getGroups().remove(group);
    }

    public boolean isInGroup(final Group group) {
        return groups.contains(group);
    }

    public boolean equalsId(final User other) {
        return getId().equals(other.getId());
    }
}
