package vn.id.hph.kitecine.facade;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import vn.id.hph.kitecine.controller.param.ShowTimeParam;
import vn.id.hph.kitecine.controller.param.ShowTimeSearchParam;
import vn.id.hph.kitecine.controller.reponse.PageResponse;
import vn.id.hph.kitecine.entity.Auditorium;
import vn.id.hph.kitecine.entity.Cinema;
import vn.id.hph.kitecine.entity.Movie;
import vn.id.hph.kitecine.entity.PriceModel;
import vn.id.hph.kitecine.facade.dto.AuditoriumShowTimeDto;
import vn.id.hph.kitecine.facade.dto.CinemaShowTimeDto;
import vn.id.hph.kitecine.facade.dto.ShowTimeBriefDto;
import vn.id.hph.kitecine.facade.dto.ShowTimeDto;
import vn.id.hph.kitecine.mapper.ShowTimeMapper;
import vn.id.hph.kitecine.service.AuditoriumService;
import vn.id.hph.kitecine.service.CinemaService;
import vn.id.hph.kitecine.service.MovieService;
import vn.id.hph.kitecine.service.PriceModelService;
import vn.id.hph.kitecine.service.ShowTimeService;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ShowTimeFacade {
    ShowTimeService showTimeService;
    AuditoriumService auditoriumService;
    PriceModelService priceModelService;
    MovieService movieService;

    ShowTimeMapper showTimeMapper;
    private final CinemaService cinemaService;

    @PreAuthorize("hasRole('ADMIN')")
    public ShowTimeDto createShowTime(ShowTimeParam param) {
        Auditorium auditorium = auditoriumService.get(param.auditoriumId());
        PriceModel priceModel = priceModelService.get(param.priceModelId());
        Movie movie = movieService.get(param.movieId());

        var entity = showTimeService.create(movie, auditorium, priceModel, param);
        return showTimeMapper.toShowTimeDto(entity);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<CinemaShowTimeDto> getShowTimes(Long movieId, LocalDate date) {
        var showTimes = showTimeService.getByMovieIdAndDate(movieId, date);

        var cinemas = cinemaService.getAll();
        var auditoriums = auditoriumService.getAll();

        List<CinemaShowTimeDto> returnList = new ArrayList<>();
        for (Cinema cinema : cinemas) {
            CinemaShowTimeDto cinemaShowTimeDto = new CinemaShowTimeDto();
            cinemaShowTimeDto.setId(cinema.getId());
            cinemaShowTimeDto.setName(cinema.getName());
            cinemaShowTimeDto.setAddress(cinema.getAddress());

            List<AuditoriumShowTimeDto> auditoriumDtos = new ArrayList<>();
            for (Auditorium auditorium : auditoriums) {
                if (auditorium.getCinema().getId().equals(cinema.getId())) {
                    AuditoriumShowTimeDto auditoriumShowTimeDto = new AuditoriumShowTimeDto();
                    auditoriumShowTimeDto.setId(auditorium.getId());
                    auditoriumShowTimeDto.setName(auditorium.getName());

                    List<ShowTimeBriefDto> showTimeDtos = new ArrayList<>();
                    for (var showTime : showTimes) {
                        if (showTime.getAuditorium().getId().equals(auditorium.getId())) {
                            ShowTimeBriefDto showTimeDto = showTimeMapper.toShowTimeBriefDto(showTime);
                            showTimeDtos.add(showTimeDto);
                        }
                    }
                    auditoriumShowTimeDto.setShowTimes(showTimeDtos);
                    auditoriumDtos.add(auditoriumShowTimeDto);

                    // Remove the show times for this auditorium from the list to avoid processing them again
                    showTimes.removeIf(
                            showTime -> showTime.getAuditorium().getId().equals(auditorium.getId()));
                }
            }

            cinemaShowTimeDto.setAuditoriums(auditoriumDtos);
            returnList.add(cinemaShowTimeDto);

            // Remove the auditoriums for this cinema from the list to avoid processing them again
            auditoriums.removeIf(auditorium -> auditorium.getCinema().getId().equals(cinema.getId()));
        }

        return returnList;
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<ShowTimeDto> getShowTimesByAuditorium(Long auditoriumId, LocalDate date) {
        var list = showTimeService.getByAuditoriumId(auditoriumId, date);
        return showTimeMapper.toShowTimeDtoList(list);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public PageResponse<ShowTimeDto> searchShowTimes(ShowTimeSearchParam param) {
        var page = showTimeService.search(param);
        var dtos = showTimeMapper.toShowTimeDtoList(page.getData());

        return PageResponse.<ShowTimeDto>builder()
                .data(dtos)
                .pageNumber(page.getPageNumber())
                .pageSize(page.getPageSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public ShowTimeDto updateShowTime(Long id, ShowTimeParam param) {
        Auditorium auditorium = auditoriumService.get(param.auditoriumId());
        PriceModel priceModel = priceModelService.get(param.priceModelId());
        Movie movie = movieService.get(param.movieId());

        var entity = showTimeService.update(id, movie, auditorium, priceModel, param);
        return showTimeMapper.toShowTimeDto(entity);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteShowTime(Long id) {
        showTimeService.delete(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public ShowTimeDto getShowTime(Long id) {
        var entity = showTimeService.get(id);
        return showTimeMapper.toShowTimeDto(entity);
    }
}
