package org.apache.dubbo.samples.merge.impl;

import com.google.common.collect.Lists;
import org.apache.dubbo.samples.merge.api.IDubboService;

import java.util.List;

public class DubboServiceImpl implements IDubboService {
    @Override
    public List<String> hello() {
        return Lists.newArrayList("1");
    }
}
