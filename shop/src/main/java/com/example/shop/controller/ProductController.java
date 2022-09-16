package com.example.shop.controller;

import com.example.shop.entity.dto.ProductDto;
import com.example.shop.entity.model.Category;
import com.example.shop.entity.model.Product;
import com.example.shop.service.CategoryService;
import com.example.shop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    @GetMapping("/products")
    public String products(Model model){

        List<ProductDto> productDtoList = productService.findByAll();
        model.addAttribute("title", "Manage Product");
        model.addAttribute("products", productDtoList);
        model.addAttribute("size", productDtoList.size());
        return "products";
    }

    @GetMapping("/products/{pageNo}")
    public String productsPage(@PathVariable("pageNo") int pageNo, Model model){

        Page<Product> products = productService.pageProduct(pageNo);
        model.addAttribute("title", "Manage Product");
        model.addAttribute("size", products.getSize());
        model.addAttribute("totalPages", products.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("products", products);
        return "products";
    }


    @GetMapping("/add-product")
    public String addProductForm(Model model){

        List<Category> categories = categoryService.findByAll();
        model.addAttribute("categories", categories);
        model.addAttribute("product", new ProductDto());
        return "add-product";
    }

    @PostMapping("/save-product")
    public String saveProduct(@ModelAttribute("product")ProductDto productDto,
                              @RequestParam("imageProduct")MultipartFile imageProduct,
                              RedirectAttributes attributes){
        try {
            productService.save(imageProduct, productDto);
            attributes.addFlashAttribute("success", "Add successfully!");
        }catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("error", "Failed to add!");
        }
        return "redirect:/products/0";
    }

    @GetMapping("/update-product/{id}")
    public String updateProductForm(@PathVariable("id") Integer id, Model model){

        model.addAttribute("title", "Update products");
        List<Category> categories = categoryService.findByAll();
        ProductDto productDto = productService.getById(id);
        model.addAttribute("categories", categories);
        model.addAttribute("productDto", productDto);
        return "update-product";
    }


    @PostMapping("/update-product/{id}")
    public String processUpdate(@PathVariable("id") Long id,
                                @ModelAttribute("productDto") ProductDto productDto,
                                @RequestParam("imageProduct")MultipartFile imageProduct,
                                RedirectAttributes attributes
    ){
        try {
            productService.update(imageProduct, productDto);
            attributes.addFlashAttribute("success", "Update successfully!");
        }catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("error", "Failed to update!");
        }
        return "redirect:/products/0";

    }

    @RequestMapping(value = "/enable-product/{id}", method = {RequestMethod.PUT , RequestMethod.GET})
    public String enabledProduct(@PathVariable("id")Integer id, RedirectAttributes attributes){
        try {
            productService.enabledById(id);
            attributes.addFlashAttribute("success", "Enabled successfully!");
        }catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("error", "Failed to enabled!");
        }
        return "redirect:/products";
    }

    @RequestMapping(value = "/delete-product/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    public String deletedProduct(@PathVariable("id") Integer id, RedirectAttributes attributes){
        try {
            productService.deleteById(id);
            attributes.addFlashAttribute("success", "Deleted successfully!");
        }catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("error", "Failed to deleted");
        }
        return "redirect:/products";
    }


    @GetMapping(value = "/products-shop")
    private String productsShop(Model model){
        List<Product>products = productService.getAllProducts();
        List<Product> listViewProducts = productService.listViewProducts();
        model.addAttribute("viewProducts", listViewProducts);
        model.addAttribute("products", products);
        return "shop";
    }

    @GetMapping(value = "/find-product/{id}")
    public String findProductById (@PathVariable("id") Integer id,Model model){
        Product product = productService.getProductById(id);
        Integer categoryId = product.getCategory().getId();
        List<Product> products = productService.getRelatedProducts(categoryId);
        model.addAttribute("product", product);
        model.addAttribute("products", products);
        return "product-detail";
    }

    @GetMapping(value = "/high-price")
    public String filterHighPrice(Model model){
        List<Category> categories = categoryService.findByAll();
        List<Product> products = productService.filterHighPrice();
        model.addAttribute("products", products);
        model.addAttribute("categories", categories);
        return "filter-high-price";
    }
}
