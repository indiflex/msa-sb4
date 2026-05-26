package com.msa.user;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.AdminClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/kafka")
public class KafkaAdminController {
    private final AdminClient adminClient;

    @GetMapping("topics")
    public Set<String> getTopics() throws ExecutionException, InterruptedException {
        return adminClient.listTopics().names().get();
    }

    @DeleteMapping("topics")
    ResponseEntity<String> deleteTopics(@RequestParam List<String> topics) throws Exception {
        adminClient.deleteTopics(topics).all().get();
        return ResponseEntity.ok(topics + " deleted");
    }
}
