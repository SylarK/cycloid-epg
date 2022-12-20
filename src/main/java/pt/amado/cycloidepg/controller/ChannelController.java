package pt.amado.cycloidepg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.amado.cycloidepg.dto.ChannelDTO;
import pt.amado.cycloidepg.exception.ChannelNotFoundException;
import pt.amado.cycloidepg.model.Channel;
import pt.amado.cycloidepg.service.ChannelService;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/channels")
public class ChannelController {

    private ChannelService channelService;

    @Autowired
    public ChannelController(ChannelService channelService){
        this.channelService = channelService;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<ChannelDTO>>> findAllChannels(){

        List<EntityModel<ChannelDTO>> channels = channelService.findAllChannels().stream()
                .map(e -> {
                    try {
                        return EntityModel.of(e,
                                linkTo(methodOn(ChannelController.class).findById(e.getId())).withSelfRel(),
                                linkTo(methodOn(ChannelController.class).findAllChannels()).withRel("channels")
                        );
                    } catch (ChannelNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }).collect(Collectors.toList());

        return ResponseEntity.ok(
                CollectionModel.of(channels,linkTo(methodOn(ChannelController.class)).withSelfRel())
        );
    }

    @PostMapping("/create")
    public ResponseEntity<EntityModel<ChannelDTO>> createChannel(@RequestBody ChannelDTO channelDTO){
        return ResponseEntity.ok(
                EntityModel.of(channelService.saveChannel(channelDTO),
                        linkTo(methodOn(ChannelController.class).findAllChannels()).withRel("channels")
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Channel>> findById(@PathVariable long id) throws ChannelNotFoundException {
        return ResponseEntity.ok(
                EntityModel.of(channelService.findById(id),
                        linkTo(methodOn(ChannelController.class).findById(id)).withSelfRel(),
                        linkTo(methodOn(ChannelController.class).findAllChannels()).withRel("channels")
                )
        );
    }

}
