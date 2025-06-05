package com.senai.pousadabackend.domain.amenidade;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AmenidadeDto {

    private Long id;

    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    private String icone;

}
