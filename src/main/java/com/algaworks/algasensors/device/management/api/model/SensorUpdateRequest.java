package com.algaworks.algasensors.device.management.api.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SensorUpdateRequest {

    private String name;
    private String ip;
    private String location;
    private String protocol;
    private String model;

}
