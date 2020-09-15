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
	"fmt"
)

import (
	"github.com/apache/dubbo-go/config"
)

import (
	hessian "github.com/apache/dubbo-go-hessian2"
)

var (
	productProvider = new(ProductProvider)
)

func init() {
	config.SetProviderService(productProvider)
	hessian.RegisterPOJO(&Product{})
}

type Product struct {
	Id       string
	Price    int
	Quantity int
}

func (Product) JavaClassName() string {
	return "com.ikurento.product.Product"
}

type ProductProvider struct{}

func (p *ProductProvider) GetProduct(ctx context.Context, req []interface{}) (*Product, error) {
	println("req:%#v", req)
	rsp := Product{"A001", 100, 100}
	println("rsp:%#v", rsp)
	return &rsp, nil
}

func (p *ProductProvider) Reference() string {
	return "ProductProvider"
}

func println(format string, args ...interface{}) {
	fmt.Printf("\033[32;40m"+format+"\033[0m\n", args...)
}
