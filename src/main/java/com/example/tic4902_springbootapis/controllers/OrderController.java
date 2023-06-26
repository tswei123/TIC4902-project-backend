package com.example.tic4902_springbootapis.controllers;
import com.example.tic4902_springbootapis.models.*;
import com.example.tic4902_springbootapis.repository.*;

import com.example.tic4902_springbootapis.repository.*;
import com.example.tic4902_springbootapis.service.FilesStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.time.format.DateTimeFormatter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.ModelAndView;

//@CrossOrigin
@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("api/order")
public class OrderController {
    @Autowired
    private PriceRepository priceRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ServiceRepository serviceRepository;
    @Autowired
    private Cart_ServiceRepository cart_serviceRepository;
    @Autowired
    private Cart_ItemRepository cart_itemRepository;

    @Autowired
    private Cart_PriceRepository cart_priceRepository;

    @Autowired
    private Shipment_ServiceRepository shipment_serviceRepository;

    @Autowired
    private Shipment_ItemRepository shipment_itemRepository;

    @Autowired
    private Shipment_PriceRepository shipment_priceRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    FilesStorageService storageService;
    @PostMapping("/addPrice")
    public String add(@RequestBody Price price){
        if(priceRepository.findByServiceidAndDays(price.getServiceid(), price.getDays()).size() > 0) {
            return "Days is already added for this service package";
        }else {
            priceRepository.save(price);
            return "New price is added";
        }
    }

    @GetMapping("/getPrice")
    public List<Price> getPrice(){
        return priceRepository.findAll();
    }
    @PutMapping("/updatePrice/{id}")
    public String updatePrice(@RequestBody Price price, @PathVariable Long id){
        boolean exists = priceRepository.existsById(id);
        if(exists){
            if(priceRepository.findByServiceidAndDays(price.getServiceid(), price.getDays()).size() > 0) {
                price.setId(id);
                priceRepository.save(price);
                return "Price record updated";
            }else {
                return "Record not found";
            }
        }else{
            return "Price record not exist";
        }
    }
    @DeleteMapping("/deletePrice/{id}")
    public String deletePrice(@PathVariable Long id){
        boolean exists = priceRepository.existsById(id);
        if(exists){
            priceRepository.deleteById(id);
            return "Price deleted";
        }else{
            return "Price record not exist";
        }
    }

    @PostMapping("/addItem")
    public String add(@RequestBody Item item){
        if(itemRepository.findByServiceidAndItemname(item.getServiceid(), item.getItemname()).size() > 0) {
            return "Item is already added for this service package";
        }else {
            itemRepository.save(item);
            return "New item is added";
        }
    }

    @GetMapping("/getItem")
    public List<Item> getItem(){
        return itemRepository.findAll();
    }

    @DeleteMapping("/deleteItem/{id}")
    public String deleteItem(@PathVariable Long id){
        boolean exists = itemRepository.existsById(id);
        if(exists){
            itemRepository.deleteById(id);
            return "Item deleted";
        }else{
            return "Item record not exist";
        }
    }

    @PostMapping("/addService")
    public String add(@RequestBody Service service){
        serviceRepository.save(service);
        return "New service is added";
    }
    @PostMapping("/addImage")
    public String add(@RequestParam("file") MultipartFile file){
        String message = "";
        final Path root = Paths.get("./assets/img");
        try {
            Files.createDirectories(root);
            storageService.save(file);

            message = "Uploaded the image successfully: " + file.getOriginalFilename();

        } catch (Exception e) {
            message = "Could not upload the image: " + file.getOriginalFilename() + ". Error: " + e.getMessage();
        }
        return message;
        //return "New service is added";
    }

