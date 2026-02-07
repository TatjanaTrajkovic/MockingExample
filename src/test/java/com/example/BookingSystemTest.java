package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
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
    @DisplayName("Should book room when romm exists and is available")
    void bookRoomSucessfully() throws Exception{
        //arange
        String roomId = "1";
        LocalDateTime currentTime = LocalDateTime.of(2026, 2, 7, 22, 00);
        when(timeProvider.getCurrentTime()).thenReturn(currentTime);

        LocalDateTime startTime = currentTime.plusHours(1);
        LocalDateTime endTime = currentTime.plusHours(2);

        Room room = mock(Room.class);
        when(roomRepository.findById("1")).thenReturn(Optional.of(room));
        when(room.isAvailable(startTime, endTime)).thenReturn(true);

        //act
        boolean result = bookingSystem.bookRoom(roomId, startTime, endTime);

        //assert
        assertThat(result).isTrue();

//        verify(room).addBooking(any(Booking.class));
//        verify(roomRepository).save(room);
//        verify(notificationService)
//                .sendBookingConfirmation(any(Booking.class));




    }
}