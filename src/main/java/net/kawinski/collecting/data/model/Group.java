package net.kawinski.collecting.data.model;

/**
 * A simple version of permission system.
 * We distinguish only basic groups and no extra permissions.
 *
 * TODO: Add proper Group, Role, Permission, Resource entities for fully functional permission system
 */
public enum Group {
    USER, PREMIUM, ADMIN
}
