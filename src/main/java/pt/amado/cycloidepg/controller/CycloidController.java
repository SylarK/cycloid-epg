package pt.amado.cycloidepg.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
public class CycloidController {

    @GetMapping
    public ResponseEntity<String> isAlive(){
        return ResponseEntity.ok("It is alive - " + LocalDateTime.now());
    }

}
