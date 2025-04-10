package cgb.classesMetier.transfer;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferRepository extends JpaRepository<Transfer, Long> {
	public List<Transfer> getTransfersByNumLotAndStatut(long numLot, String statut);
	public List<Transfer> getTransfersByTransferDateBetween(LocalDate dateDebut, LocalDate dateFin);
	public List<Transfer> getTransferByDestinationAccountNumber(String destinationAccountNumber);
}