package com.alibaba.dubbo.samples.merge.impl;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.dubbo.samples.merge.api.MergeService;

/**
 * MenuServiceImpl
 */
public class MergeServiceImpl2 implements MergeService {

    public List<String> mergeResult() {
        List<String> menus = new ArrayList<String>();
        menus.add("group-2.1");
        menus.add("group-2.2");
        return menus;
    }

}
