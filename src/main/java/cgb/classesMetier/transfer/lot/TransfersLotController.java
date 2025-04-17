package cgb.classesMetier.transfer.lot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("transfers-lot")
public class TransfersLotController {

    @Autowired
    private TransfersLotService transfersLotService;

    @GetMapping("/{refLot}")
    public ResponseEntity<TransfersLot> getTransfersLotByRefLot(@PathVariable String refLot) {
        TransfersLot transfersLot = transfersLotService.getLotByRefLot(refLot);
        return ResponseEntity.ok(transfersLot);
    }
}
