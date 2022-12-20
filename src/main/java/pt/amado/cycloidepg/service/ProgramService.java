package pt.amado.cycloidepg.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.amado.cycloidepg.dto.ChannelDTO;
import pt.amado.cycloidepg.dto.ProgramDTO;
import pt.amado.cycloidepg.exception.ChannelNotFoundException;
import pt.amado.cycloidepg.exception.CycloidGeneralException;
import pt.amado.cycloidepg.exception.ProgramNotFoundException;
import pt.amado.cycloidepg.model.Channel;
import pt.amado.cycloidepg.model.Program;
import pt.amado.cycloidepg.repository.ProgramRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProgramService {

    private ProgramRepository programRepository;
    private ChannelService channelService;
    private ModelMapper mapper = new ModelMapper();

    @Autowired
    public ProgramService(ProgramRepository programRepository,
                          ChannelService channelService){
        this.programRepository = programRepository;
        this.channelService = channelService;
    }
    public ProgramDTO saveProgram(ProgramDTO programDTO) throws ChannelNotFoundException {

        Channel channel = channelService.findById(programDTO.getChannelId());
        Program program = mapper.map(programDTO, Program.class);
        program.setChannel(channel);

        return mapper.map(programRepository.save(program), ProgramDTO.class);
    }

    public ProgramDTO findById(long programId) throws ProgramNotFoundException {
        return  mapper.map(
                    programRepository.findById(programId)
                            .orElseThrow(() -> new ProgramNotFoundException(programId))
                , ProgramDTO.class );
    }

    public void deleteProgramById(long programId) throws ProgramNotFoundException {
        checkIfProgramExists(programId);
        programRepository.deleteById(programId);
    }

    public ProgramDTO updateProgram(long id, ProgramDTO programDTO) throws CycloidGeneralException {
        checkIfProgramExists(id);
        programDTO.setId(id);
        return saveProgram(programDTO);
    }

    private void checkIfProgramExists(long programId) throws ProgramNotFoundException {
        if(!programRepository.existsById(programId))
            throw new ProgramNotFoundException(programId);
    }

    public List<ProgramDTO> findProgramsByChannel(long id) throws ChannelNotFoundException {
        Channel channel = channelService.findById(id);
        return programRepository.findAllByChannel(channel)
                .stream()
                .map(e -> mapper.map(e, ProgramDTO.class))
                .collect(Collectors.toList());
    }

    public List<ProgramDTO> findAllPrograms() {
        return programRepository.findAll()
                .stream()
                .map(e -> mapper.map(e, ProgramDTO.class))
                .collect(Collectors.toList());
    }
}
