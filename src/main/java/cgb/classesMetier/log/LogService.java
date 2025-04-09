package cgb.classesMetier.log;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogService {
	
	@Autowired
    private LogRepository logRepository;
	
	public List<Log> getAllLogs()
	{
		return logRepository.findAll();
	}

	
	public List<Log> getLogsByNumLot(Long numLot)
	{
		return logRepository.getLogsByNumLot(numLot);
	}

	public Log addLog(Log log) 
	{
		return logRepository.save(log);
	}
}
