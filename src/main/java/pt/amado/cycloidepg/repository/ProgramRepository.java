package pt.amado.cycloidepg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pt.amado.cycloidepg.model.Channel;
import pt.amado.cycloidepg.model.Program;

import java.util.List;

@Repository
public interface ProgramRepository extends JpaRepository<Program, Long> {

    List<Program> findAllByChannel(Channel channel);

}
