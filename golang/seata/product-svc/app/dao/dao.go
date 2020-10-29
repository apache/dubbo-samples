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

package dao

import (
	hessian "github.com/apache/dubbo-go-hessian2"
	"github.com/dk-lockdown/seata-golang/client/at/exec"
	"github.com/dk-lockdown/seata-golang/client/context"
)

const (
	allocateInventorySql = `update seata_product.inventory set available_qty = available_qty - ?, 
		allocated_qty = allocated_qty + ? where product_sysno = ? and available_qty >= ?`
)

type Dao struct {
	*exec.DB
}

type AllocateInventoryReq struct {
	ProductSysNo int64
	Qty          int32
}

type AllocateInventoryResult struct {
	Result bool
}

func (req AllocateInventoryReq) JavaClassName() string {
	return "com.dubbogo.seata.AllocateInventoryReq"
}

func (result AllocateInventoryResult) JavaClassName() string {
	return "com.dubbogo.seata.AllocateInventoryResult"
}

func init() {
	// ------for hessian2------
	hessian.RegisterPOJO(&AllocateInventoryReq{})
	hessian.RegisterPOJO(&AllocateInventoryResult{})
}

func (dao *Dao) AllocateInventory(ctx *context.RootContext, reqs []*AllocateInventoryReq) error {
	tx, err := dao.Begin(ctx)
	if err != nil {
		return err
	}
	for _, req := range reqs {
		_, err := tx.Exec(allocateInventorySql, req.Qty, req.Qty, req.ProductSysNo, req.Qty)
		if err != nil {
			tx.Rollback()
			return err
		}
	}
	err = tx.Commit()
	if err != nil {
		return err
	}
	return nil
}
