package pt.amado.cycloidepg.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "Program")
@Data
public class Program {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channel")
    private Channel channel;

    @Column
    private String imageUrl;

    @Column
    private String title;

    @Column
    private String description;

    @Column
    private LocalDateTime startTime;

    @Column
    private LocalDateTime endTime;

}
