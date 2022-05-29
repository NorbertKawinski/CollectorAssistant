package net.kawinski.utils.jee;

import javax.persistence.Query;

public class RepositoryUtils {
    public static void executeUpdateSingle(final Query query) {
        final int affectedRows = query.executeUpdate();
        if(affectedRows != 1)
            throw new IllegalStateException("Number of affected rows (" + affectedRows + ") != 1");
    }
}
