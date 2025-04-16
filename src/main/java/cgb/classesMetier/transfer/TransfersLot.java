package cgb.classesMetier.transfer;

import java.util.List;

public record TransfersLot(String refLot,
							String sourceAccountNumber,
							String sourceEmail,
							List<UnTransferDuLot> lesTransfers,
							String description) {}
