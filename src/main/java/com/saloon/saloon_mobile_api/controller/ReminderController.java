package com.saloon.saloon_mobile_api.controller;

import com.saloon.saloon_mobile_api.dto.ReminderRequest;
import com.saloon.saloon_mobile_api.dto.ReminderResponse;
import com.saloon.saloon_mobile_api.service.ReminderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reminders")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ReminderController {

    private final ReminderService service;

    @PostMapping
    public ReminderResponse create(
            @RequestBody ReminderRequest request) {

        return service.create(request);
    }

    @GetMapping
    public List<ReminderResponse> getAll() {
        return service.getAll();
    }

    @GetMapping("/today")
    public List<ReminderResponse> getToday() {
        return service.getToday();
    }

    @GetMapping("/pending")
    public List<ReminderResponse> getPending() {
        return service.getPending();
    }

    @GetMapping("/customer/{customerId}")
    public List<ReminderResponse> getCustomer(
            @PathVariable Long customerId) {

        return service.getCustomer(customerId);
    }

    @PutMapping("/{id}/complete")
    public ReminderResponse complete(
            @PathVariable Long id) {

        return service.complete(id);
    }

    @PutMapping("/{id}/cancel")
    public ReminderResponse cancel(
            @PathVariable Long id) {

        return service.cancel(id);
    }

    @DeleteMapping("/{id}")
    public void delete(
            @PathVariable Long id) {

        service.delete(id);
    }

    @GetMapping("/due")
    public List<ReminderResponse> dueReminders() {
        return service.getDueReminders();
    }


}