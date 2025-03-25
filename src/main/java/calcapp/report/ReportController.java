package calcapp.report;

import calcapp.report.dto.CalorieReportDto;
import calcapp.report.dto.DailyReportDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/report/user")
public class ReportController {

	private final ReportService reportService;

	@Autowired
	public ReportController(ReportService reportService) {
		this.reportService = reportService;
	}

	@GetMapping("/{userId}/daily")
	public DailyReportDto getDailyReport(@PathVariable("userId") Long userId) {
		return reportService.getDailyReport(userId);
	}

	@GetMapping("/{userId}/calories")
	public CalorieReportDto getCalorieReport(@PathVariable("userId") Long userId) {
		return reportService.getCalorieReport(userId);
	}

	@GetMapping("/{userId}/history")
	public List<DailyReportDto> getHistory(@PathVariable("userId") Long userId) {
		return reportService.getHistory(userId);
	}
}
