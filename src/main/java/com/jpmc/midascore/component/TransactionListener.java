package com.jpmc.midascore.component;

import com.jpmc.midascore.foundation.Transaction;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
// import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class TransactionListener {

    @Autowired
    private DatabaseConduit databaseConduit;

    @KafkaListener(topics = "transactions", groupId = "midas-group")
public void listen(Transaction transaction) {
    System.out.println("Processing transaction: " + transaction);
    
    // Process transaction
    databaseConduit.processTransaction(transaction);

    System.out.println("Transaction processed: " + transaction);
}

}