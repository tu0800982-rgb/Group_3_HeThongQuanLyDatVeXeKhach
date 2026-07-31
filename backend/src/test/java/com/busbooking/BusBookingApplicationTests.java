package com.busbooking;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class BusBookingApplicationTests {
        @Autowired
        private MockMvc mockMvc;
        @Autowired
        private ObjectMapper objectMapper;

        @Test
        void tripsSeatsAndDashboardAreAvailable() throws Exception {
                mockMvc.perform(get("/api/trips")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.success").value(true))
                                .andExpect(jsonPath("$.data.length()").value(6));
                mockMvc.perform(get("/api/trips/TRP-001/seats")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.data.length()").value(45));
                mockMvc.perform(get("/api/staff/dashboard")).andExpect(status().isUnauthorized());
                String loginContent = mockMvc
                                .perform(post("/api/auth/login").contentType(MediaType.APPLICATION_JSON)
                                                .content("{\"phone\":\"0987654321\"}"))
                                .andExpect(status().isOk()).andExpect(jsonPath("$.data.role").value("ADMIN"))
                                .andReturn().getResponse().getContentAsString();
                String token = objectMapper.readTree(loginContent).path("data").path("accessToken").asText();
                mockMvc.perform(get("/api/staff/dashboard").header("X-Session-Token", token)).andExpect(status().isOk())
                                .andExpect(jsonPath("$.data.availableSeats").isNumber());
                mockMvc.perform(get("/api/bookings/my").header("X-Session-Token", token)).andExpect(status().isOk())
                                .andExpect(jsonPath("$.data").isArray());
        }

        @Test
        void bookingThenPaymentCompletesSuccessfully() throws Exception {
                String bookingContent = mockMvc.perform(post("/api/bookings").contentType(MediaType.APPLICATION_JSON)
                                .content("{\"customerName\":\"Nguyen An\",\"phone\":\"0987654321\",\"email\":\"nguyen.an@example.com\",\"customerType\":\"MEMBER\",\"tripId\":\"TRP-001\",\"seatNumber\":\"C8\"}"))
                                .andExpect(status().isCreated()).andExpect(jsonPath("$.success").value(true))
                                .andReturn().getResponse().getContentAsString();
                JsonNode booking = objectMapper.readTree(bookingContent).path("data");
                mockMvc.perform(post("/api/payments").contentType(MediaType.APPLICATION_JSON)
                                .content("{\"bookingId\":\"" + booking.path("bookingId").asText()
                                                + "\",\"paymentMethod\":\"EWALLET\"}"))
                                .andExpect(status().isCreated()).andExpect(jsonPath("$.data.status").value("SUCCESS"));
        }

        @Test
        void bookingSearchVerifiesPhoneAndCancellationReleasesSeat() throws Exception {
                String loginContent = mockMvc
                                .perform(post("/api/auth/login").contentType(MediaType.APPLICATION_JSON)
                                                .content("{\"phone\":\"0987654321\"}"))
                                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
                String token = objectMapper.readTree(loginContent).path("data").path("accessToken").asText();
                mockMvc.perform(get("/api/bookings/search").header("X-Session-Token", token)
                                .param("bookingId", "BKG-001").param("phone", "0900000001"))
                                .andExpect(status().isOk()).andExpect(jsonPath("$.data.bookingId").value("BKG-001"))
                                .andExpect(jsonPath("$.data.customerName").value("Customer 001"));
                mockMvc.perform(put("/api/bookings/BKG-001/cancel").header("X-Session-Token", token))
                                .andExpect(status().isOk()).andExpect(jsonPath("$.data.status").value("CANCELLED"));
                mockMvc.perform(get("/api/bookings/search").header("X-Session-Token", token)
                                .param("bookingId", "BKG-001").param("phone", "0900000001"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.data.bookingStatus").value("CANCELLED"))
                                .andExpect(jsonPath("$.data.ticketStatus").value("CANCELLED"));
                mockMvc.perform(get("/api/trips/TRP-001/seats")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.data[8].status").value("AVAILABLE"));
        }
}