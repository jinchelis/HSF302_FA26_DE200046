package com.example.demo.service;

import com.example.demo.model.KhoaHoc;
import com.example.demo.repository.KhoaHocRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class KhoaHocService {

    private final KhoaHocRepository repo;

    // Constructor Injection
    public KhoaHocService(KhoaHocRepository repo) {
        this.repo = repo;
    }

    // Lấy danh sách và tìm kiếm theo tên khóa học
    public List<KhoaHoc> search(String keyword) {
        Sort sort = Sort.by("id");

        if (keyword == null || keyword.isBlank()) {
            return repo.findAll(sort);
        }

        return repo.findByTenKhoaHocContainingIgnoreCase(
                keyword.trim(),
                sort
        );
    }

    // Tìm khóa học theo ID
    public Optional<KhoaHoc> findById(Long id) {
        return repo.findById(id);
    }

    // Thêm hoặc cập nhật khóa học
    @Transactional
    public KhoaHoc save(KhoaHoc kh) {
        return repo.save(kh);
    }

    // Xóa khóa học theo ID
    @Transactional
    public boolean delete(Long id) {
        if (!repo.existsById(id)) {
            return false;
        }

        repo.deleteById(id);
        return true;
    }
}