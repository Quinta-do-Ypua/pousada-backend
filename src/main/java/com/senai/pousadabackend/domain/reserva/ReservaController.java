package com.senai.pousadabackend.domain.reserva;

import com.senai.pousadabackend.domain.reserva.dto.ReservaDTO;
import com.senai.pousadabackend.domain.reserva.dto.ReservaResumidaDto;
import jakarta.transaction.Transactional;
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
    @Transactional
    public ReservaDTO cadastrar(@RequestBody ReservaResumidaDto reservaResumidaDto) {
        return reservaMapper.toDTO(reservaService.salvar(reservaResumidaMapper.toReserva(reservaResumidaDto)));
    }

    @PatchMapping("/{id}/cancelar")
    @Transactional
    public ReservaDTO cancelarReserva(@PathVariable Long id) {
        return reservaMapper.toDTO(reservaService.cancelarPorId(id));
    }

    @GetMapping(params = "search")
    @Transactional
    public Page<ReservaDTO> buscarPorSpecification(@RequestParam(name = "search") String search, Pageable pageable) {
        return reservaService.buscarPorSpecification(search, pageable).map(reservaMapper::toDTO);
    }

    @GetMapping
    @Transactional
    public Page<ReservaDTO> listarPaginado(@PageableDefault(size = 15) Pageable pageable) {
        return reservaService.listarPaginado(pageable).map(reservaMapper::toDTO);
    }

    @GetMapping("/{id}")
    @Transactional
    public ReservaDTO buscarPorId(@PathVariable(name = "id") Long id) {
        return reservaMapper.toDTO(reservaService.buscarPorId(id));
    }

    @PutMapping
    @Transactional
    public ReservaDTO alterar(@RequestBody ReservaResumidaDto dto) {
        return reservaMapper.toDTO(reservaService.salvar(reservaResumidaMapper.toReserva(dto)));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ReservaDTO excluir(@PathVariable Long id) {
        ReservaDTO dto = reservaMapper.toDTO(reservaService.buscarPorId(id));
        reservaService.excluir(id);
        return dto;
    }

}
