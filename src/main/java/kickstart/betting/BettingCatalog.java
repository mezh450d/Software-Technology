package kickstart.football;

import org.salespointframework.catalog.Catalog;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;

public interface BettingCatalog extends Catalog<FootballMatch> {

	Sort DEFAULT_SORT = Sort.by("productIdentifier").descending();

	Iterable<FootballMatch> findFootballMatchByDate(LocalDateTime date, Sort sort);

	Iterable<FootballMatch> findByType(Category category, Sort sort);

	default Iterable<FootballMatch> findByType(Category category) {
		return findByType(category, DEFAULT_SORT);
	}
}
