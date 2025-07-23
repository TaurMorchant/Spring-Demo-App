package org.morchant.spring.logback;


import org.qubership.cloud.bluegreen.api.model.BlueGreenState;
import org.qubership.cloud.bluegreen.api.model.NamespaceVersion;
import org.qubership.cloud.bluegreen.api.model.State;
import org.qubership.cloud.bluegreen.api.model.Version;
import org.qubership.cloud.bluegreen.api.service.BlueGreenStatePublisher;
import org.qubership.cloud.bluegreen.impl.service.InMemoryBlueGreenStatePublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;

@RestController
public class HelloController {
    private static final Logger log = LoggerFactory.getLogger(HelloController.class);

    final BlueGreenStatePublisher blueGreenStatePublisher;

    public HelloController(BlueGreenStatePublisher blueGreenStatePublisher) {
        this.blueGreenStatePublisher = blueGreenStatePublisher;
    }

    @GetMapping("/hello")
    public String hello() {
        log.info("Received request for hello endpoint");

        return "Hello, World!";
    }

    @GetMapping("/legacy")
    public String getLegacy() {
        NamespaceVersion newNamespaceVersion = new NamespaceVersion("default", State.LEGACY, new Version("v1"));
        ((InMemoryBlueGreenStatePublisher) blueGreenStatePublisher)
                .setBlueGreenState(new BlueGreenState(newNamespaceVersion, OffsetDateTime.now()));
        log.info("BG State changed to LEGACY");
        return "BG State changed to LEGACY";
    }

    @GetMapping("/active")
    public String getActive() {
        NamespaceVersion newNamespaceVersion = new NamespaceVersion("default", State.ACTIVE, new Version("v1"));
        ((InMemoryBlueGreenStatePublisher) blueGreenStatePublisher)
                .setBlueGreenState(new BlueGreenState(newNamespaceVersion, OffsetDateTime.now()));
        log.info("BG State changed to ACTIVE");
        return "BG State changed to ACTIVE";
    }
}

