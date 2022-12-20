package pt.amado.cycloidepg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pt.amado.cycloidepg.model.Channel;

@Repository
public interface ChannelRepository extends JpaRepository<Channel, Long> {
}
