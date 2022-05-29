package net.kawinski.collecting.service.search;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SearchElementForm extends SearchForm {
    private String imageFilename;
}
