package pt.amado.cycloidepg.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pt.amado.cycloidepg.controller.ChannelController;
import pt.amado.cycloidepg.dto.ChannelDTO;
import pt.amado.cycloidepg.exception.ChannelNotFoundException;
import pt.amado.cycloidepg.model.Channel;
import pt.amado.cycloidepg.repository.ChannelRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class ChannelService {

    private ChannelRepository channelRepository;
    private ModelMapper mapper = new ModelMapper();

    @Autowired
    public ChannelService(ChannelRepository channelRepository){
        this.channelRepository = channelRepository;
    }
    public List<ChannelDTO> findAllChannels() {
        return channelRepository.findAll().stream()
                .map(e -> mapper.map(e, ChannelDTO.class))
                .collect(Collectors.toList());
    }

    public ChannelDTO saveChannel(ChannelDTO channelDTO) {
        Channel savedChannel = channelRepository.save(mapper.map(channelDTO, Channel.class));
        return mapper.map(savedChannel, ChannelDTO.class);
    }

    public Channel findById(long channelId) throws ChannelNotFoundException {
        return channelRepository.findById(channelId)
                .orElseThrow(() -> new ChannelNotFoundException(channelId));
    }
}
