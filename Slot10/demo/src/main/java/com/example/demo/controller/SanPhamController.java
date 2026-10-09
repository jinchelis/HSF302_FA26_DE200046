package com.example.demo.controller;

import com.example.demo.model.SanPham;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class SanPhamController {

    private final List<SanPham> danhSach = new ArrayList<>();

    // GET: Hiển thị form nhập sản phẩm
    @GetMapping("/sanpham/them")
    public String hienThiForm(Model model) {

        model.addAttribute("sanPham", new SanPham());

        return "sanpham/form";
    }

    // POST: Nhận dữ liệu từ form
    @PostMapping("/sanpham/them")
    public String themSanPham(
            @ModelAttribute("sanPham") SanPham sanPham,
            RedirectAttributes redirectAttributes) {

        danhSach.add(sanPham);

        redirectAttributes.addFlashAttribute(
                "thongBao",
                "Thêm sản phẩm thành công!"
        );

        return "redirect:/sanpham/danhsach";
    }

    // GET: Hiển thị danh sách sản phẩm
    @GetMapping("/sanpham/danhsach")
    public String danhSachSanPham(Model model) {

        model.addAttribute("danhSach", danhSach);

        return "sanpham/danh-sach";
    }
}