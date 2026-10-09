package com.example.demo.service;

import com.example.demo.entity.SanPhamEntity;
import java.util.List;
import java.util.Optional;

public interface SanPhamService {

    List<SanPhamEntity> findAll();

    Optional<SanPhamEntity> findById(Long id);

    SanPhamEntity save(SanPhamEntity sanPham);

    void deleteById(Long id);
}