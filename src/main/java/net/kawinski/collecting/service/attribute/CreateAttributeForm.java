package net.kawinski.collecting.service.attribute;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAttributeForm {
    @NotNull
    private Long templateId;

    @NotNull
    private Long targetId;

    private String value;
}
