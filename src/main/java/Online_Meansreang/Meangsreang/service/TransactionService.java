package Online_Meansreang.Meangsreang.service;

import Online_Meansreang.Meangsreang.entity.Transaction;
import Online_Meansreang.Meangsreang.enums.PaymentStatus;
import Online_Meansreang.Meangsreang.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    // Save new transaction when QR is generated
    public Transaction createTransaction(String transactionId, double amount,
                                          String currency, String qrString,
                                          String customerName, String email, String phone) {
        Transaction transaction = Transaction.builder()
                .transactionId(transactionId)
                .amount(amount)
                .currency(currency)
                .qrString(qrString)
                .customerName(customerName)
                .customerEmail(email)
                .customerPhone(phone)
                .status(PaymentStatus.PENDING)
                .build();

        Transaction saved = transactionRepository.save(transaction);
        log.info("Transaction created: {}", transactionId);
        return saved;
    }

    // Update status to SUCCESS
    public Transaction markAsSuccess(String transactionId) {
        Transaction transaction = transactionRepository
                .findByTransactionId(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found: " + transactionId));

        transaction.setStatus(PaymentStatus.SUCCESS);
        transaction.setPaidAt(LocalDateTime.now());

        Transaction updated = transactionRepository.save(transaction);
        log.info("Transaction SUCCESS: {}", transactionId);
        return updated;
    }

    // Update status to FAILED
    public Transaction markAsFailed(String transactionId) {
        Transaction transaction = transactionRepository
                .findByTransactionId(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found: " + transactionId));

        transaction.setStatus(PaymentStatus.FAILED);
        Transaction updated = transactionRepository.save(transaction);
        log.info("Transaction FAILED: {}", transactionId);
        return updated;
    }

    // Get transaction by ID
    public Optional<Transaction> getTransaction(String transactionId) {
        return transactionRepository.findByTransactionId(transactionId);
    }

    // Get all transactions
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    // Expire pending transactions older than 15 minutes
    @Scheduled(fixedRate = 60000) // runs every 1 minute
    public void expirePendingTransactions() {
        LocalDateTime expiryTime = LocalDateTime.now().minusMinutes(15);
        List<Transaction> expired = transactionRepository.findExpiredPendingTransactions(expiryTime);

        expired.forEach(t -> {
            t.setStatus(PaymentStatus.EXPIRED);
            transactionRepository.save(t);
            log.info("Transaction EXPIRED: {}", t.getTransactionId());
        });
    }
}
