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

package svc

import (
	"context"
	"github.com/apache/dubbo-go/config"
	"github.com/apache/dubbo-samples/golang/seata/filter"

	"errors"
)

import (
	"github.com/apache/dubbo-samples/golang/seata/order-svc/app/dao"
	dao2 "github.com/apache/dubbo-samples/golang/seata/product-svc/app/dao"
)

import (
	context2 "github.com/dk-lockdown/seata-golang/client/context"
	"github.com/dk-lockdown/seata-golang/client/tm"
)

type OrderSvc struct {
	CreateSo func(ctx context.Context, reqs []*dao.SoMaster, rsp *dao.CreateSoResult) error
}

type ProductSvc struct {
	AllocateInventory func(ctx context.Context, reqs []*dao2.AllocateInventoryReq, rsp *dao2.AllocateInventoryResult) error
}

func (svc *OrderSvc) Reference() string {
	return "OrderSvc"
}

func (svc *ProductSvc) Reference() string {
	return "ProductSvc"
}

var (
	orderSvc   = new(OrderSvc)
	productSvc = new(ProductSvc)
)

func init() {
	config.SetConsumerService(orderSvc)
	config.SetConsumerService(productSvc)
}

type Svc struct {
}

func (svc *Svc) CreateSo(ctx context.Context, rollback bool) ([]uint64, error) {
	rootContext := ctx.(*context2.RootContext)
	soMasters := []*dao.SoMaster{
		{
			BuyerUserSysNo:       10001,
			SellerCompanyCode:    "SC001",
			ReceiveDivisionSysNo: 110105,
			ReceiveAddress:       "朝阳区长安街001号",
			ReceiveZip:           "000001",
			ReceiveContact:       "斯密达",
			ReceiveContactPhone:  "18728828296",
			StockSysNo:           1,
			PaymentType:          1,
			SoAmt:                430.5,
			Status:               10,
			AppId:                "dk-order",
			SoItems: []*dao.SoItem{
				{
					ProductSysNo:  1,
					ProductName:   "刺力王",
					CostPrice:     200,
					OriginalPrice: 232,
					DealPrice:     215.25,
					Quantity:      2,
				},
			},
		},
	}

	reqs := []*dao2.AllocateInventoryReq{{
		ProductSysNo: 1,
		Qty:          2,
	}}

	var createSoResult = &dao.CreateSoResult{}
	var allocateInventoryResult = &dao2.AllocateInventoryResult{}

	// 通过 attachment 传递 xid
	err1 := orderSvc.CreateSo(context.WithValue(ctx, "attachment", map[string]string{
		filter.SEATA_XID: rootContext.GetXID(),
	}), soMasters, createSoResult)
	if err1 != nil {
		return nil, err1
	}

	// 通过 attachment 传递 xid
	err2 := productSvc.AllocateInventory(context.WithValue(ctx, "attachment", map[string]string{
		filter.SEATA_XID: rootContext.GetXID(),
	}), reqs, allocateInventoryResult)
	if err2 != nil {
		return nil, err2
	}

	if rollback {
		return nil, errors.New("there is a error")
	}
	return createSoResult.SoSysNos, nil
}

var service = &Svc{}

type ProxyService struct {
	*Svc
	CreateSo func(ctx context.Context, rollback bool) ([]uint64, error)
}

var methodTransactionInfo = make(map[string]*tm.TransactionInfo)

func init() {
	methodTransactionInfo["CreateSo"] = &tm.TransactionInfo{
		TimeOut:     60000000,
		Name:        "CreateSo",
		Propagation: tm.REQUIRED,
	}
}

func (svc *ProxyService) GetProxyService() interface{} {
	return svc.Svc
}

func (svc *ProxyService) GetMethodTransactionInfo(methodName string) *tm.TransactionInfo {
	return methodTransactionInfo[methodName]
}

var ProxySvc = &ProxyService{
	Svc: service,
}
