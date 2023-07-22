package com.molloi.ssyx.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.molloi.ssyx.model.product.SkuAttrValue;
import com.molloi.ssyx.model.product.SkuImage;
import com.molloi.ssyx.model.product.SkuInfo;
import com.molloi.ssyx.model.product.SkuPoster;
import com.molloi.ssyx.mq.constant.MqConst;
import com.molloi.ssyx.mq.service.RabbitService;
import com.molloi.ssyx.product.mapper.SkuInfoMapper;
import com.molloi.ssyx.product.service.SkuAttrValueService;
import com.molloi.ssyx.product.service.SkuImageService;
import com.molloi.ssyx.product.service.SkuInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.molloi.ssyx.product.service.SkuPosterService;
import com.molloi.ssyx.vo.product.SkuInfoQueryVo;
import com.molloi.ssyx.vo.product.SkuInfoVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * sku信息 服务实现类
 * </p>
 *
 * @author molloi
 * @since 2023-06-13
 */
@Service
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoMapper, SkuInfo> implements SkuInfoService {

    @Resource
    private SkuImageService skuImageService;

    @Resource
    private SkuAttrValueService skuAttrValueService;

    @Resource
    private SkuPosterService skuPosterService;

    @Resource
    private RabbitService rabbitService;

    @Override
    public IPage<SkuInfo> selectPage(Page<SkuInfo> param, SkuInfoQueryVo skuInfoQueryVo) {
        Long categoryId = skuInfoQueryVo.getCategoryId();
        String keyword = skuInfoQueryVo.getKeyword();
        String skuType = skuInfoQueryVo.getSkuType();
        LambdaQueryWrapper<SkuInfo> queryWrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty(keyword)) {
            queryWrapper.like(SkuInfo::getSkuName, keyword);
        }
        if (!StringUtils.isEmpty(categoryId)) {
            queryWrapper.eq(SkuInfo::getCategoryId, categoryId);
        }
        if (!StringUtils.isEmpty(skuType)) {
            queryWrapper.like(SkuInfo::getSkuType, skuType);
        }
        return baseMapper.selectPage(param, queryWrapper);
    }

    @Override
    public void saveSkuInfo(SkuInfoVo skuInfoVo) {

        // 添加sku基本信息
        SkuInfo skuInfo = new SkuInfo();
        BeanUtils.copyProperties(skuInfoVo,skuInfo);
        baseMapper.insert(skuInfo);

        // 添加图片信息
        List<SkuImage> skuImagesList = skuInfoVo.getSkuImagesList();
        if (!CollectionUtils.isEmpty(skuImagesList)) {
            for(SkuImage skuImage : skuImagesList) {
                skuImage.setSkuId(skuInfo.getId());
            }
            skuImageService.saveBatch(skuImagesList);
        }

        // 添加海报信息
        List<SkuPoster> skuPosterList = skuInfoVo.getSkuPosterList();
        if (!CollectionUtils.isEmpty(skuPosterList)) {
            for(SkuPoster skuPoster : skuPosterList) {
                skuPoster.setSkuId(skuInfo.getId());
            }
            skuPosterService.saveBatch(skuPosterList);
        }

        // 添加sku平台属性
        List<SkuAttrValue> skuAttrValueList = skuInfoVo.getSkuAttrValueList();
        if (!CollectionUtils.isEmpty(skuAttrValueList)) {
            for(SkuAttrValue skuAttrValue : skuAttrValueList) {
                skuAttrValue.setSkuId(skuInfo.getId());
            }
            skuAttrValueService.saveBatch(skuAttrValueList);
        }

    }

    // sku -> 1. 商品基本信息 2. 商品图片信息 3. 商品海报信息 4. 商品属性信息
    @Override
    public SkuInfoVo getSkuInfo(Long id) {
        SkuInfoVo skuInfoVo = new SkuInfoVo();
        SkuInfo skuInfo = baseMapper.selectById(id);
        List<SkuImage> skuImageList = skuImageService.getImageListBySkuId(id);
        List<SkuPoster> skuPosterList = skuPosterService.getPosterListBySkuId(id);
        List<SkuAttrValue> skuAttrValueList = skuAttrValueService.getAttrValueListSkuById(id);
        BeanUtils.copyProperties(skuInfo, skuInfoVo);
        skuInfoVo.setSkuImagesList(skuImageList);
        skuInfoVo.setSkuPosterList(skuPosterList);
        skuInfoVo.setSkuAttrValueList(skuAttrValueList);
        return skuInfoVo;
    }

    @Override
    public void updateSkuInfo(SkuInfoVo skuInfoVo) {
        SkuInfo skuInfo = new SkuInfo();
        BeanUtils.copyProperties(skuInfoVo, skuInfo);
        baseMapper.updateById(skuInfo);
        Long skuId = skuInfoVo.getId();
        skuImageService.remove(new LambdaQueryWrapper<SkuImage>().eq(SkuImage::getSkuId, skuId));
        List<SkuImage> skuImagesList = skuInfoVo.getSkuImagesList();
        if (!CollectionUtils.isEmpty(skuImagesList)) {
            for(SkuImage skuImage : skuImagesList) {
                skuImage.setSkuId(skuId);
            }
            skuImageService.saveBatch(skuImagesList);
        }
        skuPosterService.remove(new LambdaQueryWrapper<SkuPoster>().eq(SkuPoster::getSkuId, skuId));
        List<SkuPoster> skuPosterList = skuInfoVo.getSkuPosterList();
        if (!CollectionUtils.isEmpty(skuPosterList)) {
            for(SkuPoster skuPoster : skuPosterList) {
                skuPoster.setSkuId(skuId);
            }
            skuPosterService.saveBatch(skuPosterList);
        }
        skuAttrValueService.remove(new LambdaQueryWrapper<SkuAttrValue>().eq(SkuAttrValue::getSkuId, skuId));
        List<SkuAttrValue> skuAttrValueList = skuInfoVo.getSkuAttrValueList();
        if (!CollectionUtils.isEmpty(skuAttrValueList)) {
            for(SkuAttrValue skuAttrValue : skuAttrValueList) {
                skuAttrValue.setSkuId(skuId);
            }
            skuAttrValueService.saveBatch(skuAttrValueList);
        }
    }

    @Override
    public void check(Long skuId, Integer status) {
        SkuInfo skuInfo = baseMapper.selectById(skuId);
        skuInfo.setCheckStatus(status);
        baseMapper.updateById(skuInfo);
    }

    @Override
    public void publish(Long skuId, Integer status) {
        if (status == 1) { // 上架
            SkuInfo skuInfo = baseMapper.selectById(skuId);
            skuInfo.setPublishStatus(status);
            baseMapper.updateById(skuInfo);
            // TODO 整合MQ把数据同步到ES里面
            rabbitService.sendMessage(MqConst.EXCHANGE_GOODS_DIRECT, MqConst.ROUTING_GOODS_UPPER, skuId);
        } else { // 下架
            SkuInfo skuInfo = baseMapper.selectById(skuId);
            skuInfo.setPublishStatus(status);
            baseMapper.updateById(skuInfo);
            // TODO 整合MQ把数据同步到ES里面
            rabbitService.sendMessage(MqConst.EXCHANGE_GOODS_DIRECT, MqConst.ROUTING_GOODS_LOWER, skuId);
        }
    }

    @Override
    public void isNewPerson(Long skuId, Integer status) {
        SkuInfo skuInfoUp = new SkuInfo();
        skuInfoUp.setId(skuId);
        skuInfoUp.setIsNewPerson(status);
        baseMapper.updateById(skuInfoUp);
    }

    @Override
    public List<SkuInfo> findSkuInfoList(List<Long> skuIdList) {
        return baseMapper.selectBatchIds(skuIdList);
    }

    @Override
    public List<SkuInfo> findSkuInfoByKeyword(String keyword) {
        return baseMapper.selectList(new LambdaQueryWrapper<SkuInfo>().like(SkuInfo::getSkuName, keyword));
    }

    @Override
    public List<SkuInfo> findNewPersonSkuInfoList() {
        LambdaQueryWrapper<SkuInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SkuInfo::getIsNewPerson,1).eq(SkuInfo::getPublishStatus,1)
                .orderByDesc(SkuInfo::getStock);
        Page<SkuInfo> param = new Page<>(1,3);
        return baseMapper.selectPage(param, queryWrapper).getRecords();
    }

    @Override
    public SkuInfoVo getSkuInfoVo(Long skuId) {
        SkuInfoVo skuInfoVo = new SkuInfoVo();
        SkuInfo skuInfo = baseMapper.selectById(skuId);
        List<SkuImage> imageList = skuImageService.getImageListBySkuId(skuId);
        List<SkuPoster> posterList = skuPosterService.getPosterListBySkuId(skuId);
        List<SkuAttrValue> attrValueList = skuAttrValueService.getAttrValueListSkuById(skuId);

        BeanUtils.copyProperties(skuInfo, skuInfoVo);
        skuInfoVo.setSkuImagesList(imageList);
        skuInfoVo.setSkuPosterList(posterList);
        skuInfoVo.setSkuAttrValueList(attrValueList);

        return skuInfoVo;
    }

}
