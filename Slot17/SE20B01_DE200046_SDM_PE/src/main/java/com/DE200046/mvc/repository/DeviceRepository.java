
package com.DE200046.mvc.repository;

import com.DE200046.mvc.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeviceRepository extends JpaRepository<Device, Integer> {

    // Q1.1: Hien thi tat ca, ngay lap dat moi nhat truoc
    List<Device> findAllByOrderByInstallationDateDesc();

    // Q1.2: Tim kiem mot phan ten, khong phan biet hoa thuong
    List<Device> findByDeviceNameContainingIgnoreCaseOrderByInstallationDateDesc(
            String deviceName
    );

    // Q3: Kiem tra ma thiet bi da ton tai
    boolean existsByDeviceCode(String deviceCode);
}
