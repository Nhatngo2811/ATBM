package vn.edu.hcmuaf.fit.project_fruit.dao.cart;

import vn.edu.hcmuaf.fit.project_fruit.dao.model.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class Cart {
    private Map<Integer, CartProduct> cartProduct = new HashMap<>();

    // Thêm sản phẩm vào giỏ hàng
    public boolean addProduct(Product p, int quantity){
        if (cartProduct.containsKey(p.getId_product())){
            return update(p.getId_product(), cartProduct.get(p.getId_product()).getQuantity() + quantity);
        }
        CartProduct cartItem = convert(p);
        cartItem.setQuantity(quantity); // Đặt số lượng mong muốn
        cartProduct.put(p.getId_product(), cartItem);
        return true;
    }


    // Cập nhật số lượng của sản phẩm trong giỏ hàng
    public boolean update(int id, int quantity){
        if (cartProduct.containsKey(id)){
            CartProduct cp = cartProduct.get(id);
            cp.setQuantity(quantity);
            cartProduct.put(cp.getId_product(), cp);
            return true;
        }
        return false;
    }

    // Xóa sản phẩm khỏi giỏ hàng
    public boolean remove(int id){
        return cartProduct.remove(id) != null;
    }

    // Lấy danh sách tất cả sản phẩm trong giỏ hàng
    public List<CartProduct> getList(){
        return new ArrayList<>(cartProduct.values());
    }

    // Lấy sản phẩm theo productId
    public CartProduct getProductById(int productId){
        return cartProduct.get(productId);
    }

    // Lấy tổng số lượng sản phẩm trong giỏ hàng
    public int getTotalQuantity(){
        AtomicInteger totalQuantity = new AtomicInteger(0);
        cartProduct.values().forEach(cp -> totalQuantity.addAndGet(cp.getQuantity()));
        return totalQuantity.get();
    }

    // Tính tổng giá trị của giỏ hàng
    public Double getTotalPrice(){
        AtomicReference<Double> totalPrice = new AtomicReference<>(0.0);
        cartProduct.values().forEach(cp -> totalPrice.updateAndGet(v -> v + (cp.getQuantity() * cp.getPrice())));
        return totalPrice.get();
    }

    // Chuyển đổi từ Product sang CartProduct
    private CartProduct convert(Product p){
        CartProduct cartProduct = new CartProduct();
        cartProduct.setId_product(p.getId_product());
        cartProduct.setName(p.getName());
        cartProduct.setPrice(p.getPrice());
        cartProduct.setListImg(p.getListimg());
        cartProduct.setQuantity(1);
        return cartProduct;
    }
}