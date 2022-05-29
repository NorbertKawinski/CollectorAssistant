package net.kawinski.collecting.data.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Embeddable
//@NoArgsConstructor(onConstructor_ = {@Deprecated}) // For JPA only
@NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
public class ImageModel {

    /**
     * Forces this object to be not null.
     * Otherwise when all fields are null, JPA (Hibernate) might create null object
     */
    private int dummy = 0;

    @ManyToOne(fetch = FetchType.EAGER)
    @Getter
    @Setter
    @ToString.Include
    private CaUpload image = null;

    @ManyToOne(fetch = FetchType.EAGER)
    @Getter
    @Setter
    @ToString.Include
    private CaUpload icon = null;

    public ImageModel(ImageModel copy) {
        this.image = copy.getImage();
        this.icon = copy.getIcon();
    }

    public CaUpload getImageOrIcon() {
        if(image != null)
            return image;
        return icon;
    }

    public CaUpload getIconOrImage() {
        if(icon != null)
            return icon;
        return image;
    }

    public boolean getHasImageOrIcon() {
        return icon != null || image != null;
    }
}
