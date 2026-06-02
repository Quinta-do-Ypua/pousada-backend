package com.senai.pousadabackend.domain.reserva;

import com.senai.pousadabackend.domain.reserva.dto.ReservaDTO;
import com.senai.pousadabackend.domain.reserva.dto.ReservaResumidaDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("reservas")
public class ReservaController {

    private final ReservaService reservaService;
    private final ReservaResumidaMapper reservaResumidaMapper;
    private final ReservaMapper reservaMapper;

    public ReservaController(ReservaService reservaService,
                             ReservaResumidaMapper reservaResumidaMapper,
                             ReservaMapper reservaMapper) {
        this.reservaService = reservaService;
        this.reservaResumidaMapper = reservaResumidaMapper;
        this.reservaMapper = reservaMapper;
    }

    @PostMapping
    public ReservaDTO cadastrar(@RequestBody ReservaResumidaDto reservaResumidaDto) {
        return reservaMapper.toDTO(reservaService.salvar(reservaResumidaMapper.toReserva(reservaResumidaDto)));
    }

    @PatchMapping("/{id}/cancelar")
    public ReservaDTO cancelarReserva(@PathVariable Long id) {
        return reservaMapper.toDTO(reservaService.cancelarPorId(id));
    }

    @GetMapping(params = "search")
    public Page<ReservaDTO> buscarPorSpecification(@RequestParam(name = "search") String search, Pageable pageable) {
        return reservaService.buscarPorSpecification(search, pageable).map(reservaMapper::toDTO);
    }

    @GetMapping
    public Page<ReservaDTO> listarPaginado(@PageableDefault(size = 15) Pageable pageable) {
        return reservaService.listarPaginado(pageable).map(reservaMapper::toDTO);
    }

    @GetMapping("/{id}")
    public ReservaDTO buscarPorId(@PathVariable(name = "id") Long id) {
        return reservaMapper.toDTO(reservaService.buscarPorId(id));
    }

    @PutMapping
    public ReservaDTO alterar(@RequestBody ReservaResumidaDto dto) {
        return reservaMapper.toDTO(reservaService.salvar(reservaResumidaMapper.toReserva(dto)));
    }

}
