package com.yt.demo.core.dao;


import com.yt.demo.core.entity.SPUEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 
 * @author joeyzheng
 * @date 2017年2月12日
 */
public interface SPUDao extends JpaRepository<SPUEntity, Long> {

	SPUEntity findTop1ByCode(String code);

    SPUEntity findTop1ByBrandIdAndModelIdAndName(Long brandId, Long modelId, String name);
	
	@Query("from SPUEntity where code in ?1 order by lastModifiedDate desc")
	List<SPUEntity> findByCodeIn(List<String> codes);

    List<SPUEntity> findTop60ByBrandId(Long brandId);
    
    List<SPUEntity> findTop60ByBrandIdAndDeleted(Long brandId, Boolean deleted);

    List<SPUEntity> findTop60ByModelId(Long modelId);
    
    List<SPUEntity> findTop60ByModelIdAndDeleted(Long modelId, Boolean deleted);

    List<SPUEntity> findByMerchantNumberIn(List<String> merchantNumbers);
}
