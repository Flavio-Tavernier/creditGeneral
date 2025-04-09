package cgb.classesMetier.transfer;

import java.time.LocalDate;
import java.util.List;

public record TransfersLot(String sourceAccountNumber,
								List<UnTransferDuLot> lesTransfers,
								LocalDate transferDate,
								String description) {}
