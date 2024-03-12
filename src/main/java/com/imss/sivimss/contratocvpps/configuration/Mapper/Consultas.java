package com.imss.sivimss.contratocvpps.configuration.Mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Repository
public interface Consultas {
	static class PureSqlProvider {
		public String sql(String sql) {
			return sql;
		}

		public String count(String from) {
			return "SELECT count(*) FROM " + from;
		}
	}

	public class PureSqlProviderPage {

		public String sqlWithPagination(String sql, Pageable pageable) {
			StringBuilder paginatedSql = new StringBuilder(sql);

			// Agregar cláusula de ordenación si se proporciona
			Sort sort = pageable.getSort();
			if (sort != null && sort.isSorted()) {
				paginatedSql.append(" ORDER BY ");
				boolean first = true;
				for (Sort.Order order : sort) {
					if (!first) {
						paginatedSql.append(", ");
					}
					paginatedSql.append(order.getProperty()).append(" ").append(order.getDirection());
					first = false;
				}
			}

			// Agregar cláusula de paginación
			paginatedSql.append(" LIMIT ").append(pageable.getPageSize())
					.append(" OFFSET ").append(pageable.getOffset());

			return paginatedSql.toString();
		}

		public String countPages(String from, Pageable pageable) {
			int pageSize = pageable.getPageSize();
			return "SELECT CAST(CEIL(COUNT(*) / " + pageSize + ") AS INT) " +
					"FROM ( " + from + " ) sc;";
		}

		public String count(String from) {
			return "SELECT count(*) FROM (" + from + " ) sc;";
		}
	}

	@SelectProvider(type = PureSqlProvider.class, method = "sql")
	public List<Map<String, Object>> selectNativeQuery(String sql);

	@SelectProvider(type = PureSqlProviderPage.class, method = "sqlWithPagination")
	public List<Map<String, Object>> selectNativeQueryPag(String sql, Pageable pageable);

	@SelectProvider(type = PureSqlProviderPage.class, method = "count")
	public Integer selectNativeQueryPagCuentaPaginas(String from, Pageable pageable);

}