package kickstart.football;

import org.salespointframework.catalog.Catalog;
import org.springframework.data.domain.Sort;

public interface BettingCatalog extends Catalog<Data> {

	Sort DEFAULT_SORT = Sort.by("productIdentifier").descending();

	Iterable<Data> findByType(Category category, Sort sort);

	default Iterable<Data> findByType(Category category) {
		return findByType(category, DEFAULT_SORT);
	}
}
