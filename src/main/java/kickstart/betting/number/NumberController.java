package kickstart.betting.number;

import kickstart.betting.number.param.BuyRequest;
import kickstart.other.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("number")
public class NumberController {


	@Resource
	private NumberRepository numberRepository;
	private Integer[] systemNumList = {1, 2, 3, 4, 5, 6};
	private int systemSuperzahl = 5;

	// /number/buy
	@RequestMapping("buy")
	public Result buy(@RequestBody BuyRequest request) {
		Result result = new Result();
		int count = 0;
		List<Integer> list = Arrays.asList(systemNumList);
		StringBuilder stringBuilder = new StringBuilder();
		for (Integer number : request.getNumberList()) {
			if (list.contains(number)) {
				count++;
			}
			stringBuilder.append(number).append(",");
		}
		String numberListStr = stringBuilder.toString();
		numberListStr = numberListStr.substring(0, numberListStr.length() - 1);
		int money = compute(count, request.getSuperzahl());
		NumberEntry numberEntry = new NumberEntry();
		numberEntry.setNumberListStr(numberListStr);
		numberEntry.setSuperzahl(request.getSuperzahl());
		numberEntry.setMenge(request.getMenge());
		numberEntry.setResult(money);
		numberEntry.setUserId(1L);
		numberRepository.save(numberEntry);
		return result;
	}

	@RequestMapping("findNumberRecordList")
	public Result findNumberRecordList() {
		Iterable<NumberEntry> iterable = numberRepository.findAll();
		Iterator<NumberEntry> iterator = iterable.iterator();
		List<NumberEntry> list = new ArrayList<>();
		while (iterator.hasNext()) {
			list.add(iterator.next());
		}
		Result result = new Result();
		result.setData(list);
		return result;
	}

	public int compute(int count, int superzahl) {
		if (count == 6 && superzahl == systemSuperzahl) {
			return 100000;
		} else if (count == 6 && superzahl != systemSuperzahl) {
			return 90000;
		} else if (count == 5 && superzahl == systemSuperzahl) {
			return 80000;
		} else if (count == 5 && superzahl != systemSuperzahl) {
			return 70000;
		} else if (count == 4 && superzahl == systemSuperzahl) {
			return 50000;
		} else if (count == 4 && superzahl != systemSuperzahl) {
			return 20000;
		} else if (count == 3 && superzahl == systemSuperzahl) {
			return 10000;
		} else if (count == 3 && superzahl != systemSuperzahl) {
			return 10000;
		} else if (count == 2 && superzahl == systemSuperzahl) {
			return 60;
		}
		return 0;
	}

}
