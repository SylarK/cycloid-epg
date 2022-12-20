package pt.amado.cycloidepg.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pt.amado.cycloidepg.dto.ChannelDTO;
import pt.amado.cycloidepg.dto.ProgramDTO;
import pt.amado.cycloidepg.exception.ChannelNotFoundException;
import pt.amado.cycloidepg.exception.CycloidGeneralException;
import pt.amado.cycloidepg.exception.ProgramNotFoundException;
import pt.amado.cycloidepg.model.Channel;
import pt.amado.cycloidepg.model.Program;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class ProgramServiceTest {

    @Autowired
    private ProgramService programService;

    @Autowired
    private ChannelService channelService;
    private ModelMapper mapper = new ModelMapper();

    private void saveExampleChannel() {
        channelService.saveChannel(
                ChannelDTO.builder()
                        .name("Sky Unit Test")
                        .position(1)
                        .category("Sports")
                        .build()
        );
    }

    @Test
    public void shouldSaveANewProgram() throws CycloidGeneralException {

        saveExampleChannel();

        ProgramDTO programDTO = ProgramDTO.builder()
                .channelId(1)
                .imageUrl("https://cdn.mos.cms.futurecdn.net/tEzeTYPgYPvEeLznZNwR9o.jpg")
                .title("Cycloid Unit Tests")
                .description("Consulting, Architecture, Design and Development. Maintenance and evolution at your own pace.")
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now())
                .build();

        programService.saveProgram(programDTO);
        Program program = mapper.map(programService.findById(1), Program.class);

        Assertions.assertArrayEquals(
                new String[]{
                        String.valueOf(programDTO.getChannelId()),
                        programDTO.getImageUrl(),
                        programDTO.getDescription(),
                        programDTO.getTitle()
                },
                new String[]{
                        String.valueOf(program.getChannel().getId()),
                        program.getImageUrl(),
                        program.getDescription(),
                        program.getTitle()
                }
        );
    }

    @Test
    public void shouldThrowExceptionIfChannelDoestNotExists(){
        ProgramDTO programDTO = ProgramDTO.builder()
                .channelId(10)
                .imageUrl("https://cdn.mos.cms.futurecdn.net/tEzeTYPgYPvEeLznZNwR9o.jpg")
                .title("Cycloid Unit Tests")
                .description("Consulting, Architecture, Design and Development. Maintenance and evolution at your own pace.")
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now())
                .build();

        Exception exception = assertThrows(ChannelNotFoundException.class, () -> {
            programService.saveProgram(programDTO);
        });

        String expectedMessage = "There is no channel registered with id 10";

        Assertions.assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionIfProgramDoestNotExists(){

        Exception exception = assertThrows(ProgramNotFoundException.class, () -> {
            programService.findById(10);
        });

        String expectedMessage = "There is no program registered with id 10";

        Assertions.assertEquals(expectedMessage, exception.getMessage());
    }
}