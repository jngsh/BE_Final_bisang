package com.exam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.exam.config.ProductsMapper;
import com.exam.service.CartService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class CartController {

    Logger logger = LoggerFactory.getLogger(CartController.class);

    @Autowired
    CartService cartService;

    @Autowired
    ProductsMapper productsMapper;

    @GetMapping("/products")
    public String viewProducts(Model model) {
        try {
            model.addAttribute("products", productsMapper.findAll());
            return "products";
        } catch (Exception e) {
            logger.error("제품을 가져오는 중 오류 발생", e);
            model.addAttribute("errorMessage", "제품을 가져오는 중 오류가 발생했습니다. 나중에 다시 시도해 주세요.");
            return "error";
        }
    }

    @PostMapping("/add-to-cart")
    public String addToCart(@RequestParam int cartId, @RequestParam int productId, Model model) {
        try {
            cartService.addProductToCart(cartId, productId);
            return "redirect:/cart?cartId=" + cartId;
        } catch (Exception e) {
            logger.error("장바구니에 제품을 추가하는 중 오류 발생", e);
            model.addAttribute("errorMessage", "장바구니에 제품을 추가하는 중 오류가 발생했습니다. 나중에 다시 시도해 주세요.");
            return "error";
        }
    }

    @GetMapping("/cart")
    public String viewCart(@RequestParam int cartId, Model model) {
        try {
            model.addAttribute("cartItems", cartService.getCartItems(cartId));
            return "cart";
        } catch (Exception e) {
            logger.error("장바구니 항목을 가져오는 중 오류 발생", e);
            model.addAttribute("errorMessage", "장바구니 항목을 가져오는 중 오류가 발생했습니다. 나중에 다시 시도해 주세요.");
            return "error";
        }
    }
}
