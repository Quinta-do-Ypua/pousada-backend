package com.senai.pousadabackend.domain.parametro;

import com.senai.pousadabackend.core.entity.EntityAudit;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalTime;

@Entity
@Table(name = "parametro_reserva")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class ParametroReserva extends EntityAudit {

    @Id
    @Column(name = "id")
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "bloquear_reserva_com_pendencia", nullable = false)
    @Builder.Default
    private Boolean bloquearReservaComPendencia = true;

    @Column(name = "multa_cancelamento_ativa", nullable = false)
    @Builder.Default
    private Boolean multaCancelamentoAtiva = false;

    @Column(name = "max_reservas_ativas_por_usuario", nullable = false)
    @Builder.Default
    private Integer maxReservasAtivasPorUsuario = 1;

    @Column(name = "tempo_entre_reservas_dias", nullable = false)
    @Builder.Default
    private Integer tempoEntreReservasDias = 0;

    @Column(name = "prazo_maximo_cancelamento_dias", nullable = false)
    @Builder.Default
    private Integer prazoMaximoCancelamentoDias = 7;

    @Column(name = "tempo_minimo_para_reserva_dias", nullable = false)
    @Builder.Default
    private Integer tempoMinimoParaReservaDias = 1;

    @Column(name = "duracao_minima_dias", nullable = false)
    @Builder.Default
    private Integer duracaoMinimaDias = 1;

    @Column(name = "duracao_maxima_dias", nullable = false)
    @Builder.Default
    private Integer duracaoMaximaDias = 30;

    @Column(name = "horario_check_in", nullable = false)
    @Builder.Default
    private LocalTime horarioCheckIn = LocalTime.of(14, 0);

    @Column(name = "horario_check_out", nullable = false)
    @Builder.Default
    private LocalTime horarioCheckOut = LocalTime.of(12, 0);

    @Column(name = "percentual_multa_cancelamento", nullable = false, precision = 5, scale = 2)
    @Builder.Default
    private BigDecimal percentualMultaCancelamento = new BigDecimal("20.00");

    @Version
    private Long version;

    public static ParametroReserva comDefaults() {
        return ParametroReserva.builder()
                .id(1L)
                .build();
    }

}
