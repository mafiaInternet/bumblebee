package com.example.bumblebee.service.Impl;

import com.example.bumblebee.exception.CartException;
import com.example.bumblebee.exception.OrderException;
import com.example.bumblebee.exception.ProductException;
import com.example.bumblebee.exception.UserException;
import com.example.bumblebee.model.dao.*;
import com.example.bumblebee.model.entity.*;
import com.example.bumblebee.request.CreateOrderRequest;
import com.example.bumblebee.response.CategorySold;
import com.example.bumblebee.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderDao orderDao;
    @Autowired
    private CartService cartService;
    @Autowired
    private AddressDao addressDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private CheckOutDao checkOutDao;
    @Autowired
private OrderItemService orderItemService;

private OrderItemDao orderItemDao;
    @Autowired
private ProductService productService;
    @Autowired
private CartItemService cartItemService;
    private CategoryDao categoryDao;
    private ProductDao productDao;
    private SizeDao sizeDao;
    private ColorDao colorDao;
    @Autowired
    public OrderServiceImpl(ProductService productService,OrderDao orderDao, CartService cartService, CartItemService cartItemService, AddressDao addressDao, UserDao userDao, OrderItemDao orderItemDao, OrderItemService orderItemService, CheckOutDao checkOutDao, CategoryDao categoryDao, ProductDao productDao, ColorDao colorDao, SizeDao sizeDao){
       this.productService = productService;
        this.orderDao = orderDao;
       this.cartService = cartService;
       this.addressDao = addressDao;
       this.userDao = userDao;
       this.orderItemDao = orderItemDao;
       this.orderItemService = orderItemService;
       this.cartItemService = cartItemService;
        this.checkOutDao = checkOutDao;
        this.categoryDao = categoryDao;
        this.productDao = productDao;
        this.colorDao = colorDao;
        this.sizeDao = sizeDao;
    }

    @Override
    public Order getOrdersByUserYetCheckedout(User user){

        return orderDao.getUsersOrdersYetCheckedOut(user.getId());

    }

    public void handleCreateOrder (CartItem cartItem) throws ProductException {
        Product product = productService.findProductById(cartItem.getProduct().getId());
        System.out.println("product order - " + product.toString());
        for(Color color: product.getColors()){
            if(color.getName().equals(cartItem.getColor())){

                for(Size size: color.getSizes()){

                    if(size.getName().equals(cartItem.getSize())){
                        System.out.println("hello");
                        size.setQuantity(size.getQuantity()-cartItem.getQuantity());
                        product.setTotalQuantity(product.getTotalQuantity() - cartItem.getQuantity());
                        product.setTotalSold(product.getTotalSold() + cartItem.getQuantity());

                    }
                }
            }
        }
        productDao.save(product);

    }
    @Override
    public Order addOrder(User user, CreateOrderRequest createOrderRequest) throws ProductException {
        Order createOrder = new Order();
        Cart cart = createOrderRequest.getCart();
        Address address = createOrderRequest.getAddress() ;
        if (address.getId() == null){
            addressDao.save(address);
        }
        List<OrderItem> orderItems = new ArrayList<>();
            for(CartItem item:cart.getCartItems()){
                OrderItem createdOrderItem = orderItemService.createOrderItem(item);
                handleCreateOrder(item);
                orderItems.add(createdOrderItem);

            }
        createOrder.setUser(user);
       createOrder.setOrderItems(orderItems);
       createOrder.setTotalItem(cart.getTotalItem());
       createOrder.setDiscount(createOrderRequest.getDiscountedPrice());
       createOrder.getPaymentDetails().setPaymentMethod(createOrderRequest.getPaymentMethod());
       createOrder.setTotalPrice(cart.getTotalPrice());
       createOrder.setOrderStatus("Đang chờ xác nhận");
        createOrder.setAddress(address);
        createOrder.setCreateAt(LocalDateTime.now());
        Order saveOrder = orderDao.save(createOrder);

        for (OrderItem item:orderItems){
            item.setOrder(createOrder);
            orderItemDao.save(item);
        }

        return saveOrder;
    }
    @Override
    public List<Order> usersOrderHistory(Long userId) {
        List<Order> orders = orderDao.getOrderByUser(userId);


        return orders;
    }

    @Override
    public Order putStatusOrderByAdmin(Long orderId, String status) throws OrderException{
        Order order = findOderById(orderId);
        if (status.equals("Hoàn tất")){
            Optional<User> user = userDao.findById(order.getUser().getId());
            user.get().setFpoint(user.get().getFpoint() - order.getFpoint());
            userDao.save(user.get());
        }
        order.setOrderStatus(status);
        return orderDao.save(order);
    }
    @Override
    public Order findOderById(Long orderId) throws OrderException {
        Optional<Order> opt = orderDao.findById(orderId);

        if(opt.isPresent()){
            return opt.get();
        }
        throw new OrderException("Order not exist with id" + orderId);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderDao.findAll();
    }

    @Override
    public void deleteOrder(Long orderId) throws OrderException {
        orderDao.deleteById(orderId);
    }

    @Override
    public List<CategorySold> getCategoryBestSold(User user) {

        LocalDateTime oneWeekAgo = LocalDateTime.now().minusWeeks(1);
        List<Order> orders = orderDao.findAll();
        List<Order> topWeekOrders = orders.stream()
                .filter(o -> o.getCreateAt().isAfter(oneWeekAgo) && o.getCreateAt().isBefore(LocalDateTime.now()))
                .sorted(Comparator.comparing(Order::getTotalPrice).reversed())

                .collect(Collectors.toList());

        List<Category> categories = categoryDao.findAll();
        List<CategorySold> categorySolds = new ArrayList<>();
        for(Category category: categories){
            CategorySold categorySold = new CategorySold();
            categorySold.setName(category.getName());
            categorySolds.add(categorySold);
        }

        for (Order order: topWeekOrders){
            System.out.println("order");
            for (OrderItem orderItem: order.getOrderItems()){
                System.out.println("order iTem");
                for (CategorySold categorySold: categorySolds){
                    System.out.println("category sold - " + categorySold.getName());
                    System.out.println("product category - " + orderItem.getProduct().getCategory().getName());
                    if(categorySold.getName().equals(orderItem.getProduct().getCategory().getName())){
                        categorySold.setTotalPrice((long) (categorySold.getTotalPrice() + orderItem.getPrice()));
                        categorySold.setTotalQuantity(categorySold.getTotalQuantity() + orderItem.getQuantity());
                        System.out.println("category");
                    }
                }
            }
        }



        return categorySolds;

    }

    @Override
    public List<CategorySold> getCategoryMonth(User user){

        List<Order> orders = orderDao.findAll();
        LocalDateTime oneMothAgo = LocalDateTime.now().withDayOfMonth(1);
//        LocalDateTime oneMothAgo =  LocalDateTime.now().minusMonths(1);
        List<Order> ordersOfMonth = orders.stream()
                .filter(o -> o.getCreateAt().isAfter(oneMothAgo) && o.getCreateAt().isBefore(LocalDateTime.now()))
                .sorted(Comparator.comparing(Order::getTotalPrice).reversed())
                .collect(Collectors.toList());
        List<CategorySold> categorySolds = new ArrayList<>();
        for(Category category: categoryDao.findAll()){
            CategorySold categorySold = new CategorySold();
            categorySold.setName(category.getName());
            categorySolds.add(categorySold);
        }
        for(Order order: ordersOfMonth){
            for(OrderItem orderItem: order.getOrderItems()){
                for(CategorySold categorySold: categorySolds){
                    if(categorySold.getName().equals(orderItem.getProduct().getCategory().getName())){
                        categorySold.setTotalQuantity(categorySold.getTotalQuantity() + orderItem.getQuantity());
                        categorySold.setTotalPrice((long) (categorySold.getTotalPrice() + orderItem.getPrice()));
                    }
                }
            }
        }
        return categorySolds;
    }

    @Override
    public List<Order> getDemo(User user){
        List<Order> orders = orderDao.findAll();
        LocalDateTime oneMothAgo = LocalDateTime.now().withDayOfMonth(1);
//        LocalDateTime oneMothAgo =  LocalDateTime.now().minusMonths(1);
        List<Order> ordersOfMonth = orders.stream()
                .filter(o -> o.getCreateAt().isAfter(oneMothAgo) && o.getCreateAt().isBefore(LocalDateTime.now()))
                .sorted(Comparator.comparing(Order::getTotalPrice).reversed())
                .collect(Collectors.toList());
        return ordersOfMonth;
    }

    @Override
    public List<CategorySold> getCategoryYear(User user){
        List<Order> orders = orderDao.findAll();
        LocalDateTime oneYearAgo = LocalDateTime.now().minusYears(1);
        List<Order> orderOfYear = orders.stream()
                .filter(o -> o.getCreateAt().isAfter(oneYearAgo)  && o.getCreateAt().isBefore(LocalDateTime.now()))
                .sorted(Comparator.comparing(Order::getTotalPrice).reversed())
              .collect(Collectors.toList());
        List<CategorySold> categorySolds = new ArrayList<>();
        for (Category category: categoryDao.findAll()){
            CategorySold categorySold = new CategorySold();
            categorySold.setName(category.getName());
            categorySolds.add(categorySold);
        }
        for(Order order: orderOfYear){
            for(OrderItem orderItem: order.getOrderItems()){
                for(CategorySold categorySold: categorySolds){
                    if(categorySold.getName().equals(orderItem.getProduct().getCategory().getName())){
                        categorySold.setTotalQuantity(categorySold.getTotalQuantity() + orderItem.getQuantity());
                        categorySold.setTotalPrice((long) (categorySold.getTotalPrice() + orderItem.getPrice()));
                    }
                }
            }
        }
        return categorySolds;
    }

    @Override
    public List<Integer> getToTalQuantityProductSold(int year){
        ArrayList<Integer> quantities = new ArrayList<>();
        int[] month = {1,2,3,4,5,6,7,8,9,10,11,12};

        for (int i = 1; i <=12; i++){
            YearMonth yearMonth = YearMonth.of(year, i);
            LocalDateTime startMonthOfYear = yearMonth.atDay(1).atStartOfDay();
            LocalDateTime endMonthOfYear = yearMonth.atEndOfMonth().atTime(23, 59);
            int totalQuantity = 0;
            List<Order> monthOfYear = orderDao.findAll().stream()
                    .filter(o -> o.getCreateAt().isAfter(startMonthOfYear) && o.getCreateAt().isBefore(endMonthOfYear))
                    .collect(Collectors.toList());
            for(Order order: monthOfYear){
                totalQuantity += order.getTotalItem();
            }
            quantities.add(totalQuantity);
        }

        return quantities;
    }

    @Override
    public List<Long> getToTalPriceProductSold(int year){
        ArrayList<Long> listTotalPrices = new ArrayList<>();
        int[] month = {1,2,3,4,5,6,7,8,9,10,11,12};

        for (int i = 1; i <=12; i++){
            YearMonth yearMonth = YearMonth.of(year, i);
            LocalDateTime startMonthOfYear = yearMonth.atDay(1).atStartOfDay();
            LocalDateTime endMonthOfYear = yearMonth.atEndOfMonth().atTime(23, 59);
            long totalPrice = 0;
            List<Order> monthOfYear = orderDao.findAll().stream()
                    .filter(o -> o.getCreateAt().isAfter(startMonthOfYear) && o.getCreateAt().isBefore(endMonthOfYear))
                    .sorted(Comparator.comparing(Order::getTotalPrice).reversed())
                    .collect(Collectors.toList());
            for(Order order: monthOfYear){
                totalPrice += order.getTotalPrice();
            }
            listTotalPrices.add(totalPrice);
        }

        return listTotalPrices;
    }

    @Override
    public List<Order> findOrder(String email,  Long id){
        List<Order> orders = orderDao.findOrders(email, id);
        return orders;
    }


    @Override
    public List<Order> findOrdersByStatus(String status){
        List<Order> orders = new ArrayList<>();
        for(Order order : orderDao.findAll()){
            if(order.getOrderStatus().equals(status)){
                orders.add(order);
            }
        }

        return orders;
    }
}
