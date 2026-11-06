
package com.DE200046.mvc.service.impl;

import com.DE200046.mvc.dto.DeviceDTO;
import com.DE200046.mvc.entity.Category;
import com.DE200046.mvc.entity.Device;
import com.DE200046.mvc.repository.CategoryRepository;
import com.DE200046.mvc.repository.DeviceRepository;
import com.DE200046.mvc.service.DeviceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.List;
import java.util.Set;

@Service
public class DeviceServiceImpl implements DeviceService {

    private final DeviceRepository deviceRepository;
    private final CategoryRepository categoryRepository;

    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("dd/MM/uuuu")
                    .withResolverStyle(ResolverStyle.STRICT);

    public DeviceServiceImpl(
            DeviceRepository deviceRepository,
            CategoryRepository categoryRepository) {
        this.deviceRepository = deviceRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Device> getAllDevices() {
        return deviceRepository.findAllByOrderByInstallationDateDesc();
    }

    @Override
    public List<Device> searchByName(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return getAllDevices();
        }

        return deviceRepository
                .findByDeviceNameContainingIgnoreCaseOrderByInstallationDateDesc(
                        keyword.trim()
                );
    }

    @Override
    public Device getById(Integer id) {
        return deviceRepository.findById(id).orElse(null);
    }

    @Override
    public void validateBusinessRules(
            DeviceDTO dto,
            BindingResult result) {


        if (dto.getDeviceCode() != null
                && deviceRepository.existsByDeviceCode(dto.getDeviceCode())) {
            result.rejectValue(
                    "deviceCode", "duplicate",
                    "Device Code already exists"
            );
        }


        if (dto.getDeviceName() != null
                && !dto.getDeviceName().equals(dto.getDeviceName().trim())) {
            result.rejectValue(
                    "deviceName", "spaces",
                    "Device Name must not have spaces at both sides"
            );
        }


        if (dto.getCategoryId() != null
                && !categoryRepository.existsById(dto.getCategoryId())) {
            result.rejectValue(
                    "categoryId", "invalid",
                    "Selected category does not exist"
            );
        }


        if (dto.getInstallationDate() != null
                && !dto.getInstallationDate().isBlank()) {
            try {
                LocalDate date = LocalDate.parse(
                        dto.getInstallationDate(), DATE_FORMAT
                );

                if (!date.isAfter(LocalDate.now())) {
                    result.rejectValue(
                            "installationDate", "future",
                            "Installation Date must be a future date"
                    );
                }
            } catch (DateTimeParseException e) {
                result.rejectValue(
                        "installationDate", "format",
                        "Date must follow dd/MM/yyyy"
                );
            }
        }


        if (dto.getStatus() != null
                && !dto.getStatus().isBlank()
                && !Set.of("AC", "IN", "DR").contains(dto.getStatus())) {

            result.rejectValue(
                    "status",
                    "invalid",
                    "Status must be AC, IN or DR"
            );
        }
    }

    @Override
    @Transactional
    public void createDevice(DeviceDTO dto) {
        Category category = categoryRepository
                .findById(dto.getCategoryId())
                .orElseThrow(() ->
                        new IllegalArgumentException("Invalid category"));

        if (deviceRepository.existsByDeviceCode(dto.getDeviceCode())) {
            throw new IllegalArgumentException("Device Code already exists");
        }

        Device device = new Device();

        device.setDeviceCode(dto.getDeviceCode());
        device.setDeviceName(dto.getDeviceName());
        device.setCategory(category);
        device.setPrice(dto.getPrice());
        device.setWarrantyMonths(dto.getWarrantyMonths());
        device.setInstallationDate(
                LocalDate.parse(dto.getInstallationDate(), DATE_FORMAT)
        );
        device.setStatus(dto.getStatus());

        deviceRepository.save(device);
    }

    @Override
    @Transactional
    public void deleteDevice(Integer id) {
        Device device = deviceRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Device not found"));

        if ("AC".equals(device.getStatus())) {
            throw new IllegalStateException(
                    "Cannot delete an active device."
            );
        }

        deviceRepository.delete(device);
    }
}
