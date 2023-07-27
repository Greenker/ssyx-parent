package com.molloi.ssyx.search.service;

import com.molloi.ssyx.model.search.SkuEs;
import com.molloi.ssyx.vo.search.SkuEsQueryVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Molloi
 * @date 2023/6/17 15:48
 */
@Service
public interface SkuService {
    void upperSku(Long skuId);

    void lowerSku(Long skuId);

    List<SkuEs> findHotSkuList();

    Page<SkuEs> search(Pageable pageable, SkuEsQueryVo skuEsQueryVo);

    Boolean incrHotScore(Long skuId);
}