    @GetMapping("/assets/img/{filename:.+}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) {
        Resource file = storageService.load(filename);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @GetMapping("/getService")
    public List<Service> getService(){
        return serviceRepository.findAll();
    }

    @DeleteMapping("/deleteService/{id}")
    public String deleteService(@PathVariable Long id){
        boolean exists = serviceRepository.existsById(id);
        if(exists){
            serviceRepository.deleteItem(id);
            serviceRepository.deletePrice(id);
            serviceRepository.deleteById(id);
            return "Service deleted";
        }else{
            return "Service record not exist";
        }

    }
    @PutMapping("/updateService/{id}")
    public String updateService(@RequestBody Service service, @PathVariable Long id){
        boolean exists = serviceRepository.existsById(id);
        if(exists){
            service.setId(id);
            serviceRepository.save(service);
            return "Service updated";
        }else{
            return "Service record not exist";
        }
    }

    @GetMapping("/getIndividualService")
    public Optional<Service> getIndividualService(@RequestParam Long id){
        return serviceRepository.findById(id);
    }

    @PostMapping("/addToCart")
    public String add(@RequestBody Cart_Service cart_service){
            //check order number
            String ordernumberSet ="";
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            String dateTimeNow = formatter.format(java.time.LocalDate.now()).toString();
            String ordernumberMax = cart_serviceRepository.findMaxOrdernumber();
            if(ordernumberMax == "" || ordernumberMax == null || ordernumberMax.length() == 0){
                ordernumberSet = "Ora" + dateTimeNow + "10000001";
            }else{
                String temp = ordernumberMax.substring(0, ordernumberMax.length() - 8);
                Integer temp1 = Integer.parseInt(ordernumberMax.substring(ordernumberMax.length() - 8));
                temp1++;
                ordernumberSet = temp + temp1.toString();
            }

            cart_service.setOrdernumber(ordernumberSet);
            cart_serviceRepository.save(cart_service);
            add2(cart_service, ordernumberSet);
            Optional<Service> service= serviceRepository.findById(cart_service.getId());
            Service service1 = new Service();

            service1.setId(service.get().getId());
            service1.setDeliveryprice(service.get().getDeliveryprice());
            service1.setDepositprice(service.get().getDepositprice());
            service1.setImagepath(service.get().getImagepath());
            int newQty = service.get().getQuantity() - 1;
            service1.setQuantity(newQty);
            service1.setReturnprice(service.get().getReturnprice());
            service1.setServicedesc(service.get().getServicedesc());
            service1.setServicename(service.get().getServicename());

            serviceRepository.save(service1);

            return ordernumberSet;
    }
    public void add2(Cart_Service cart_service, String ordernumberSet) {

        Cart_Item[] cartItem = new Cart_Item[cart_service.getCart_items().size()];
        cart_service.getCart_items().toArray(cartItem);
        for (int i = 0; i < cart_service.getCart_items().size(); i++){
            cartItem[i].setOrdernumber(ordernumberSet);
            cart_itemRepository.save(cartItem[i]);
        }

        Cart_Price[] cartPrice = new Cart_Price[cart_service.getCart_prices().size()];
        cart_service.getCart_prices().toArray(cartPrice);
        for (int i = 0; i < cart_service.getCart_prices().size(); i++){
            cartPrice[i].setOrdernumber(ordernumberSet);
            cart_priceRepository.save(cartPrice[i]);
        }
    }
    @DeleteMapping("/deleteServiceCart")
    public String deleteCart(@RequestParam Long id,@RequestParam String ordernumber){

        Optional<Service> service= serviceRepository.findById(id);

        Service service2 = new Service();

        service2.setId(service.get().getId());
        service2.setDeliveryprice(service.get().getDeliveryprice());
        service2.setDepositprice(service.get().getDepositprice());
        service2.setImagepath(service.get().getImagepath());
        int newQty = service.get().getQuantity() + 1;
        service2.setQuantity(newQty);
        service2.setReturnprice(service.get().getReturnprice());
        service2.setServicedesc(service.get().getServicedesc());
        service2.setServicename(service.get().getServicename());

        //cart_serviceRepository.deleteById(id);
        cart_serviceRepository.deleteCart(ordernumber);
        cart_serviceRepository.deletePrice(ordernumber);
        cart_serviceRepository.deleteCart_Service(ordernumber);
        serviceRepository.save(service2);
        return "Cart deleted";
    }

    @GetMapping("/getServiceCart")
    public List<Cart_Service> getServiceCart(){
        return cart_serviceRepository.findAll();
    }

    @GetMapping("/getIndividualServiceCart")
    public List<Cart_Service> getIndividualServiceCart(@RequestParam String email){
       return cart_serviceRepository.findIndividualCart(email);
    }

    @PostMapping("/addToShipment")
    public String add(@RequestParam String email, @RequestParam String ordernumber, @RequestParam String address, @RequestParam Integer contactnumber){
        List<Cart_Service> cart_services = cart_serviceRepository.findCart_Service(ordernumber);
        String userName = cart_serviceRepository.findUserName(email).toString();
        Shipment_Service shipment_service = new Shipment_Service();
        shipment_service.setId(cart_services.get(0).getId());
        shipment_service.setOrdernumber(cart_services.get(0).getOrdernumber());
        shipment_service.setServicename(cart_services.get(0).getServicename());
        shipment_service.setServicedesc(cart_services.get(0).getServicedesc());
        shipment_service.setDeliveryprice(cart_services.get(0).getDeliveryprice());
        shipment_service.setReturnprice(cart_services.get(0).getReturnprice());
        shipment_service.setDepositprice(cart_services.get(0).getDepositprice());
        shipment_service.setEmail(cart_services.get(0).getEmail());
        shipment_service.setUsername(userName);
        shipment_service.setRentfrom(cart_services.get(0).getRentfrom());
        shipment_service.setRentto(cart_services.get(0).getRentto());
        shipment_service.setTotalprice(cart_services.get(0).getTotalprice());
        shipment_service.setAddress(address);
        shipment_service.setContactnumber(contactnumber);
        shipment_service.setShipmentstatus("pending shipment");
        shipment_serviceRepository.save(shipment_service);
        Cart_Item[] cartItem = new Cart_Item[cart_services.get(0).getCart_items().size()];
        cart_services.get(0).getCart_items().toArray(cartItem);
        for (int i = 0; i < cart_services.get(0).getCart_items().size(); i++){
            Shipment_Item shipmentItem = new Shipment_Item();
            shipmentItem.setItemname(cartItem[i].getItemname());
            shipmentItem.setPrice(cartItem[i].getPrice());
            shipmentItem.setServiceid(cartItem[i].getServiceid());
            shipmentItem.setOrdernumber(cartItem[i].getOrdernumber());
            shipment_itemRepository.save(shipmentItem);
        }
        Cart_Price[] cartPrice = new Cart_Price[cart_services.get(0).getCart_prices().size()];
        cart_services.get(0).getCart_prices().toArray(cartPrice);
        for (int i = 0; i < cart_services.get(0).getCart_prices().size(); i++){
            Shipment_Price shipmentPrice = new Shipment_Price();
            shipmentPrice.setDays(cartPrice[i].getDays());
            shipmentPrice.setPrice(cartPrice[i].getPrice());
            shipmentPrice.setServiceid(cartPrice[i].getServiceid());
            shipmentPrice.setOrdernumber(cartPrice[i].getOrdernumber());
            shipment_priceRepository.save(shipmentPrice);
        }
        cart_services.get(0).setOrderstatus("Payment successful");
        cart_serviceRepository.save(cart_services.get(0));
        List<User> user = userRepository.findIndividualUser(cart_services.get(0).getEmail());
        user.get(0).setAddress(address);
        user.get(0).setContact(contactnumber);
        userRepository.save(user.get(0));
        return "New service is added to shipment";
    }

    @GetMapping("/getServiceShipment")
    public List<Shipment_Service> getServiceShipment(){
        return shipment_serviceRepository.findAll();
    }

    @GetMapping("/getIndividualServiceShipment")
    public List<Shipment_Service> getIndividualShipment(@RequestParam String email){
        return shipment_serviceRepository.findIndividualShipment(email);
    }
    @PostMapping("/changeShipmentStatus")
    public String updateShipmentFlag(@RequestParam String ordernumber) {
        List<Shipment_Service> shipment_service = shipment_serviceRepository.getShipmentDetail(ordernumber);
        if(shipment_service.size() == 0){
            return "ordernumber not found";
        }
        shipment_service.get(0).setShipmentstatus("item delivered");
        shipment_serviceRepository.save(shipment_service.get(0));
        return "Shipment status updated";
    }
    @GetMapping("/")
    public ModelAndView  hiController(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/index.html");
        //return modelAndView;
        return modelAndView;
    }
}
