package com.github.superbackend.controller;

import com.github.superbackend.dto.OrderHistDto;
import com.github.superbackend.dto.PaymentRequest;
import com.github.superbackend.dto.ResponseDto;
import com.github.superbackend.repository.payment.Payment;
import com.github.superbackend.service.PaymentService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
@Slf4j
public class PaymentController {
    private final PaymentService paymentService;

    @ApiOperation("쇼핑몰 결제 정보 추가")
    @PostMapping("/{orderId}")
    public ResponseEntity<ResponseDto> insertPayment(@PathVariable Long orderId, @AuthenticationPrincipal UserDetails userDetails) {
        Payment payment = paymentService.savePayment(orderId, userDetails.getUsername()); //userDetails.getUsername()    @AuthenticationPrincipal UserDetails userDetails

        if (payment != null) {
            return ResponseEntity.ok(ResponseDto.builder()
                    .status(HttpStatus.OK.toString())
                    .message(payment.getPaymentId().toString())
                    .build());
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    ResponseDto.builder()
                            .status(HttpStatus.NOT_FOUND.toString())
                            .message("결제 정보 추가에 실패하였습니다.")
                            .build()
            );
        }
    }

    @ApiOperation("쇼핑몰 사용자별 결제 정보 조회")
    @GetMapping("/{memberId}")
    public ResponseEntity<Page<OrderHistDto>> getPayment(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long memberId, Pageable pageable) {
        Page<OrderHistDto> orders = paymentService.findPayment(userDetails.getUsername(), pageable);

        if (orders != null) {
            return ResponseEntity.ok(orders);
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

} // End class
