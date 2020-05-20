package com.m3alem.m3alem_back_end.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseConfirmationDTO extends CourseInputDTO {
    private Boolean confirmed;
}
