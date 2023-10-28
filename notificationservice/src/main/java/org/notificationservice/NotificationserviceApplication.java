package org.notificationservice;

import lombok.extern.slf4j.Slf4j;
import org.notificationservice.event.OrderPlaceEvent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

@SpringBootApplication
@Slf4j
public class NotificationserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotificationserviceApplication.class, args);
	}

	@KafkaListener(topics = "notification")
	public void handleNotification(OrderPlaceEvent orderPlaceEvent){
		log.info("Receiveed Notification for order - {}",orderPlaceEvent.getOrderNumber());
	}


}
