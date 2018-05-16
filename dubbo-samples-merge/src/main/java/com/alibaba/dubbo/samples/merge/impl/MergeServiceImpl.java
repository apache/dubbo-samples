package com.alibaba.dubbo.samples.merge.impl;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.dubbo.samples.merge.api.MergeService;

/**
 * MenuServiceImpl
 */
public class MergeServiceImpl implements MergeService {

    public List<String> mergeResult() {
        List<String> menus = new ArrayList<String>();
        menus.add("group-1.1");
        menus.add("group-1.2");
        return menus;
    }

}
