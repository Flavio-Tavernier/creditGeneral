package cgb.classesMetier.log;

import org.springframework.data.jpa.repository.JpaRepository;

import cgb.classesMetier.transfert.Transfer;

public interface LogRepository extends JpaRepository<Log, Long> {

}
