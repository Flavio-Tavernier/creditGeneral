package cgb.classesMetier.transfer.lot;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class TransfersLotService {
    @Autowired
    private TransfersLotRepository transfersLotRepository;

    public TransfersLot getLotByRefLot(String refLot)
    {
        return this.transfersLotRepository.getLotByRefLot(refLot);
    }
}
