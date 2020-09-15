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

var orderProvider = new(OrderProvider)

func init() {
	config.SetConsumerService(orderProvider)
	hessian.RegisterPOJO(&Order{})
	hessian.RegisterPOJO(&Product{})
}

type Order struct {
	Id      string
	Name    string
	Price   int
	Product Product
}

func (u Order) JavaClassName() string {
	return "com.ikurento.order.Order"
}

type Product struct {
	Id       string
	Price    int
	Quantity int
}

func (p Product) JavaClassName() string {
	return "com.ikurento.product.Product"
}

type OrderProvider struct {
	GetOrder func(ctx context.Context, req []interface{}, rsp *Order) error
}

func (u *OrderProvider) Reference() string {
	return "OrderProvider"
}


