/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package main

import (
	"context"
)

import (
	"github.com/apache/dubbo-samples/golang/seata/filter"
	dao2 "github.com/apache/dubbo-samples/golang/seata/product-svc/app/dao"
)

import (
	context2 "github.com/dk-lockdown/seata-golang/client/context"
)

type ProductSvc struct {
	dao *dao2.Dao
}

func (svc *ProductSvc) AllocateInventory(ctx context.Context, reqs []*dao2.AllocateInventoryReq) (*dao2.AllocateInventoryResult, error) {
	val := ctx.Value(filter.SEATA_XID)
	xid := val.(string)

	rootContext := &context2.RootContext{Context: ctx}
	rootContext.Bind(xid)

	err := svc.dao.AllocateInventory(rootContext, reqs)
	if err == nil {
		return &dao2.AllocateInventoryResult{true}, nil
	}
	return &dao2.AllocateInventoryResult{false}, err
}

func (svc *ProductSvc) Reference() string {
	return "ProductSvc"
}
