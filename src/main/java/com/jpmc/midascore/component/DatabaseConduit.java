package com.jpmc.midascore.component;

import com.jpmc.midascore.foundation.*;
import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.repository.TransactionRecordRepository;
import com.jpmc.midascore.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class DatabaseConduit {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRecordRepository transactionRecordRepository;

    @Transactional
    public void processTransaction(Transaction transaction) {
        long senderId = transaction.getSenderId();
        long recipientId = transaction.getRecipientId();
        BigDecimal amount = BigDecimal.valueOf(transaction.getAmount());

        // Fetch sender and recipient from the database
        Optional<UserRecord> senderOpt = Optional.ofNullable(userRepository.findById(senderId));
        Optional<UserRecord> recipientOpt = Optional.ofNullable(userRepository.findById(recipientId));

        if (!senderOpt.isPresent() || !recipientOpt.isPresent()) {
            System.out.println("Transaction rejected: Invalid sender or recipient ID");
            return;
        }

        UserRecord sender = senderOpt.get();
        UserRecord recipient = recipientOpt.get();

        // Debug logging
        System.out.println("Processing transaction: " + transaction);
        System.out.println("Sender balance before: " + sender.getBalance());
        System.out.println("Recipient balance before: " + recipient.getBalance());

        if (sender.getBalance().compareTo(amount) >= 0) {
            // Deduct amount from sender
            sender.setBalance(sender.getBalance().subtract(amount));

            // Add amount to recipient
            recipient.setBalance(recipient.getBalance().add(amount));

            // Debug logging
            System.out.println("Sender balance after: " + sender.getBalance());
            System.out.println("Recipient balance after: " + recipient.getBalance());
            
            // Save updated balances
            userRepository.save(sender);
            userRepository.save(recipient);

            // Create and save the transaction record
            TransactionRecord transactionRecord = new TransactionRecord(sender, recipient, amount);
            transactionRecordRepository.save(transactionRecord);

            System.out.println("Transaction processed: " + transactionRecord);
        } else {
            System.out.println("Transaction rejected: Insufficient balance for senderId=" + senderId);
        }
    }

    public void save(UserRecord user) {
        // Implementation to save user record
        userRepository.save(user);
    }
}