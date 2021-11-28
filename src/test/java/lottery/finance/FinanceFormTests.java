package lottery.finance;
import lottery.finance.FinanceForm;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FinanceFormTests {

	@Test
	void testAmount() {
		FinanceForm form = new FinanceForm(100.0, "EUR");
		assertEquals(form.getAmount(), 100.0);
	}

	@Test
	void testNote() {
		FinanceForm form = new FinanceForm(100.0, "EUR");
		assertEquals(form.getNote(), "EUR");
	}
}
