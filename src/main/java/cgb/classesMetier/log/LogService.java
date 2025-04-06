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
	
	public long getLastLogNumber()
	{
		return logRepository.count();
	}
	
	/*public List<Log> getLogsById(Long numLot)
	{
		return logRepository.findAllById(numLot);
	}*/

	public Log addLog(Log log) 
	{
		return logRepository.save(log);
	}
}
