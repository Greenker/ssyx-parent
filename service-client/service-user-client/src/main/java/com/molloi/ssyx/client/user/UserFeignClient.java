package com.molloi.ssyx.client.user;

import com.molloi.ssyx.vo.user.LeaderAddressVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Molloi
 * @date 2023/6/23 21:54
 */
@FeignClient("service-user")
public interface UserFeignClient {
    // 提货点地址信息
    @GetMapping("/api/user/leader/inner/getUserAddressByUserId/{userId}")
    LeaderAddressVo getUserAddressByUserId(@PathVariable(value = "userId") Long userId);
}
