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
	"github.com/apache/dubbo-go/config"
)

import (
	hessian "github.com/apache/dubbo-go-hessian2"
)

var (
	orderProvider = new(OrderProvider)
)

func init() {
	config.SetProviderService(orderProvider)
	hessian.RegisterPOJO(&Order{})
}

type Order struct {
	Id      string
	Name    string
	Price   int
	Count   int
	Product Product
}

func (Order) JavaClassName() string {
	return "com.ikurento.order.Order"
}

type OrderProvider struct{}

func (o *OrderProvider) GetOrder(ctx context.Context, req []interface{}) (*Order, error) {
	println("req:%#v", req)

	product := &Product{}
	err := productProvider.GetProduct(context.TODO(), []interface{}{"A001"}, product)
	if err != nil {
		panic(err)
	}
	println("response from product result: %v\n", product)

	rsp := Order{
		"A001", "A001", 200, 2, *product}

	println("rsp:%#v", rsp)
	return &rsp, nil
}

func (o *OrderProvider) Reference() string {
	return "OrderProvider"
}
