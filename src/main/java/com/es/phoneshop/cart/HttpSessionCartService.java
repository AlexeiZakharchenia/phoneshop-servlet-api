package com.es.phoneshop.cart;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Optional;

public class HttpSessionCartService implements CartService {
    public static final String SESSION_CART_KEY = "sessionCart";
    private static volatile HttpSessionCartService instance = null;
    private ProductDao productDao = ArrayListProductDao.getInstance();

    private HttpSessionCartService() {
    }

    public static CartService getInstance() {
        HttpSessionCartService localInstance = instance;

        if (instance == null) {
            synchronized (HttpSessionCartService.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new HttpSessionCartService();
                }

            }

        }
        return instance;
    }

    @Override
    public Cart getCart(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute(SESSION_CART_KEY);
        if (cart == null) {
            cart = new Cart();
            session.setAttribute(SESSION_CART_KEY, cart);
        }
        return cart;
    }

    @Override
    public void add(Cart cart, long productId, int quantity) throws OutOfStockException {
        if (quantity < 0) throw new IllegalArgumentException("Invalid input");
        Product product = productDao.getProduct(productId);
        if (quantity > product.getStock()) {
            throw new OutOfStockException("Not enougth stock. Product stock is " + product.getStock());
        }
        Optional<CartItem> cartItemOptional = cart.getCartItems().stream()
                .filter(cartItem -> Long.valueOf(productId).equals(cartItem.getProduct().getId()))
                .findAny();
        if (cartItemOptional.isPresent()) {
            CartItem cartItem = cartItemOptional.get();
            if (quantity + cartItem.getQuantity() > cartItem.getProduct().getStock()) {
                throw new OutOfStockException("Not enougth stock. Product stock is " + product.getStock());
            }
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        } else {
            CartItem cartItem = new CartItem(product, quantity);
            cart.getCartItems().add(cartItem);
        }
        recalculateTotals(cart);
    }

    @Override
    public void update(Cart cart, long productId, int quantity) throws OutOfStockException {
        if (quantity < 0) throw new IllegalArgumentException("Invalid input");
        Product product = productDao.getProduct(productId);
        if (quantity > product.getStock()) {
            throw new OutOfStockException("Not enougth stock. Product stock is " + product.getStock());
        }
        Optional<CartItem> cartItemOptional = cart.getCartItems().stream()
                .filter(cartItem -> Long.valueOf(productId).equals(cartItem.getProduct().getId()))
                .findAny();
        if (cartItemOptional.isPresent()) {
            CartItem cartItem = cartItemOptional.get();
            cartItem.setQuantity(quantity);
        } else {
            CartItem cartItem = new CartItem(product, quantity);
            cart.getCartItems().add(cartItem);
        }
        recalculateTotals(cart);
    }

    @Override
    public void delete(Cart cart, long productId) {
        Long productIdLong = productId;
        cart.getCartItems().removeIf(cartItem -> productIdLong.equals(cartItem.getProduct().getId()));
        recalculateTotals(cart);
    }

    private void recalculateTotals(Cart cart) {
        BigDecimal totalPrice = new BigDecimal(0);
        Optional<BigDecimal> actualPrice = cart.getCartItems().stream()
                .map(cartItem -> cartItem.getProduct().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())))
                .reduce(BigDecimal::add);
        if (actualPrice.isPresent()) {
            totalPrice = actualPrice.get();
        }

        Integer totalQuantity = cart.getCartItems().stream()
                .map(CartItem::getQuantity)
                .mapToInt(Integer::intValue).sum();
        cart.setTotalPrice(totalPrice);
        cart.setTotalQuantity(totalQuantity);
    }
}
