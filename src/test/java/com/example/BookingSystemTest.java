package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

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
        LocalDateTime currentTime = LocalDateTime.of(2026, 2, 7, 22, 46);
    }
}