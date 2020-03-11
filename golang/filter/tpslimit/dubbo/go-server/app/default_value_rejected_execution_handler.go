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
	"errors"
	"sync"
)

import (
	"github.com/apache/dubbo-go/common"
	"github.com/apache/dubbo-go/common/extension"
	"github.com/apache/dubbo-go/common/logger"
	"github.com/apache/dubbo-go/filter"
	"github.com/apache/dubbo-go/protocol"
)

func init() {
	/*
	 * register your custom implementation into filter.
	 * "DefaultValueHandler" is the name used in configure file, like server.yml:
	 * "UserProvider":
	 *   registry: "hangzhouzk"
	 *   protocol : "dubbo"
	 *   interface : "com.ikurento.user.UserProvider"
	 *   ... # other configuration
	 *   tps.limiter: "method-service",
	 *
	 *   tps.limit.rejected.handler: "DefaultValueHandler",
	 * So when the invocation is over the tps limitation, it will return the default value.
	 * This is a common use case.
	 */
	extension.SetRejectedExecutionHandler("DefaultValueHandler", GetDefaultValueRejectedExecutionHandlerSingleton)

}

/**
 * The RejectedExecutionHandler is used by some components,
 * e.g, ExecuteLimitFilter, GracefulShutdownFilter, TpsLimitFilter.
 * When the requests are rejected, the RejectedExecutionHandler allows you to do something.
 * You can alert the developer, or redirect those requests to another providers. It depends on what you need.
 *
 * Let's assume that you need a RejectedExecutionHandler which will return some default result if the request was rejected.
 */
type DefaultValueRejectedExecutionHandler struct {
	defaultResult sync.Map
}

func (mh *DefaultValueRejectedExecutionHandler) RejectedExecution(url common.URL, invocation protocol.Invocation) protocol.Result {
	// put your custom business here.
	logger.Error("Here is my custom rejected handler. I want to do something if the requests are rejected. ")
	// in most cases, if the request was rejected, you won't want to invoke the origin provider.
	// But if you really want to do that, you can do it like this:
	// invocation.Invoker().Invoke(invocation)

	// the ServiceKey + methodName is the key
	key := url.ServiceKey() + "#" + invocation.MethodName()
	result, loaded := mh.defaultResult.Load(key)
	if !loaded {
		// we didn't configure any default value for this invocation
		return &protocol.RPCResult{
			Err: errors.New("The request is rejected and doesn't have any default value. "),
		}
	}
	return result.(*protocol.RPCResult)
}

func GetCustomRejectedExecutionHandler() filter.RejectedExecutionHandler {
	return &DefaultValueRejectedExecutionHandler{}
}

var (
	customHandlerOnce     sync.Once
	customHandlerInstance *DefaultValueRejectedExecutionHandler
)

/**
 * the better way is designing the RejectedExecutionHandler as singleton.
 */
func GetDefaultValueRejectedExecutionHandlerSingleton() filter.RejectedExecutionHandler {
	customHandlerOnce.Do(func() {
		customHandlerInstance = &DefaultValueRejectedExecutionHandler{}
	})

	initDefaultValue()

	return customHandlerInstance
}

func initDefaultValue() {
	// setting your default value
}
