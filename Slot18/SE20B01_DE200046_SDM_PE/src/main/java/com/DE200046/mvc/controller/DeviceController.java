
package com.DE200046.mvc.controller;

import com.DE200046.mvc.dto.DeviceDTO;
import com.DE200046.mvc.entity.Device;
import com.DE200046.mvc.service.CategoryService;
import com.DE200046.mvc.service.DeviceService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
public class DeviceController {

    private final DeviceService deviceService;
    private final CategoryService categoryService;

    public DeviceController(DeviceService deviceService,
                            CategoryService categoryService) {
        this.deviceService = deviceService;
        this.categoryService = categoryService;
    }

    private Map<String, String> statusOptions() {
        Map<String, String> statuses = new LinkedHashMap<>();
        statuses.put("AC", "Active");
        statuses.put("IN", "Inactive");
        statuses.put("DR", "Draft");
        return statuses;
    }

    // Q1: List and Search
    @GetMapping
    public String list(
            @RequestParam(value = "deviceName", required = false)
            String deviceName,
            Model model) {

        String keyword = deviceName == null ? "" : deviceName.trim();

        List<Device> devices = keyword.isEmpty()
                ? deviceService.getAllDevices()
                : deviceService.searchByName(keyword);

        model.addAttribute("devices", devices);
        model.addAttribute("keyword", keyword);

        return "device-list";
    }

    // Q2: Delete
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id,
                         RedirectAttributes redirectAttributes) {
        try {
            deviceService.deleteDevice(id);
            redirectAttributes.addFlashAttribute(
                    "successMessage", "Deleted successfully"
            );
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute(
                    "errorMessage", e.getMessage()
            );
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute(
                    "errorMessage", "Device not found"
            );
        }

        return "redirect:/";
    }

    // Q3.1: Show Add form
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("DeviceDTO", new DeviceDTO());
        model.addAttribute("categories",
                categoryService.getAllCategories());
        model.addAttribute("statuses", statusOptions());

        return "device-add";
    }

    // Q3.2: Validate and Create
    @PostMapping("/add")
    public String create(
            @Valid @ModelAttribute("DeviceDTO") DeviceDTO deviceForm,
            BindingResult bindingResult,
            Model model) {

        if (deviceForm.getDeviceCode() != null) {
            deviceForm.setDeviceCode(
                    deviceForm.getDeviceCode().trim()
            );
        }

        deviceService.validateBusinessRules(
                deviceForm, bindingResult
        );

        model.addAttribute("categories",
                categoryService.getAllCategories());
        model.addAttribute("statuses", statusOptions());

        if (bindingResult.hasErrors()) {
            return "device-add";
        }

        try {
            deviceService.createDevice(deviceForm);
        } catch (IllegalArgumentException e) {
            bindingResult.rejectValue(
                    "deviceCode", "invalid", e.getMessage()
            );
            return "device-add";
        }

        model.addAttribute("DeviceDTO", new DeviceDTO());
        model.addAttribute(
                "successMessage",
                "Created new device successfully"
        );

        return "device-add";
    }

    // Q4: View details
    @GetMapping("/view/{id}")
    public String view(@PathVariable Integer id, Model model) {
        Device device = deviceService.getById(id);

        if (device == null) {
            model.addAttribute("errorMessage", "Device not found");
        } else {
            model.addAttribute("device", device);
        }

        return "device-view";
    }
}
