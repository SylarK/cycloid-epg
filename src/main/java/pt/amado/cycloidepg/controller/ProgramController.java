package pt.amado.cycloidepg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.amado.cycloidepg.dto.ProgramDTO;
import pt.amado.cycloidepg.exception.CycloidGeneralException;
import pt.amado.cycloidepg.service.ProgramService;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/programs")
public class ProgramController {

    private ProgramService programService;

    @Autowired
    public ProgramController(ProgramService programService){
        this.programService = programService;
    }

    @PostMapping("/create")
    public ResponseEntity<EntityModel<ProgramDTO>> createProgram(@RequestBody ProgramDTO programDTO) throws CycloidGeneralException {
        ProgramDTO savedProgram = programService.saveProgram(programDTO);
        return ResponseEntity.ok(
                EntityModel.of(savedProgram,
                            linkTo(methodOn(ProgramController.class).deleteProgramById(savedProgram.getId())).withRel("delete program"),
                            linkTo(methodOn(ProgramController.class).findProgramsByChannel(savedProgram.getChannelId())).withRel("programs by channel"),
                            linkTo(methodOn(ProgramController.class).findAllPrograms()).withRel("programs")
                        )
        );
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<ProgramDTO>>> findAllPrograms() {
        List<EntityModel<ProgramDTO>> programs = programService.findAllPrograms().stream()
                .map(ProgramController::generateDefaultListProgramDTOWithHateoas)
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                CollectionModel.of(programs, linkTo(ProgramController.class).withSelfRel())
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ProgramDTO>> findProgramById(@PathVariable long id) throws CycloidGeneralException {
        return ResponseEntity.ok(
                EntityModel.of(programService.findById(id),
                        linkTo(methodOn(ProgramController.class).findProgramById(id)).withSelfRel(),
                        linkTo(methodOn(ProgramController.class).deleteProgramById(id)).withRel("delete program"),
                        linkTo(methodOn(ProgramController.class).findAllPrograms()).withRel("programs")
                )
        );
    }

    @GetMapping("/channel/{id}")
    public ResponseEntity<CollectionModel<EntityModel<ProgramDTO>>> findProgramsByChannel(@PathVariable long id) throws CycloidGeneralException {

        List<EntityModel<ProgramDTO>> programs = programService.findProgramsByChannel(id).stream()
                .map(ProgramController::generateDefaultListProgramDTOWithHateoas)
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                CollectionModel.of(programs, linkTo(ProgramController.class).withSelfRel())
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProgramById(@PathVariable long id) throws CycloidGeneralException {
        programService.deleteProgramById(id);
        return ResponseEntity.ok(String.format(
                "The program with id %s was successfully deleted.",
                id
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<ProgramDTO>> updateProgramById(@PathVariable long id,
                                                    @RequestBody ProgramDTO programDTO) throws CycloidGeneralException {
        return ResponseEntity.ok(
                EntityModel.of(programService.updateProgram(id, programDTO),
                        linkTo(methodOn(ProgramController.class).findProgramById(id)).withSelfRel(),
                        linkTo(methodOn(ProgramController.class).deleteProgramById(id)).withRel("delete program"),
                        linkTo(methodOn(ProgramController.class).findProgramsByChannel(id)).withRel("programs by channel"),
                        linkTo(methodOn(ProgramController.class).findAllPrograms()).withRel("programs")
                )
        );
    }

    private static EntityModel<ProgramDTO> generateDefaultListProgramDTOWithHateoas(ProgramDTO e) {
        try {
            return EntityModel.of(e,
                    linkTo(methodOn(ProgramController.class).findProgramById(e.getId())).withSelfRel(),
                    linkTo(methodOn(ProgramController.class).deleteProgramById(e.getId())).withRel("delete program"),
                    linkTo(methodOn(ProgramController.class).findProgramsByChannel(e.getChannelId())).withRel("programs by channel"),
                    linkTo(methodOn(ProgramController.class).findAllPrograms()).withRel("programs")
            );
        } catch (CycloidGeneralException ex) {
            throw new RuntimeException(ex);
        }
    }

}
