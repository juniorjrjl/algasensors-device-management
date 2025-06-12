package com.algaworks.algasensors.device.management.api.controller;

import com.algaworks.algasensors.device.management.api.client.SensorMonitoringClient;
import com.algaworks.algasensors.device.management.api.model.SensorFoundResponse;
import com.algaworks.algasensors.device.management.api.model.SensorInsertRequest;
import com.algaworks.algasensors.device.management.api.model.SensorInsertedResponse;
import com.algaworks.algasensors.device.management.api.model.SensorPagedResponse;
import com.algaworks.algasensors.device.management.api.model.SensorUpdateRequest;
import com.algaworks.algasensors.device.management.api.model.SensorUpdatedResponse;
import com.algaworks.algasensors.device.management.common.IdGenerator;
import com.algaworks.algasensors.device.management.domain.model.SensorEntity;
import com.algaworks.algasensors.device.management.domain.model.SensorId;
import com.algaworks.algasensors.device.management.domain.repository.SensorRepository;
import io.hypersistence.tsid.TSID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/api/sensors")
@RequiredArgsConstructor
public class SensorController {

    private final SensorRepository repository;
    private final SensorMonitoringClient sensorMonitoringClient;

    @PostMapping
    @ResponseStatus(CREATED)
    SensorInsertedResponse create(@RequestBody final SensorInsertRequest request){
        var entity = new SensorEntity(
                new SensorId(IdGenerator.generateTSID()),
                request.getName(),
                request.getIp(),
                request.getLocation(),
                request.getProtocol(),
                request.getModel(),
                request.getEnabled()
        );
        var inserted = repository.save(entity);
        return new SensorInsertedResponse(
                inserted.getId().getValue(),
                inserted.getName(),
                inserted.getIp(),
                inserted.getLocation(),
                inserted.getProtocol(),
                inserted.getModel(),
                inserted.getEnabled()
        );
    }

    @PutMapping("{id}")
    SensorUpdatedResponse update(@PathVariable final TSID id, @RequestBody final SensorUpdateRequest request){
        var entity = find(id);
        entity.setId(new SensorId(id));
        entity.setName(request.getName());
        entity.setIp(request.getIp());
        entity.setLocation(request.getLocation());
        entity.setProtocol(request.getProtocol());
        entity.setModel(request.getModel());
        var updated = repository.save(entity);
        return new SensorUpdatedResponse(
                updated.getId().getValue(),
                updated.getName(),
                updated.getIp(),
                updated.getLocation(),
                updated.getProtocol(),
                updated.getModel(),
                updated.getEnabled()
        );
    }

    @PutMapping("{id}/enable")
    @ResponseStatus(NO_CONTENT)
    void enable(@PathVariable final TSID id){
        changeActivation(id, true);
        sensorMonitoringClient.enableMonitoring(id);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    void delete(@PathVariable final TSID id){
        find(id);
        repository.deleteById(new SensorId(id));
        sensorMonitoringClient.disableMonitoring(id);
    }

    @DeleteMapping("{id}/enable")
    @ResponseStatus(NO_CONTENT)
    void disable(@PathVariable final TSID id){
        changeActivation(id, false);
        sensorMonitoringClient.disableMonitoring(id);
    }

    @GetMapping("{id}")
    SensorFoundResponse findById(@PathVariable final TSID id){
        var entity = find(id);
        return new SensorFoundResponse(
                entity.getId().getValue(),
                entity.getName(),
                entity.getIp(),
                entity.getLocation(),
                entity.getProtocol(),
                entity.getModel(),
                entity.getEnabled()
        );
    }

    @GetMapping
    Page<SensorPagedResponse> search(final Pageable pageable){
        return repository.findAll(pageable).map(s -> new SensorPagedResponse(
                s.getId().getValue(),
                s.getName(),
                s.getProtocol(),
                s.getModel(),
                s.getEnabled()
        ));
    }

    private void changeActivation(final TSID id, final Boolean enabled){
        var entity = find(id);
        entity.setEnabled(enabled);
        repository.save(entity);
    }

    private SensorEntity find(final TSID id){
        return repository.findById(new SensorId(id))
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
    }

}
