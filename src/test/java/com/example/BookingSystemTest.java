package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class BookingSystemTest {

    private TimeProvider timeProvider;
    private RoomRepository roomRepository;
    private NotificationService notificationService;

    private BookingSystem bookingSystem;


    @BeforeEach
    void setUp() {
        timeProvider = mock(TimeProvider.class);
        roomRepository = mock(RoomRepository.class);
        notificationService = mock(NotificationService.class);

        bookingSystem = new BookingSystem(
                timeProvider,
                roomRepository,
                notificationService
        );
    }


    @Test
    @DisplayName("Check that room can be successfully booked")
    void bookRoomSuccessfully(){
        //Arrange
        LocalDateTime currentTime = LocalDateTime.of(2026, 2, 8, 10, 0);
        LocalDateTime startTime = currentTime.plusHours(1);
        LocalDateTime endTime = startTime.plusHours(2);
        when(timeProvider.getCurrentTime()).thenReturn(currentTime);

        Room room = mock(Room.class);

        when(roomRepository.findById("1")).thenReturn(Optional.of(room));

        when(room.isAvailable(startTime, endTime)).thenReturn(true);

        //Act
        boolean result = bookingSystem.bookRoom("1", startTime, endTime);


        //Assert

        assertThat(result).isTrue();

    }

    @ParameterizedTest
    @MethodSource("invalidBookingArguments")
    @DisplayName("Should throw exception if startTime, endTime or roomId are null")
    void shouldThrowExceptionIfStartEndOrRoomIdIsNull(LocalDateTime startTime,
                                                      LocalDateTime endTime,
                                                      String roomId){
        //act stoppas in asserten
        assertThatThrownBy(() ->
                    bookingSystem.bookRoom(roomId, startTime, endTime)
                );
    }

    //Arrange
    static Stream<Arguments> invalidBookingArguments(){
        LocalDateTime validTime = LocalDateTime.of(2026, 2, 8, 10, 0);
        return Stream.of(
                Arguments.of(null, validTime, "1"),
                Arguments.of(validTime, null, "1"),
                Arguments.of(validTime, validTime.plusHours(1), null)
        );
    }


    @Test
    @DisplayName("Should throw exception when startTime before currentTime")
    void shouldThrowExceptionStartBeforeCurrentTime(){
        //Arrange
        LocalDateTime currentTime = LocalDateTime.of(2026, 2, 8, 10, 0);
        when(timeProvider.getCurrentTime()).thenReturn(currentTime);
        LocalDateTime startTime = currentTime.minusMinutes(1);
        LocalDateTime endTime = startTime.plusHours(1);


        //Act // Assert
        assertThatThrownBy(() ->
                    bookingSystem.bookRoom("1", startTime, endTime)
                );
    }

    @Test
    @DisplayName("Should throw exception if endTime is before startTime")
    void shouldThrowExceptionIfEndTimeIsBeforeStartTime(){
        //arrange
        LocalDateTime currentTime = LocalDateTime.of(2026, 2, 8, 10, 0);
        LocalDateTime startTime = currentTime.plusHours(1);
        LocalDateTime endTime = startTime.minusMinutes(1);

        when(timeProvider.getCurrentTime()).thenReturn(currentTime);


        //act and assert
        assertThatThrownBy(() ->
                    bookingSystem.bookRoom("1", startTime, endTime)
                );
    }

    @Test
    @DisplayName("Should return false for no availbale rooms")
    void shouldReturnFalseForNoAvailableRooms(){
        //Arrange
        LocalDateTime currentTime = LocalDateTime.of(2026, 2, 8, 10, 0);
        LocalDateTime startTime = currentTime.plusHours(1);
        LocalDateTime endTime = startTime.plusHours(2);

        when(timeProvider.getCurrentTime()).thenReturn(currentTime);

        Room room = mock(Room.class);
        when(roomRepository.findById("1")).thenReturn(Optional.of(room));

        when(room.isAvailable(startTime, endTime)).thenReturn(false);

        //Act
        boolean result = bookingSystem.bookRoom("1", endTime, endTime);

        //Assert-verifiera
        assertThat(result).isFalse();

    }


    @Test
    @DisplayName("Should return all available rooms")
    void shouldReturnAllAvailableRooms(){
        //arrange
        //2. 3.
        LocalDateTime startTime = LocalDateTime.of(2026, 2, 8, 10, 0);
        LocalDateTime endTime = startTime.plusHours(1);

        //5.
        Room room1 = mock(Room.class);
        Room room2 = mock(Room.class);

        //6.
        List<Room> availableRooms = List.of(room1, room2);
        //Vi tittar på repository.findall() som ska mockas för att returnera en lista, med 2 lediga room

        //4.
        when(roomRepository.findAll()).thenReturn(availableRooms);
        //7.
        when(room1.isAvailable(startTime,endTime)).thenReturn(true);
        when(room2.isAvailable(startTime,endTime)).thenReturn(true);

        //act 1
        List<Room> result = bookingSystem.getAvailableRooms(startTime, endTime);

        //assert 8.
        assertThat(result).containsExactlyInAnyOrder(room1, room2);

    }

    @ParameterizedTest
    @MethodSource("invalidTimeArguments")
    @DisplayName("Should throw exception when start or end time is null")
    void shouldThrowExceptionWhenStartOrEndTimeIsNull(LocalDateTime startTime, LocalDateTime endTime){
        //act and assert
        assertThatThrownBy(() ->
                    bookingSystem.getAvailableRooms(startTime, endTime)
                );
    }
    static Stream<Arguments> invalidTimeArguments(){
        LocalDateTime startTime = LocalDateTime.of(2026,2,8, 10, 0);
        LocalDateTime endTime = startTime.plusHours(1);
        return Stream.of(
                Arguments.of(null, endTime),
                Arguments.of(startTime, null)
        );
    }

    @Test
    @DisplayName("Should throw exception when endtime is before starttime")
    void shouldThrowExceptionWhenEndIsBeforeStartTime(){
        // arrange
        LocalDateTime startTime = LocalDateTime.of(2026, 2, 8, 10, 0);
        LocalDateTime endTime = startTime.minusMinutes(1);

        // act and assert
        assertThatThrownBy(() ->
                    bookingSystem.getAvailableRooms(startTime, endTime)
                );
    }

    @Test
    @DisplayName("Should return true when cancellation is successfull")
    void shouldReturnTrueWhenCancellationIsSuccessfull(){
        //arrange
        Room room1 = mock(Room.class);
        Booking booking1 = mock(Booking.class);


        List<Room> rooms = List.of(room1);
        when(roomRepository.findAll()).thenReturn(rooms);
        when(room1.hasBooking("1")).thenReturn(true);
        when(room1.getBooking("1")).thenReturn(booking1);

        LocalDateTime currentTime = LocalDateTime.of(2026,2,8, 10, 0);
        LocalDateTime startTime = currentTime.plusHours(1);
        when(timeProvider.getCurrentTime()).thenReturn(currentTime);
        when(booking1.getStartTime()).thenReturn(startTime);

        //act
        boolean result = bookingSystem.cancelBooking("1");

        //assert
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("Should throw exception when bookingId is null")
    void shouldThrowExceptionWhenBookingIdIsNull(){
        //act and assert
        assertThatThrownBy(() ->
                    bookingSystem.cancelBooking(null)
                );
    }

    @Test
    @DisplayName("Should return false when booking is empty")
    void shouldReturnFalseWhenBookingIsEmpty(){
        //arrange
        when(roomRepository.findAll()).thenReturn(List.of());

        //act
        boolean result = bookingSystem.cancelBooking("1");

        //assert
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("Should throw exception when startTime is before currentTime")
    void shouldThrowExceptionWhenStartIsBeforeCurrentTime(){
        //arrange
        Room room = mock(Room.class);
        Booking booking = mock(Booking.class);

        when(roomRepository.findAll()).thenReturn(List.of(room));
        when(room.hasBooking("1")).thenReturn(true);
        when(room.getBooking("1")).thenReturn(booking);

        LocalDateTime currentTime = LocalDateTime.of(2026,2,8, 10, 0);
        LocalDateTime startTime = currentTime.minusMinutes(1);
        when(timeProvider.getCurrentTime()).thenReturn(currentTime);
        when(booking.getStartTime()).thenReturn(startTime);

        //act and assert
        assertThatThrownBy(() ->
                    bookingSystem.cancelBooking("1")
                );
    }




}