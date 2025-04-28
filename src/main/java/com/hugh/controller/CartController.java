package com.hugh.controller;

import com.hugh.aop.RequireLogin;
import com.hugh.domain.CartItem;
import com.hugh.domain.User;
import com.hugh.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 购物车控制器
 */
@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    /**
     * 获取当前用户的购物车
     */
    @GetMapping
    @RequireLogin
    public Result getCart(HttpSession session) {
        User user = (User) session.getAttribute("user");
        List<CartItem> cartItems = cartService.getCartItems(user.getUserId());
        
        // 计算总金额
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (CartItem item : cartItems) {
            totalAmount = totalAmount.add(item.getSubtotal());
        }
        
        Map<String, Object> data = new HashMap<>();
        data.put("items", cartItems);
        data.put("totalAmount", totalAmount);
        
        return Result.success(data);
    }

    /**
     * 添加商品到购物车
     */
    @PostMapping("/add")
    @RequireLogin
    public Result addToCart(@RequestBody Map<String, Object> requestMap, HttpSession session) {
        User user = (User) session.getAttribute("user");

        Integer bookId = Integer.parseInt(requestMap.get("bookId").toString());
        Integer quantity = Integer.parseInt(requestMap.get("quantity").toString());
        
        if (quantity <= 0) {
            return Result.error("数量必须大于0");
        }

        boolean success = cartService.addToCart(user.getUserId(), bookId, quantity);
        
        if (success) {
            return Result.success("已成功添加到购物车");
        } else {
            return Result.error("添加失败，可能是库存不足");
        }
    }

    /**
     * 更新购物车项数量
     */
    @PutMapping("/update")
    @RequireLogin
    public Result updateQuantity(@RequestBody Map<String, Object> requestMap, HttpSession session) {
        User user = (User) session.getAttribute("user");

        Integer cartItemId = Integer.parseInt(requestMap.get("cartItemId").toString());
        Integer quantity = Integer.parseInt(requestMap.get("quantity").toString());
        
        if (quantity <= 0) {
            return Result.error("数量必须大于0");
        }

        boolean success = cartService.updateQuantity(cartItemId, quantity);
        
        if (success) {
            return Result.success("数量已更新");
        } else {
            return Result.error("更新失败");
        }
    }

    /**
     * 从购物车中删除项
     */
    @DeleteMapping("/remove/{cartItemId}")
    @RequireLogin
    public Result removeFromCart(@PathVariable Integer cartItemId, HttpSession session) {
        User user = (User) session.getAttribute("user");

        boolean success = cartService.removeFromCart(cartItemId);
        
        if (success) {
            return Result.success("已从购物车中移除");
        } else {
            return Result.error("移除失败");
        }
    }

    /**
     * 清空购物车
     */
    @DeleteMapping("/clear")
    @RequireLogin
    public Result clearCart(HttpSession session) {
        User user = (User) session.getAttribute("user");

        boolean success = cartService.clearCart(user.getUserId());
        
        if (success) {
            return Result.success("购物车已清空");
        } else {
            return Result.error("清空购物车失败");
        }
    }
}
