package com.github.superbackend.controller;


import com.github.superbackend.dto.PaymoneyRequest;
import com.github.superbackend.dto.ResponseDto;
import com.github.superbackend.repository.paymoney.Paymoney;
import com.github.superbackend.service.PaymoneyService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/paymoney")
@RequiredArgsConstructor
@Slf4j
public class PaymoneyController {
    private final PaymoneyService paymoneyService;

    @ApiOperation("쇼핑몰 페이머니 충전")
    @PostMapping
    public ResponseEntity<ResponseDto> insertPaymoney(@RequestBody PaymoneyRequest paymoneyRequest,
                                                      @AuthenticationPrincipal UserDetails userDetails
    ) {
        // TODO : userDetails 확인필요
        String username = "2"; //userDetails.getUsername();
        Integer money = paymoneyRequest.getPaymoney();

        Paymoney paymoney = paymoneyService.savePaymoney(Long.parseLong(username), money, true);

        if (paymoney != null) {
            return ResponseEntity.ok(ResponseDto.builder()
                    .status(HttpStatus.OK.toString())
                    .message(paymoney.getPaymoney().toString())
                    .build());
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(
                            ResponseDto.builder()
                                    .status(HttpStatus.NOT_FOUND.toString())
                                    .message("페이머니 충전에 실패하였습니다.")
                                    .build()
                    );
        }
    }

    @ApiOperation("멤버아이디로 쇼핑몰 페이머니 조회")
    @GetMapping
    public ResponseEntity<ResponseDto> getPaymoney() { // @AuthenticationPrincipal UserDetails userDetails
        // TODO : Userdetails 확인 필요
        String username = "2"; //userDetails.getUsername();
        Paymoney paymoney = paymoneyService.findPaymoney(Long.parseLong(username));

        if (paymoney != null) {
            return ResponseEntity.ok(ResponseDto.builder()
                    .status(HttpStatus.OK.toString())
                    .message(paymoney.getPaymoney().toString())
                    .build());
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    ResponseDto.builder()
                            .status(HttpStatus.NOT_FOUND.toString())
                            .message("페이머니 조회에 실패하였습니다.")
                            .build()
            );
        }
    }

    @ApiOperation("쇼핑몰 페이머니 결제, 차감")
    @PutMapping
    public ResponseEntity<ResponseDto> updatePaymoney(@RequestBody PaymoneyRequest paymoneyRequest) { // ,@AuthenticationPrincipal UserDetails userDetails
        // TODO : userDetails 확인필요
        String username = "2"; //userDetails.getUsername();
        Integer money = paymoneyRequest.getPaymoney();

        Paymoney paymoney = paymoneyService.savePaymoney(Long.parseLong(username), money, false);

        if (paymoney != null) {
            return ResponseEntity.ok(ResponseDto.builder()
                    .status(HttpStatus.OK.toString())
                    .message(paymoney.getPaymoney().toString())
                    .build());
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(
                            ResponseDto.builder()
                                    .status(HttpStatus.NOT_FOUND.toString())
                                    .message("페이머니 차감에 실패하였습니다.")
                                    .build()
                    );
        }
    }
} // end class
