package com.imss.sivimss.contratocvpps.util;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.imss.sivimss.contratocvpps.configuration.MyBatisConfig;
import com.imss.sivimss.contratocvpps.configuration.Mapper.Consultas;
import com.imss.sivimss.contratocvpps.service.NuevoPlanSFPAService;

@Component
public class Paginator {
	private static final Logger log = LoggerFactory.getLogger(NuevoPlanSFPAService.class);

	@Autowired
	private MyBatisConfig myBatisConfig;

	public ResponseEntity<Object> paginarConsulta(String consultaSQL, int pagina, int elementos, String columna,
			String ord) {
		SqlSessionFactory sqlSessionFactory = myBatisConfig.buildqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			Consultas consultas = session.getMapper(Consultas.class);

			try {
				Pageable pageable = null;

				switch (ord) {
					case "desc":
						pageable = PageRequest.of(pagina, elementos, Sort.by((columna)).ascending());
						break;

					case "asc":
						pageable = PageRequest.of(pagina, elementos, Sort.by((columna)).descending());
						break;
					default:
						pageable = PageRequest.of(pagina, elementos, Sort.by((columna)).ascending());
						break;
				}

				List<Map<String, Object>> resp = consultas.selectNativeQueryPag(consultaSQL, pageable);
				Integer paginasContadas = consultas.selectNativeQueryPagCuentaPaginas(consultaSQL, pageable);

				Page<Map<String, Object>> objetoMapeado = new PageImpl<>(resp, pageable, paginasContadas);

				session.commit();
				return ResponseEntity.ok(objetoMapeado);

			} catch (Exception e) {
				session.rollback();
				log.error(e.getMessage(), e);
				log.info("==> rollback() ");
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
			}
		}
	}
}
