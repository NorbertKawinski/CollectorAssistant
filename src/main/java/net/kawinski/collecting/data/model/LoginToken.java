package net.kawinski.collecting.data.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.omnifaces.persistence.model.GeneratedIdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.time.Instant;

import static javax.persistence.EnumType.STRING;
import static org.omnifaces.utils.security.MessageDigests.digest;

@Entity(name = "LoginToken")
@NoArgsConstructor(onConstructor_ = {@Deprecated}) // For JPA only
@ToString(onlyExplicitlyIncluded = true, callSuper = true)
public class LoginToken extends GeneratedIdEntity<Long> {

    private static final int HASH_LENGTH = 32;

    public enum TokenType {
        CONFIRM_MAIL,
        REMEMBER_ME,
        API,
        RESET_PASSWORD
    }

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @Getter
    @ToString.Include
    private User user;

    @Enumerated(STRING)
    @Getter
    @ToString.Include
    private TokenType type;

    /**
     *
     */
    @NotNull
    @Column(length = HASH_LENGTH, nullable = false, unique = true)
    @Getter
    private String tokenHash;

    @NotNull
    @Column(nullable = false)
    @Getter
    @ToString.Include
    private @NotNull Instant created;

    @NotNull
    @Column(nullable = false)
    @Getter
    @ToString.Include
    private @NotNull Instant expires;

    // TODO: Add token name so that user can manage tokens more easily (distinguish which token is used by whom)

    public LoginToken(final User user, final TokenType type, final String rawToken, final Duration expiresIn) {
        this(user, type, hashTokenBytes(rawToken), expiresIn);
    }

    public LoginToken(final User user, final TokenType type, final byte[] tokenHash, final Duration expiresIn) {
        this.user = user;
        this.type = type;
        this.tokenHash = new String(tokenHash);
        created = Instant.now();
        expires = created.plus(expiresIn);
    }

    public static byte[] hashTokenBytes(final String rawToken) {
        final String MESSAGE_DIGEST_ALGORITHM = "SHA-256";
        return digest(rawToken, MESSAGE_DIGEST_ALGORITHM);
    }

    public static String hashToken(final String rawToken) {
        return new String(hashTokenBytes(rawToken));
    }
}
