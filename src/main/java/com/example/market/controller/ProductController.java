package com.example.market.controller;

import com.example.market.dto.SearchParam;
import com.example.market.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/")
    public String getMainPage(Model model) {

        model.addAttribute("page", 1);
        model.addAttribute("popularProducts", productService.getPopularProducts());

        return "home";
    }


    /**
     * http://localhost:8080/product/detail/1
     *
     * @param model
     * @param productCode
     * @return
     */
    @GetMapping("/product/detail/{productCode}")
    public String getProductDetailPage(Model model, @PathVariable("productCode") String productCode) {
        model.addAttribute("product", productService.getProductDataByProductCode(productCode));
        return "product_details";
    }

//    @GetMapping("/products")
//    public String getProducts(Model model, @ModelAttribute SearchParam searchParam) {
//
//        // System.out.println("ProductsController - ");
//        int totalProducts = productService.getAllProductsCnt();    // 총 상품 개수
//        int productsPerPage = 12;       // 한 페이지당 표시할 상품의 개수
//        int totalPages = ((totalProducts-1) / productsPerPage) + 1;             // 총 페이지 개수
//        int displayPageNum = 10;         // 한 번에 표시할 페이지의 개수
//        int startPage = ((searchParam.getCurrentPage()-1) / displayPageNum) * displayPageNum + 1;              // 시작 페이지 번호
//        boolean prevTF = startPage != 1;         // 이전으로 가는 화살표 표시 여부
//        int endPage = (((searchParam.getCurrentPage()-1)/displayPageNum)+1) * displayPageNum;                // 끝 페이지 번호
//        if (totalPages < endPage) endPage = totalPages;
//        boolean nextTF = endPage != totalPages;         // 다음으로 가는 화살표 표시 여부
//        int startIdx = ((searchParam.getCurrentPage()-1) * productsPerPage)+1;
//
//        model.addAttribute("products", productService.getProducts(startIdx, productsPerPage, ""));
//        model.addAttribute("prevTF", prevTF);
//        model.addAttribute("startPage", startPage);
//        model.addAttribute("endPage", endPage);
//        model.addAttribute("nextTF", nextTF);
//
//        return "products";
//    }

    @PostMapping("/product")
    public String getProducts(Model model, @ModelAttribute SearchParam searchParam) {

//        if (searchParam) {
//            searchParam.setPageSize(12);
//        }

        System.out.println("ProductsController - PostMapping");

        int totalProducts = productService.getAllProductsCnt();    // 총 상품 개수
        int totalPages = ((totalProducts-1) / searchParam.getPageSize()) + 1;             // 총 페이지 개수
        int displayPageNum = 10;         // 한 번에 표시할 페이지의 개수
        int startPage = ((searchParam.getCurrentPage()-1) / displayPageNum) * displayPageNum + 1;              // 시작 페이지 번호
        boolean prevTF = startPage != 1;         // 이전으로 가는 화살표 표시 여부
        int endPage = (((searchParam.getCurrentPage()-1)/displayPageNum)+1) * displayPageNum;                // 끝 페이지 번호
        if (totalPages < endPage) endPage = totalPages;
        boolean nextTF = endPage != totalPages;         // 다음으로 가는 화살표 표시 여부
        int startIdx = ((searchParam.getCurrentPage()-1) * searchParam.getPageSize())+1;
        System.out.println(startIdx);
        System.out.println(searchParam);

        model.addAttribute("products", productService.getProducts(startIdx, searchParam));
        model.addAttribute("prevTF", prevTF);
        model.addAttribute("startPage", startPage);
        model.addAttribute("currentPage", searchParam.getCurrentPage());
        model.addAttribute("endPage", endPage);
        model.addAttribute("nextTF", nextTF);


        return "products";
    }
}
