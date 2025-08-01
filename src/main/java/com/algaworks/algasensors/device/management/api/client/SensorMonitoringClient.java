package com.algaworks.algasensors.device.management.api.client;

import com.algaworks.algasensors.device.management.api.model.SensorMonitoringDetailResponse;
import io.hypersistence.tsid.TSID;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PutExchange;

@HttpExchange("/api/sensors/{sensorId}")
public interface SensorMonitoringClient {

    @PutExchange("/enable")
    void enableMonitoring(@PathVariable final TSID sensorId);

    @DeleteExchange("/enable")
    void disableMonitoring(@PathVariable final TSID sensorId);

    @GetExchange
    SensorMonitoringDetailResponse getDetail(@PathVariable final TSID sensorId);

}
