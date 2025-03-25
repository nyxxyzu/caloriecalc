package calcapp.report;

import calcapp.report.dto.CalorieReportDto;
import calcapp.report.dto.DailyReportDto;

import java.util.List;


public interface ReportService {
	DailyReportDto getDailyReport(Long userId);

	CalorieReportDto getCalorieReport(Long userId);

	List<DailyReportDto> getHistory(Long userId);
}
