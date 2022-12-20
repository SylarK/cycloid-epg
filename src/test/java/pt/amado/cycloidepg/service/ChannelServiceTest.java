package pt.amado.cycloidepg.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pt.amado.cycloidepg.dto.ChannelDTO;
import pt.amado.cycloidepg.exception.ChannelNotFoundException;
import pt.amado.cycloidepg.model.Channel;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class ChannelServiceTest {

    @Autowired
    private ChannelService channelService;

    @Test
    public void shouldSaveNewChannel() throws ChannelNotFoundException {

        channelService.saveChannel(
                ChannelDTO.builder()
                        .name("Sky Unit Test")
                        .position(1)
                        .category("Sports")
                        .build()
        );

        Channel channel = channelService.findById(1);
        Assertions.assertEquals(Channel.class, channel.getClass());
    }

    @Test
    public void shouldThrowChannelNotFoundExceptionWhenSearchAChannelDoesNotExists(){

        long channelId = 10;

        Exception exception = assertThrows(ChannelNotFoundException.class, () -> {
            Channel channel = channelService.findById(channelId);
        });

        String expectedMessage = String.format(
                "There is no channel registered with id %s",
                channelId
        );

        Assertions.assertEquals(expectedMessage, exception.getMessage());
    }
}