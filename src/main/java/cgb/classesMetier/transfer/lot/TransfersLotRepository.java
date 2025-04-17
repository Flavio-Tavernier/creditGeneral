package cgb.classesMetier.transfer.lot;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TransfersLotRepository extends JpaRepository<TransfersLot, Long> {
    public TransfersLot getLotByRefLot(String reflot);
}
