package com.example.bumblebee.config;

import com.example.bumblebee.model.dao.*;
import com.example.bumblebee.model.entity.*;
import com.example.bumblebee.service.CategoryService;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;


@Component
public class DataSeeder {
    private final CategoryService categoryService;
    private final UserDao userDao;
    private final AddressDao addressDao;
    private final PasswordEncoder passwordEncoder;
    private final ProductDao productDao;

    private final ColorDao colorDao;
    private final SizeDao sizeDao;
    private final CategoryDao categoryDao;
    private final CartItemDao cartItemDao;
    private final CartDao cartDao;

    private final OrderItemDao orderItemDao;
    private final OrderDao orderDao;

    public DataSeeder(CategoryService categoryService, UserDao userDao, AddressDao addressDao, PasswordEncoder passwordEncoder, ProductDao productDao, ColorDao colorDao, SizeDao sizeDao, CategoryDao categoryDao, CartItemDao cartItemDao, CartDao cartDao, OrderItemDao orderItemDao, OrderDao orderDao){
        this.userDao = userDao;
        this.addressDao = addressDao;
        this.passwordEncoder = passwordEncoder;
        this.productDao = productDao;
        this.colorDao = colorDao;
        this.sizeDao = sizeDao;
        this.cartItemDao = cartItemDao;
        this.categoryDao = categoryDao;
        this.cartDao = cartDao;
        this.orderItemDao = orderItemDao;
        this.orderDao = orderDao;
        this.categoryService = categoryService;
    }
    public Address AddressFaker (){
        Faker faker = new Faker();
        Address address = new Address();
        address.setName(faker.name().name());
        address.setMobile(faker.phoneNumber().cellPhone());
        address.setProvince(faker.address().state());  // Tỉnh
        address.setDistrict(faker.address().cityName());  // Quận/Huyện
        address.setWard(faker.address().city());  // Phường/Xã
        address.setDescription(faker.address().streetAddress());

        return addressDao.save(address);

    }
    public void UserFaker () {
            Faker faker = new Faker();
            Random random = new Random();
            User user = new User();
            user.setName(faker.name().fullName());
            user.setEmail(faker.internet().emailAddress());
            user.setPassword(passwordEncoder.encode("Vandinh@123"));
            user.setFpoint(faker.number().randomDouble(2, 0, 1000));
            user.setCreateAt(LocalDateTime.now());

            User savedUser = userDao.save(user);
            for (int i = 0; i< random.nextInt(4); i++){
                Address address = new Address();
                if (i == 0){
                    address.setState("Mặc định");
                    address.setUser(savedUser.getId());
                    address = AddressFaker();
                }else {
                    address.setState("");
                    address.setUser(savedUser.getId());
                    address = AddressFaker();
                }
            }
        Cart cart = new Cart();
        cart.setUser(user);

    }


    public Product ProductFaker(){
        Faker faker = new Faker();
        Random random = new Random();
        Product newProduct = new Product();
        Product product = productDao.save(newProduct);

        product.setTitle(faker.commerce().productName());
        List<String> listImageUrl = new ArrayList<>();
        for (int i = 0; i < 8; i++) { // Tạo 5 URL hình ảnh giả
            listImageUrl.add(faker.internet().image(3000, 3000, true,"fashion"));
        }
        product.setListImageUrl(listImageUrl);
        product.setDescription(faker.lorem().paragraph());

        // Giá sản phẩm và giá khuyến mãi
        product.setPrice(faker.number().numberBetween(200000, 4000000));
        product.setDiscountPersent(faker.number().numberBetween(5, 70));
        product.setDiscountedPrice(product.getPrice() - (product.getPrice() * product.getDiscountPersent() / 100));

        Category existingCategory = categoryDao.findById(faker.number().numberBetween(0, 7))
                .orElseThrow(() -> new RuntimeException("Category not found"));

        // Thêm Category đã có sẵn vào Product
        product.setCategory(existingCategory);

        List<Color> colors = new ArrayList<>();
        for (int i = 1; i <= 3; i++){
            Color color = new Color();
            color.setName(faker.color().name());

            color.setImageUrl(listImageUrl.get(random.nextInt(listImageUrl.size())));
            color.setProduct(product);
            List<String> text = Arrays.asList("M", "L", "XL");
            List<Size> sizes = new ArrayList<>();
            for (String item : text) {
                Size size = new Size();
                size.setColor(colorDao.save(color));
                size.setName(item);
                sizes.add(sizeDao.save(size));
            }
            color.setSizes(sizes);
            Color saveColor = colorDao.save(color);
            colors.add(saveColor);
        }
    product.setColors(colors);
        product.setNumRatings(5);
        product.setDescription(faker.lorem().paragraph());
        product.setCreateAt(LocalDateTime.now());
        return productDao.save(product);
    }

