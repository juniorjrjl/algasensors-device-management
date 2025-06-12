package com.algaworks.algasensors.device.management.common;

import io.hypersistence.tsid.TSID;
import lombok.NoArgsConstructor;

import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class IdGenerator {

    private static final TSID.Factory tsidFactory;

    static {
        Optional.ofNullable(System.getenv("tsid.node"))
                .ifPresent(node -> System.setProperty("tsid.node", node));
        Optional.ofNullable(System.getenv("tsid.node.count"))
                .ifPresent(nodeCount -> System.setProperty("tsid.node.count", nodeCount));

        tsidFactory = TSID.Factory.builder().build();
    }

    public static TSID generateTSID(){
        return tsidFactory.generate();
    }

}
