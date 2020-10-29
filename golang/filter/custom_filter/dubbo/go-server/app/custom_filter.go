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
	"sync"
)

import (
	"github.com/dubbogo/gost/log"
)

import (
	"github.com/apache/dubbo-go/common/extension"
	"github.com/apache/dubbo-go/filter"
	"github.com/apache/dubbo-go/protocol"
)

func init() {
	/**
	 * MyCustomFilter would be the name that used in your configuration file.
	 * it can be used as reference filter and provider filter.
	 * For example, using this filter in server, and the configure file looks like:
	 *
	 * filter: "MyCustomFilter",
	 * registries:
	 *  "demoZk":
	 *    protocol: "zookeeper"
	 *    timeout	: "3s"
	 *    address: "127.0.0.1:2181"
	 * Another important things is that you should make sure this statement executed. It usually means that
	 * this file should be imported.
	 */
	extension.SetFilter("MyCustomFilter", GetMyCustomFilter)

	// or using the singleton
	// filter.SetFilter("MyCustomFilter", GetMyCustomFilterSingleton)
}

type myCustomFilter struct{}

func (mf myCustomFilter) Invoke(ctx context.Context, invoker protocol.Invoker, invocation protocol.Invocation) protocol.Result {
	// the logic put here...
	// you can get many params in url. And the invocation provides more information about
	url := invoker.GetUrl()
	serviceKey := url.ServiceKey()
	gxlog.CInfo("Here is the my custom filter. The service is invoked: %s", serviceKey)
	return invoker.Invoke(ctx, invocation)
}

func (mf myCustomFilter) OnResponse(ctx context.Context, result protocol.Result, invoker protocol.Invoker, invocation protocol.Invocation) protocol.Result {
	// you can do something here with result
	gxlog.CInfo("Got result!")
	return result
}

func GetMyCustomFilter() filter.Filter {
	return &myCustomFilter{}
}

var (
	myFilterOnce sync.Once
	myFilter     *myCustomFilter
)

/**
 * In most cases, the filter would be designed as singleton.
 */
func GetMyCustomFilterSingleton() filter.Filter {
	myFilterOnce.Do(func() {
		myFilter = &myCustomFilter{}
	})
	return myFilter
}
