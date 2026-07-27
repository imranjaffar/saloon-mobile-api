package com.saloon.saloon_mobile_api.service;


import com.saloon.saloon_mobile_api.dto.ServiceRequest;
import com.saloon.saloon_mobile_api.dto.ServiceResponse;
import com.saloon.saloon_mobile_api.entity.Services;
import com.saloon.saloon_mobile_api.mapper.ServiceMapper;
import com.saloon.saloon_mobile_api.repository.SalonServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class SaloonServiceImpl
        implements SaloonService {


    private final SalonServiceRepository repository;

    private final ServiceMapper serviceMapper;


    @Override
    public ServiceResponse create(ServiceRequest request) {

        Services service = Services.builder()
                .name(request.getName())
                .active(true)
                .description(request.getDescription())
                .durationMinutes(request.getDurationMinutes())
                .price(request.getPrice())
                .build();

        Services saved = repository.save(service);

        return serviceMapper.toResponse(saved);
    }


    @Override
    public ServiceResponse update(
            Long id,
            ServiceRequest request) {

        Services service = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Service not found"));

        service.setName(request.getName());
        service.setDescription(request.getDescription());
        service.setDurationMinutes(request.getDurationMinutes());
        service.setPrice(request.getPrice());

        Services updated = repository.save(service);

        return serviceMapper.toResponse(updated);
    }


    @Override
    public void delete(Long id) {

        Services service =
                repository.findById(id)
                        .orElseThrow();


        service.setActive(false);

        repository.save(service);

    }


    @Override
    public List<ServiceResponse> getAll() {
        List<Services> servicesList = repository.findByActiveTrue();
        return servicesList.stream().map(serviceMapper::toResponse).collect(Collectors.toList());

    }


    @Override
    public ServiceResponse getById(Long id) {

        Services services = repository.findById(id)
                .orElseThrow();
        return serviceMapper.toResponse(services);
    }


}
