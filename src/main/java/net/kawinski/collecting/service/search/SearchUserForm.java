package net.kawinski.collecting.service.search;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SearchUserForm extends BaseSearchForm {
    private String email;
    private Boolean emailVerified;
//    private String group;
//    private Integer numCollections;
}
