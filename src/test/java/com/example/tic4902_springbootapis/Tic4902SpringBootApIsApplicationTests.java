package com.example.tic4902_springbootapis;

import com.example.tic4902_springbootapis.controllers.OrderController;
import com.example.tic4902_springbootapis.models.Service;
import com.example.tic4902_springbootapis.payload.request.SignupRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.example.tic4902_springbootapis.controllers.AuthController;
import com.example.tic4902_springbootapis.payload.request.LoginRequest;


@SpringBootTest
class Tic4902SpringBootApIsApplicationTests {
	@Autowired
	AuthController authController = new AuthController();
	@Autowired
	OrderController orderController = new OrderController();

//	@Test
//	void testLogin() {
//		LoginRequest lo = new LoginRequest();
//		lo.setEmail("user123@u.nus.edu");
//		lo.setPassword("user123");
//		assertEquals(authController.authenticateUser(lo).getStatusCode().toString(), "200 OK");
//	}
//	@Test
//	void testSignup() {
//		SignupRequest si = new SignupRequest();
//		si.setUsername("Sean");
//		si.setEmail("Sean@u.nus.edu");
//		si.setPassword("Sean123");
//		assertEquals(authController.registerUser(si).getStatusCode().toString(), "200 OK");
//	}
//	@Test
//	void UpdateParticular() {
//		String email = "Sean@u.nus.edu";
//		String username = "Sean";
//		String address = "Marsiling";
//		Integer contact = 90784590;
//		assertEquals(authController.update(email, username, address, contact), "Update successfully");
//	}
//	@Test
//	void getService() {
//		System.out.println(orderController.getService().get(0).getId() !=0);
//		assertEquals(orderController.getService().get(0).getId() !=0, true);
//	}
//	@Test
//	void updateService() {
//		Service service = new Service();
//		Long id = 1L;
//		service.setServicename("Oculus Quest 4 Meta Quest 4");
//		service.setServicedesc("Oculus Quest 4 128Gb Meta Quest 4 128Gb Meta Quest");
//		service.setImagepath("/assets/img/Feature1.png");
//		service.setDeliveryprice(5.99);
//		service.setReturnprice(5.99);
//		service.setDepositprice(580);
//		service.setQuantity(8);
//
//		assertEquals(orderController.updateService(service, id), "Service updated");
//	}
//	@Test
//	void getCarts() {
//		assertEquals(orderController.getServiceCart().size() >=0, true);
//	}
	@Test
	void updateShipmentFlag() {
		String orderNo = "Ora2023011310000003";
		if(orderController.updateShipmentFlag(orderNo) == "ordernumber not found"){
			return;
		}else{
			assertEquals(orderController.updateShipmentFlag(orderNo),"Shipment status updated");
		}
	}
}

