package cgb.classesMetier.log;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cgb.classesMetier.transfer.Transfer;

public interface LogRepository extends JpaRepository<Log, Long> {
	public List<Log> getLogsByNumLot(Long numLot);
}
