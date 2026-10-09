package com.example.demo.controller;

import com.example.demo.entity.SanPhamEntity;
import com.example.demo.service.SanPhamService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
    public String hienThiFormThem(Model model) {
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

    @GetMapping("/sua/{id}")
    public String hienThiFormSua(@PathVariable Long id, Model model) {
        SanPhamEntity sanPham = sanPhamService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Không tìm thấy sản phẩm ID: " + id
                ));

        model.addAttribute("sanPham", sanPham);
        return "sanpham-jpa/form-sua";
    }

    @PostMapping("/sua/{id}")
    public String capNhatSanPham(
            @PathVariable Long id,
            @ModelAttribute("sanPham") SanPhamEntity sanPham,
            RedirectAttributes redirectAttributes) {

        SanPhamEntity hienTai = sanPhamService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Không tìm thấy sản phẩm ID: " + id
                ));

        hienTai.setMaSP(sanPham.getMaSP());
        hienTai.setTenSP(sanPham.getTenSP());
        hienTai.setGia(sanPham.getGia());
        hienTai.setSoLuong(sanPham.getSoLuong());

        sanPhamService.save(hienTai);

        redirectAttributes.addFlashAttribute(
                "message", "Cập nhật sản phẩm thành công!"
        );

        return "redirect:/sanpham-jpa/danhsach";
    }

    @PostMapping("/xoa/{id}")
    public String xoaSanPham(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes) {

        if (sanPhamService.findById(id).isEmpty()) {
            redirectAttributes.addFlashAttribute(
                    "error", "Không tìm thấy sản phẩm!"
            );
        } else {
            sanPhamService.deleteById(id);
            redirectAttributes.addFlashAttribute(
                    "message", "Xóa sản phẩm thành công!"
            );
        }

        return "redirect:/sanpham-jpa/danhsach";
    }
}