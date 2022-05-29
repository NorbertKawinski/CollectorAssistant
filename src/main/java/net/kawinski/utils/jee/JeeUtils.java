package net.kawinski.utils.jee;

import org.omnifaces.util.Faces;

import java.util.Optional;

public class JeeUtils {

    public static Optional<String> getRequestParam(final String paramName) {
        return Optional.ofNullable(Faces.getRequest().getParameter(paramName));
    }

    public static String getRequestParamOrThrow(final String paramName) {
        return getRequestParam(paramName)
                .orElseThrow(() -> new IllegalStateException("Missing argument '" + paramName + "'"));
    }

    public static Optional<Integer> getRequestParamInt(final String paramName) {
        return getRequestParam(paramName)
                .map(Integer::valueOf);
    }

    public static Integer getRequestParamIntOrThrow(final String paramName) {
        return getRequestParamInt(paramName)
                .orElseThrow(() -> new IllegalStateException("Missing argument '" + paramName + "'"));
    }

    public static Optional<Long> getRequestParamLong(final String paramName) {
        return getRequestParam(paramName)
                .map(Long::valueOf);
    }

    public static Long getRequestParamLongOrThrow(final String paramName) {
        return getRequestParamLong(paramName)
                .orElseThrow(() -> new IllegalStateException("Missing argument '" + paramName + "'"));
    }

    public static Optional<Boolean> getRequestParamBool(final String paramName) {
        return getRequestParam(paramName)
                .map(Boolean::valueOf);
    }

    public static Boolean getRequestParamBoolOrThrow(final String paramName) {
        return getRequestParamBool(paramName)
                .orElseThrow(() -> new IllegalStateException("Missing argument '" + paramName + "'"));
    }


}
