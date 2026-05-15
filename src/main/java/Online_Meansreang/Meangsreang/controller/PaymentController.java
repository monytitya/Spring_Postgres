package Online_Meansreang.Meangsreang.controller;

import Online_Meansreang.Meangsreang.entity.Transaction;
import Online_Meansreang.Meangsreang.model.PaymentRequest;
import Online_Meansreang.Meangsreang.model.PaymentResponse;
import Online_Meansreang.Meangsreang.service.BakongApiService;
import Online_Meansreang.Meangsreang.service.KhqrService;
import Online_Meansreang.Meangsreang.service.TransactionService;
import Online_Meansreang.Meangsreang.util.QrCodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/payment")
@CrossOrigin(origins = "*")
@Slf4j
public class PaymentController {

    @Autowired private KhqrService khqrService;
    @Autowired private QrCodeUtil qrCodeUtil;
    @Autowired private TransactionService transactionService;
    @Autowired private BakongApiService bakongApiService;

    // 1️⃣ Generate QR + Save to DB
    @PostMapping("/generate-qr")
    public ResponseEntity<PaymentResponse> generateQr(@RequestBody PaymentRequest request) {
        try {
            String transactionId = "TXN" + System.currentTimeMillis();

            // Generate KHQR string
            String qrString = khqrService.generateKhqr(
                transactionId, request.getAmount(), request.getCurrency()
            );

            // Generate QR image
            String qrBase64 = qrCodeUtil.generateQrBase64(qrString, 300, 300);

            // ✅ Save to PostgreSQL
            transactionService.createTransaction(
                transactionId,
                request.getAmount(),
                request.getCurrency(),
                qrString,
                request.getCustomerName(),
                request.getEmail(),
                request.getPhone()
            );

            return ResponseEntity.ok(PaymentResponse.builder()
                .transactionId(transactionId)
                .qrString(qrString)
                .qrImageBase64("data:image/png;base64," + qrBase64)
                .amount(request.getAmount())
                .currency(request.getCurrency())
                .status("PENDING")
                .build());

        } catch (Exception e) {
            log.error("Error generating QR", e);
            return ResponseEntity.status(500).build();
        }
    }

    // 2️⃣ Check Status + Update DB
    @GetMapping("/check-status/{transactionId}")
    public ResponseEntity<?> checkStatus(@PathVariable String transactionId) {
        String status = bakongApiService.checkTransactionStatus(transactionId);

        if ("SUCCESS".equals(status)) {
            transactionService.markAsSuccess(transactionId); // ✅ update DB
        }

        return ResponseEntity.ok(Map.of(
            "transactionId", transactionId,
            "status", status
        ));
    }

    // 3️⃣ Bakong Webhook → Update DB
    @PostMapping("/callback")
    public ResponseEntity<String> handleCallback(@RequestBody Map<String, Object> payload) {
        String transactionId = (String) payload.get("externalRef");
        String status = (String) payload.get("status");

        if ("SUCCESS".equals(status)) {
            transactionService.markAsSuccess(transactionId); // ✅ update DB
        } else {
            transactionService.markAsFailed(transactionId);
        }

        return ResponseEntity.ok("OK");
    }

    // 4️⃣ Get All Transactions (admin view)
    @GetMapping("/transactions")
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        return ResponseEntity.ok(transactionService.getAllTransactions());
    }

    // 5️⃣ Get Single Transaction
    @GetMapping("/transactions/{transactionId}")
    public ResponseEntity<?> getTransaction(@PathVariable String transactionId) {
        return transactionService.getTransaction(transactionId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}