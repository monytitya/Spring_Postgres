package Online_Meansreang.Meangsreang.repository;

import Online_Meansreang.Meangsreang.entity.Transaction;
import Online_Meansreang.Meangsreang.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    // Find by transaction ID
    Optional<Transaction> findByTransactionId(String transactionId);

    // Find all by status
    List<Transaction> findByStatus(PaymentStatus status);

    // Find all by customer email
    List<Transaction> findByCustomerEmail(String email);

    // Find pending transactions older than X minutes (for expiry check)
    @Query("SELECT t FROM Transaction t WHERE t.status = Online_Meansreang.Meangsreang.enums.PaymentStatus.PENDING AND t.createdAt < :expiryTime")
    List<Transaction> findExpiredPendingTransactions(@Param("expiryTime") LocalDateTime expiryTime);
}
