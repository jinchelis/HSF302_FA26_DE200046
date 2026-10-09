package com.example.demo.service;

import com.example.demo.entity.SanPhamEntity;
import com.example.demo.repository.SanPhamRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SanPhamServiceImpl implements SanPhamService {

    private final SanPhamRepository sanPhamRepository;

    public SanPhamServiceImpl(SanPhamRepository sanPhamRepository) {
        this.sanPhamRepository = sanPhamRepository;
    }

    @Override
    public List<SanPhamEntity> findAll() {
        return sanPhamRepository.findAll();
    }

    @Override
    public Optional<SanPhamEntity> findById(Long id) {
        return sanPhamRepository.findById(id);
    }

    @Override
    public SanPhamEntity save(SanPhamEntity sanPham) {
        return sanPhamRepository.save(sanPham);
    }

    @Override
    public void deleteById(Long id) {
        sanPhamRepository.deleteById(id);
    }
}