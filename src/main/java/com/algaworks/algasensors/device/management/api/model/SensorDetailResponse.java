package com.algaworks.algasensors.device.management.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SensorDetailResponse {

    private SensorFoundResponse sensor;
    private SensorMonitoringDetailResponse monitoring;

}
