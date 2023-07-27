package com.molloi.ssyx.home.service;

import java.util.Map;

/**
 * @author Molloi
 * @date 2023/7/20 21:10
 */
public interface ItemService {
    Map<String, Object> item(Long skuId, Long userId);
}
