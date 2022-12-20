package pt.amado.cycloidepg.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table
@Data
public class Channel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private long id;

    @Column
    private String name;

    @Column
    private int position;

    @Column
    private String category;
}
