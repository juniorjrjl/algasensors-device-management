package com.algaworks.algasensors.device.management.domain.model;

import io.hypersistence.tsid.TSID;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SENSORS")
public class SensorEntity {

    @Id
    @AttributeOverride(name = "value", column = @Column(name = "id", columnDefinition = "BIGINT"))
    private SensorId id;
    private String name;
    private String ip;
    private String location;
    private String protocol;
    private String model;
    private Boolean enabled;

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof SensorEntity sensorEntity)) return false;
        return Objects.equals(id, sensorEntity.id) &&
                Objects.equals(name, sensorEntity.name) &&
                Objects.equals(ip, sensorEntity.ip) &&
                Objects.equals(location, sensorEntity.location) &&
                Objects.equals(protocol, sensorEntity.protocol) &&
                Objects.equals(model, sensorEntity.model) &&
                Objects.equals(enabled, sensorEntity.enabled);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, ip, location, protocol, model, enabled);
    }
}
