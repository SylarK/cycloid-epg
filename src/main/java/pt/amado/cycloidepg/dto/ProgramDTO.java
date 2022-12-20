package pt.amado.cycloidepg.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProgramDTO {

    private long id;
    private int channelId;
    private String imageUrl;
    private String title;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

}
