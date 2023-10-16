package com.inventoryservice.inventoryservice;

import com.inventoryservice.inventoryservice.model.Inventory;
import com.inventoryservice.inventoryservice.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class InventoryserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryserviceApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(InventoryRepository inventoryRepository){
		return args -> {
			Inventory inventory = new Inventory();
			inventory.setSkuCode("Lenovo_Thinkpad");
			inventory.setQuantity(99);

			Inventory inventoryy = new Inventory();
			inventoryy.setSkuCode("Lenovo_ThinkBook");
			inventoryy.setQuantity(100);

			inventoryRepository.save(inventory);
			inventoryRepository.save(inventoryy);

		};
	}

}
