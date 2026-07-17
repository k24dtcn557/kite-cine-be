package vn.id.hph.kitecine.facade;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import vn.id.hph.kitecine.controller.param.AddRowParam;
import vn.id.hph.kitecine.controller.param.AuditoriumParam;
import vn.id.hph.kitecine.controller.param.AuditoriumSearchParam;
import vn.id.hph.kitecine.controller.param.ChangeTypeParam;
import vn.id.hph.kitecine.controller.param.CinemaParam;
import vn.id.hph.kitecine.controller.param.KeywordStatusSearchParam;
import vn.id.hph.kitecine.controller.param.SeatParam;
import vn.id.hph.kitecine.controller.param.SeatSearchParam;
import vn.id.hph.kitecine.controller.reponse.PageResponse;
import vn.id.hph.kitecine.entity.Seat;
import vn.id.hph.kitecine.facade.dto.AuditoriumDto;
import vn.id.hph.kitecine.facade.dto.CinemaDto;
import vn.id.hph.kitecine.facade.dto.SeatDto;
import vn.id.hph.kitecine.facade.dto.SeatRowDto;
import vn.id.hph.kitecine.mapper.AuditoriumMapper;
import vn.id.hph.kitecine.mapper.CinemaMapper;
import vn.id.hph.kitecine.mapper.SeatMapper;
import vn.id.hph.kitecine.service.AuditoriumService;
import vn.id.hph.kitecine.service.CinemaService;
import vn.id.hph.kitecine.service.SeatService;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CinemaFacade {
    CinemaService cinemaService;
    AuditoriumService auditoriumService;
    SeatService seatService;

    CinemaMapper cinemaMapper;
    AuditoriumMapper auditoriumMapper;
    SeatMapper seatMapper;

    @PreAuthorize("hasRole('ADMIN')")
    public CinemaDto createCinema(CinemaParam param) {
        var cinema = cinemaService.create(param);
        return cinemaMapper.toCinemaDto(cinema);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public PageResponse<CinemaDto> searchCinemas(KeywordStatusSearchParam param) {

        var cinemaPageResponse = cinemaService.search(param);
        var cinemaDtos = cinemaMapper.toCinemaDtoList(cinemaPageResponse.getData());

        return PageResponse.<CinemaDto>builder()
                .data(cinemaDtos)
                .pageNumber(cinemaPageResponse.getPageNumber())
                .pageSize(cinemaPageResponse.getPageSize())
                .totalElements(cinemaPageResponse.getTotalElements())
                .totalPages(cinemaPageResponse.getTotalPages())
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public CinemaDto updateCinema(Long cinemaId, CinemaParam param) {
        var cinema = cinemaService.update(cinemaId, param);
        return cinemaMapper.toCinemaDto(cinema);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteCinema(Long cinemaId) {
        cinemaService.delete(cinemaId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public CinemaDto getCinema(Long cinemaId) {
        var cinema = cinemaService.get(cinemaId);
        return cinemaMapper.toCinemaDto(cinema);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void activateCinema(Long cinemaId) {
        cinemaService.activate(cinemaId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deactivateCinema(Long cinemaId) {
        cinemaService.deactivate(cinemaId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public AuditoriumDto createAuditorium(AuditoriumParam param) {
        var cinema = cinemaService.get(param.cinemaId());
        var auditorium = auditoriumService.create(cinema, param);
        return auditoriumMapper.toAuditoriumDto(auditorium);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public AuditoriumDto updateAuditorium(Long auditoriumId, AuditoriumParam param) {
        var cinema = cinemaService.get(param.cinemaId());
        var auditorium = auditoriumService.update(auditoriumId, cinema, param);
        return auditoriumMapper.toAuditoriumDto(auditorium);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public AuditoriumDto getAuditorium(Long auditoriumId) {
        var auditorium = auditoriumService.get(auditoriumId);
        return auditoriumMapper.toAuditoriumDto(auditorium);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteAuditorium(Long auditoriumId) {
        auditoriumService.delete(auditoriumId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void activateAuditorium(Long auditoriumId) {
        auditoriumService.activate(auditoriumId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deactivateAuditorium(Long auditoriumId) {
        auditoriumService.deactivate(auditoriumId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public PageResponse<AuditoriumDto> searchAuditoriums(AuditoriumSearchParam param) {
        var auditoriumPageResponse = auditoriumService.search(param);
        var auditoriumDtos = auditoriumMapper.toAuditoriumDtoList(auditoriumPageResponse.getData());
        return PageResponse.<AuditoriumDto>builder()
                .data(auditoriumDtos)
                .pageNumber(auditoriumPageResponse.getPageNumber())
                .pageSize(auditoriumPageResponse.getPageSize())
                .totalElements(auditoriumPageResponse.getTotalElements())
                .totalPages(auditoriumPageResponse.getTotalPages())
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public SeatDto createSeat(SeatParam param) {
        var auditorium = auditoriumService.get(param.auditoriumId());
        var seat = seatService.create(auditorium, param);
        return seatMapper.toSeatDto(seat);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public SeatDto updateSeat(Long seatId, SeatParam param) {
        var auditorium = auditoriumService.get(param.auditoriumId());
        var seat = seatService.update(seatId, auditorium, param);
        return seatMapper.toSeatDto(seat);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public SeatDto getSeat(Long seatId) {
        var seat = seatService.get(seatId);
        return seatMapper.toSeatDto(seat);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteSeat(Long seatId) {
        seatService.delete(seatId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public PageResponse<SeatDto> searchSeats(SeatSearchParam param) {
        var auditoriumPageResponse = seatService.search(param);
        var seatDtos = seatMapper.toSeatDtoList(auditoriumPageResponse.getData());
        return PageResponse.<SeatDto>builder()
                .data(seatDtos)
                .pageNumber(auditoriumPageResponse.getPageNumber())
                .pageSize(auditoriumPageResponse.getPageSize())
                .totalElements(auditoriumPageResponse.getTotalElements())
                .totalPages(auditoriumPageResponse.getTotalPages())
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<SeatDto> addRow(AddRowParam param) {
        var auditorium = auditoriumService.get(param.auditoriumId());
        var seats = seatService.addRow(auditorium, param);
        return seatMapper.toSeatDtoList(seats);
    }

    public List<SeatRowDto> getAuditoriumSeats(Long id) {
        var auditorium = auditoriumService.get(id);
        var seats = seatService.getByAuditoriumId(auditorium.getId());
        seats.sort(Comparator.comparing(Seat::getSeatNumber));

        Map<String, List<SeatDto>> seatRowMap = new HashMap<>();
        seats.forEach(seat -> {
            List<SeatDto> rowSeat = seatRowMap.get(seat.getRowLetter());
            if (rowSeat == null) {
                rowSeat = new ArrayList<>();
            }
            rowSeat.add(seatMapper.toSeatDto(seat));
            seatRowMap.put(seat.getRowLetter(), rowSeat);
        });

        return seatRowMap.entrySet().stream()
                .map(entry -> new SeatRowDto(entry.getKey(), entry.getValue()))
                .sorted(Comparator.comparing(SeatRowDto::rowLetter))
                .collect(Collectors.toList());
    }

    public List<CinemaDto> getCinemas() {
        var cinemas = cinemaService.getAll();
        return cinemas.stream()
                .map(cinema -> new CinemaDto(
                        cinema.getId(),
                        cinema.getName(),
                        cinema.getAddress(),
                        auditoriumService.getNumberOfAuditoriums(cinema.getId()),
                        cinema.getCreatedAt(),
                        cinema.getUpdatedAt(),
                        cinema.getStatus()))
                .collect(Collectors.toList());
    }

    public void deleteSeats(List<Long> ids) {}

    public void changeSeatType(ChangeTypeParam param) {
        seatService.changeType(param);
    }
}
