package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
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





//
//    @Test
//    @DisplayName("Should book room when romm exists and is available")
//    void bookRoomSucessfully(){
//        // Arange
//        String roomId = "1";
//        LocalDateTime currentTime = LocalDateTime.of(2026, 2, 7, 22, 00);
//        when(timeProvider.getCurrentTime()).thenReturn(currentTime);
//
//        LocalDateTime startTime = currentTime.plusHours(1);
//        LocalDateTime endTime = currentTime.plusHours(2);
//
//        Room room = mock(Room.class);
//        when(roomRepository.findById("1")).thenReturn(Optional.of(room));
//        when(room.isAvailable(startTime, endTime)).thenReturn(true);
//
//        //act
//        boolean result = bookingSystem.bookRoom(roomId, startTime, endTime);
//
//        //assert
//        assertThat(result).isTrue();
//
//
//
//
//
//        @Test
//        @DisplayName("Should show all available rooms for a certain timeperion")
//        void showAllAvailableRooms(){
//            //Arrange
//            LocalDateTime currentTime = LocalDateTime.of(2026, 2, 7, 22, 00);
//
//            LocalDateTime startTime = currentTime.plusHours(1);
//            LocalDateTime endTime = startTime.plusHours(2);
//
//            Room room1 = mock(Room.class);
//            Room room2 = mock(Room.class);
//
//            when(room1.isAvailable(startTime, endTime)).thenReturn(true);
//            when(room2.isAvailable(startTime, endTime)).thenReturn(true);
//
//            List<Room> roomsAvailable = List.of(room1, room2);
//            when(roomRepository.findAll()).thenReturn(roomsAvailable);
//
//            //Act
//            List<Room> rooms = bookingSystem.getAvailableRooms(startTime, endTime);
//
//            //Assert
//            assertThat(rooms).containsExactlyInAnyOrder(room1, room2);
//
//
//        }
//
//    }


}