package com.github.superbackend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.superbackend.config.SecurityConfig;
import com.github.superbackend.dto.PaymoneyRequest;
import com.github.superbackend.dto.ResponseDto;
import com.github.superbackend.repository.paymoney.Paymoney;
import com.github.superbackend.service.PaymoneyService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.StandardCharsets;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Slf4j
class PaymoneyControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymoneyService paymoneyService;

    // JSON 리퀘스트 바디를 위한 객체 매퍼
    @Autowired
    private ObjectMapper objectMapper;

    // "Given-When-Then-Verify" 패턴
    @DisplayName("PaymoneyController의 페이머니 충전 api 테스트 ")
    @WithMockUser(username = "1", password = "1234", roles = "USER")
    @Test
    void insertPaymoney() throws Exception {
        // given
        Integer money = 30000;
        PaymoneyRequest paymoneyRequest = new PaymoneyRequest(money);

        // when & then
        String content = mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/paymoney")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(paymoneyRequest)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        log.info("insertPaymoney 테스트 결과 : " + content);
    }

    @DisplayName("PaymoneyController의 페이머니 조회 api 테스트")
    @WithMockUser(username = "1", password = "1234", roles = "USER")
    @Test
    void getPaymoney() throws Exception {
        String content = mockMvc.perform(MockMvcRequestBuilders.get("/api/paymoney"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        log.info("getPaymoney 테스트 결과 : " + content);
    }

    @DisplayName("PaymoneyController의 페이머니 결제 api 테스트")
    @WithMockUser(username = "1", password = "1234", roles = "USER")
    @Test
    void updatePaymoney() throws Exception {
        // given
        Integer money = 10000;
        PaymoneyRequest paymoneyRequest = new PaymoneyRequest(money);

        // when & then
        String content = mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/paymoney")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(paymoneyRequest)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        log.info("updatePaymoney 테스트 결과 : " + content);

    }
}