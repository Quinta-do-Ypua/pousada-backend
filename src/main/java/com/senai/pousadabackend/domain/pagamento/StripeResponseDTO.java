package com.senai.pousadabackend.domain.pagamento;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StripeResponseDTO {

    private String status;
    private String message;
    private String sessionId;
    private String sessionUrl;
}
