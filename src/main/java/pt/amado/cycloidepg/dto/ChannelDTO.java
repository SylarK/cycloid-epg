package pt.amado.cycloidepg.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChannelDTO extends RepresentationModel<ChannelDTO> {

    public int id;
    public String name;
    public int position;
    public String category;

}
