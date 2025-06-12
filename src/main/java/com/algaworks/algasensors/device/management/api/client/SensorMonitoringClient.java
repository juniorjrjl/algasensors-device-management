package com.algaworks.algasensors.device.management.api.client;

import io.hypersistence.tsid.TSID;

public interface SensorMonitoringClient {

    void enableMonitoring(final TSID id);
    void disableMonitoring(final TSID id);

}
