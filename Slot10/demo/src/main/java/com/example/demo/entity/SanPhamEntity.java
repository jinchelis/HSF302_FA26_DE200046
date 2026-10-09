package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "san_pham")
public class SanPhamEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ma_sp", nullable = false, unique = true, length = 20)
    private String maSP;

    @Column(name = "ten_sp", nullable = false, length = 100)
    private String tenSP;

    @Column(name = "gia", nullable = false)
    private Double gia;

    @Column(name = "so_luong", nullable = false)
    private Integer soLuong;

    public SanPhamEntity() {
    }

    public SanPhamEntity(String maSP, String tenSP,
                         Double gia, Integer soLuong) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.gia = gia;
        this.soLuong = soLuong;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public Double getGia() {
        return gia;
    }

    public void setGia(Double gia) {
        this.gia = gia;
    }

    public Integer getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Integer soLuong) {
        this.soLuong = soLuong;
    }
}