package com.algaworks.algasensors.device.management;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static java.time.temporal.ChronoUnit.MINUTES;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;
import static com.algaworks.algasensors.device.management.common.IdGenerator.generateTSID;

class TSIDTest {

    @Test
    void shouldGenerateTSID(){
        var tsid = generateTSID();
        assertThat(tsid.getInstant()).isCloseTo(Instant.now(), within(1, MINUTES));
    }

}
