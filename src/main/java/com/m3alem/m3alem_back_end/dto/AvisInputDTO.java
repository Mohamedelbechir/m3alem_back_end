package com.m3alem.m3alem_back_end.dto;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvisInputDTO {

    @NotNull
    private Long idDriver;
    @NotNull
    private Long idPassager;
    private Integer nbEtoile = 0;
    private String message;

}