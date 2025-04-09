package cgb.classesMetier.transfer.lot;

import java.time.LocalDate;
import java.util.List;

public record TestTransferLot(String sourceAccountNumber,
								List<TestTransferLotUnite> lesTransfers,
								LocalDate transferDate,
								String description) {}
