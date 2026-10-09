package com.example.demo.controller;

import com.example.demo.entity.SanPhamEntity;
import com.example.demo.service.SanPhamService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/sanpham-jpa")
public class SanPhamJpaController {

    private final SanPhamService sanPhamService;

    public SanPhamJpaController(SanPhamService sanPhamService) {
        this.sanPhamService = sanPhamService;
    }

    @GetMapping("/danhsach")
    public String danhSach(Model model) {
        model.addAttribute("dsSanPham", sanPhamService.findAll());
        return "sanpham-jpa/danh-sach";
    }

    @GetMapping("/them")
    public String hienThiForm(Model model) {
        model.addAttribute("sanPham", new SanPhamEntity());
        return "sanpham-jpa/form";
    }

    @PostMapping("/them")
    public String themSanPham(
            @ModelAttribute("sanPham") SanPhamEntity sanPham,
            RedirectAttributes redirectAttributes) {

        sanPhamService.save(sanPham);

        redirectAttributes.addFlashAttribute(
                "message", "Thêm sản phẩm thành công!"
        );

        return "redirect:/sanpham-jpa/danhsach";
    }
}