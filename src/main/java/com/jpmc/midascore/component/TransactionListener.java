package com.jpmc.midascore.component;

import com.jpmc.midascore.foundation.Transaction;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
// import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class TransactionListener {

    @KafkaListener(topics = "${general.kafka-topic}", groupId = "midas-group")
    public void listen(Transaction transaction) {
        System.out.println("Received Transaction: " + transaction);
    }
}