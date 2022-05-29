package net.kawinski.collecting.data.model;

import lombok.NoArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * This class allows us to flexibly define the credentials model.
 * We are free to change this class without actually touching the users of this class.
 * Even though we've selected very specific hashing algorithm for this model, that doesn't mean we cannot change it in the future.
 *
 * Of course, adding columns, modifying column length or changing column format requires database migration,
 * but that's still better to keep it separated in one place.
 */
@Embeddable
@NoArgsConstructor(onConstructor_ = {@Deprecated}) // For JPA only
public class Credentials implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final int HASH_LENGTH = 60; // BCrypt hashes can be up to 60 characters

    @SuppressWarnings("FieldNotUsedInToString") // Don't print passwords
    @Column(length = HASH_LENGTH, nullable = false)
    private String passwordHash;

    public Credentials(final String password) {
        passwordHash = hash(password);
    }

    public Credentials(final String password, final String salt) {
        passwordHash = hash(password, salt);
    }

    public boolean isValid(final String password) {
        return BCrypt.checkpw(password, passwordHash);
    }

    private String hash(final String password) {
        return hash(password, BCrypt.gensalt());
    }

    private String hash(final String password, final String salt) {
        return BCrypt.hashpw(password, salt);
    }

    @Override
    public String toString() {
        return "Credentials{secret=hunter2}"; // Don't print any credentials!
    }
}