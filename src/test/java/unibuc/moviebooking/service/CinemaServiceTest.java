package unibuc.moviebooking.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import unibuc.moviebooking.builder.CinemaUtil;
import unibuc.moviebooking.domain.Cinema;
import unibuc.moviebooking.dto.CinemaRequest;
import unibuc.moviebooking.repository.CinemaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CinemaServiceTest {

    @Mock
    private CinemaRepository cinemaRepository;

    @InjectMocks
    private CinemaService cinemaService;

    @Test
    @DisplayName("Create cinema - happy flow")
    void createCinemaHappyFlow() {

        // Arrange
        Cinema cinema = CinemaUtil.anCinema(0L);
        Cinema savedCinema = CinemaUtil.anCinema(9L);

        when(cinemaRepository.save(cinema)).thenReturn(savedCinema);

        // Act
        Cinema result = cinemaService.save(cinema);

        //Assert
        assertThat(result).isNotNull();

        verify(cinemaRepository, times(1)).save(cinema);
    }


    @Test
    @DisplayName("Get cinema by ID - happy flow cinema exists")
    void getCinemaByIDHappyFlow() {

        // Arrange
        List<CinemaRequest> cinemasDTO = new ArrayList<CinemaRequest>();
        cinemasDTO.add(CinemaUtil.aCinemaDto(1L));
        cinemasDTO.add(CinemaUtil.aCinemaDto(2L));


        List<Cinema> cinemas = new ArrayList<Cinema>();
        cinemas.add(CinemaUtil.anCinema(1L));
        cinemas.add(CinemaUtil.anCinema(2L));

        when(cinemaRepository.getOne(1L)).thenReturn(Optional.ofNullable(cinemas.get(0)));


        Cinema result = cinemaService.getOne(1L);


        assertEquals(1, result.getId());
        assertEquals(cinemas.get(0).getName(), result.getName());
        assertEquals(cinemas.get(0).getLocationId(), result.getLocationId());

        verify(cinemaRepository).getOne(1L);
    }

    @Test
    @DisplayName("Get cinema by ID - throws exception")
    void getCinemaByIDThrowException() {

        // Arrange
        List<CinemaRequest> cinemasDTO = new ArrayList<CinemaRequest>();
        cinemasDTO.add(CinemaUtil.aCinemaDto(1L));
        cinemasDTO.add(CinemaUtil.aCinemaDto(2L));


        List<Cinema> cinemas = new ArrayList<Cinema>();
        cinemas.add(CinemaUtil.anCinema(1L));
        cinemas.add(CinemaUtil.anCinema(2L));

        when(cinemaRepository.getOne(3L)).thenReturn(Optional.empty());


        NullPointerException exception = assertThrows(NullPointerException.class,
                () -> cinemaService.getOne(3L));


        assertEquals("Cinema not found!", exception.getMessage());

    }

    @Test
    @DisplayName("Get all cinemas - happy flow")
    void getAllCinemasHappyFlow() {

        // Arrange
        List<CinemaRequest> cinemasDTO = new ArrayList<CinemaRequest>();
        cinemasDTO.add(CinemaUtil.aCinemaDto(1L));
        cinemasDTO.add(CinemaUtil.aCinemaDto(2L));

        List<Cinema> cinemas = new ArrayList<Cinema>();
        cinemas.add(CinemaUtil.anCinema(1L));
        cinemas.add(CinemaUtil.anCinema(2L));

        when(cinemaRepository.getAll()).thenReturn(cinemas);

        // Act
        List<Cinema> result = cinemaService.getAll();

        //Assert
        assertThat(result).isNotNull();
        assertEquals(2, result.size());

        verify(cinemaRepository, times(1)).getAll();

    }

    @Test
    void deleteCinemaByIDHappyFlow() {
        // Arrange
        List<CinemaRequest> cinemasDTO = new ArrayList<CinemaRequest>();
        cinemasDTO.add(CinemaUtil.aCinemaDto(1L));
        cinemasDTO.add(CinemaUtil.aCinemaDto(2L));


        List<Cinema> cinemas = new ArrayList<Cinema>();
        cinemas.add(CinemaUtil.anCinema(1L));
        cinemas.add(CinemaUtil.anCinema(2L));

        //act
        cinemaRepository.delete(1L);

        //assert
        verify(cinemaRepository, times(1)).delete(1L);
    }

}