    public CartItem CartItemFaker(){

        CartItem cartItem = new CartItem();
        List<Product> products = productDao.findAll();

        if (products.isEmpty()) {
            throw new RuntimeException("Không có sản phẩm nào trong cơ sở dữ liệu");
        }
        Random random = new Random();

        System.out.println("abc");
        // Lấy ngẫu nhiên một sản phẩm từ danh sách
        int index = random.nextInt(products.size());
        System.out.println("index product - " + index);
        Product demo = products.get(index);
        System.out.println("demo - " + demo.getTitle());
        Product product = products.get(random.nextInt(products.size()));
        System.out.println("Product -" + product.getTitle());

        cartItem.setQuantity(1);
        Color color = product.getColors().get(index);
        System.out.println("color - " + color.getName());
        System.out.println(" size - " + color.getSizes().size());
        cartItem.setColor(color.getName());
        cartItem.setImageUrl(color.getImageUrl());

        Size size = color.getSizes().get(random.nextInt(color.getSizes().size()));
        System.out.println("size - " + size.getName());
        size.setQuantity(size.getQuantity() - 1);
        sizeDao.save(size);
        cartItem.setSize(size.getName());
        product.setTotalQuantity(product.getTotalQuantity() - 1);
        cartItem.setPrice(product.getPrice());
        cartItem.setDiscountedPrice(product.getDiscountedPrice());
        cartItem.setProduct(product);

        return cartItem;
    }

    public void CartFaker(){
        Random random = new Random();
        List<Cart> carts = cartDao.findAll();
        Cart cart = carts.get(random.nextInt(carts.size()));
        Set<CartItem> cartItems = new HashSet<>();
        int quantity = random.nextInt(6);
        for (int i = 0; i< quantity; i++){
            CartItem cartItem = CartItemFaker();
            cart.getCartItems().add(cartItem);
            cart.setTotalItem(cart.getTotalItem()+1);
            cartItem.setCart(cartDao.save(cart));
            cartItem.setUserId(cart.getUser().getId());
            cartItemDao.save(cartItem);
            cartItems.add(cartItem);
        }


    }

    public OrderItem OrderItemFaker(){
        Random random = new Random();
       OrderItem orderItem = new OrderItem();
        List<User> users = userDao.findAll();
        User user = users.get(random.nextInt(users.size()));


        Cart cart = cartDao.findByUserId(user.getId());

        List<CartItem> cartItems = new ArrayList<>(cart.getCartItems());
        CartItem cartItem = cartItems.get(random.nextInt(cartItems.size()));

        orderItem.setColor(cartItem.getColor());
        orderItem.setSize(cartItem.getSize());
        orderItem.setQuantity(cartItem.getQuantity());
        orderItem.setPrice(cartItem.getPrice());
        orderItem.setDiscountedPrice(cartItem.getDiscountedPrice());
        orderItem.setProduct(cartItem.getProduct());
        orderItem.setImageUrl(cartItem.getImageUrl());
        orderItem.setUserId(user.getId());
        Optional<Product> product = productDao.findById(orderItem.getProduct().getId());

        for (Color color : product.get().getColors()){
            for (Size size : color.getSizes()){
                if (size.equals(cartItem.getSize())){
                    size.setQuantity(size.getQuantity() - cartItem.getQuantity());
                    sizeDao.save(size);
                }
            }
        }
        product.get().setTotalQuantity(product.get().getTotalQuantity() - cartItem.getQuantity());
        productDao.save(product.get());
        return orderItem;
    }

    public void OrderFaker(){
        Random random = new Random();
        Order order = new Order();
        List<User> users = userDao.findAll();
       User user = users.get(random.nextInt(users.size()));
        order.setUser(user);

        order.setAddress(user.getAddress().get(random.nextInt(user.getAddress().size())));
        int quatity = random.nextInt(6);
        for (int i =0; i < quatity; i++){
            OrderItem orderItem = OrderItemFaker();
            orderItem.setOrder(orderDao.save(order));

            orderItemDao.save(orderItem);
        }
        order.setCreateAt(LocalDateTime.now());
    }


//    @Bean
//    CommandLineRunner run (){
//        return args -> {
//            categoryService.createCategory();
//            for (int i = 0; i < 30; i++){
//                UserFaker();
//            }
//            List<User> users = userDao.findAll();
//            for(int i =0; i<users.size(); i++){
//                CartFaker();
//            }
//            ProductFaker();
//            CartFaker();
//            OrderFaker();
//        };
//    }
}
