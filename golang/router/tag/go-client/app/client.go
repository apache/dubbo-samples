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
	"os"
	"os/signal"
	"syscall"
	"time"
)

import (
	hessian "github.com/apache/dubbo-go-hessian2"
	"github.com/apache/dubbo-go/common/logger"
	_ "github.com/apache/dubbo-go/common/proxy/proxy_factory"
	"github.com/apache/dubbo-go/config"
	_ "github.com/apache/dubbo-go/protocol/dubbo"
	_ "github.com/apache/dubbo-go/registry/directory"
	_ "github.com/apache/dubbo-go/registry/protocol"
	gxlog "github.com/dubbogo/gost/log"

	_ "github.com/apache/dubbo-go/filter/filter_impl"

	_ "github.com/apache/dubbo-go/cluster/cluster_impl"
	_ "github.com/apache/dubbo-go/cluster/loadbalance"
	_ "github.com/apache/dubbo-go/cluster/router/tag"
	"github.com/apache/dubbo-go/common/constant"
	_ "github.com/apache/dubbo-go/config_center/zookeeper"
	_ "github.com/apache/dubbo-go/registry/zookeeper"
)

var (
	survivalTimeout int = 10e9
)

// they are necessary:
// 		export CONF_CONSUMER_FILE_PATH="xxx"
// 		export APP_LOG_CONF_FILE="xxx"
// 		export CONF_ROUTER_FILE_PATH="xxx"
func main() {
	hessian.RegisterPOJO(&User{})
	config.Load()
	time.Sleep(1e9)

	gxlog.CInfo("\n\n\nstart to test dubbo")
	user := &User{}
	ctx := context.Background()
	atm := make(map[string]string)
	atm["dubbo.tag"] = "beijing"
	atm["dubbo.force.tag"] = "true"
	ctx = context.WithValue(ctx, constant.AttachmentKey, atm)
	err := userProvider.GetUser(ctx, []interface{}{"A001"}, user)
	if err != nil {
		panic(err)
	}

	gxlog.CInfo(fmt.Sprintf("response result: %v\n", user))
	initSignal()
}

func initSignal() {
	signals := make(chan os.Signal, 1)
	// It is not possible to block SIGKILL or syscall.SIGSTOP
	signal.Notify(signals, os.Interrupt, os.Kill, syscall.SIGHUP,
		syscall.SIGQUIT, syscall.SIGTERM, syscall.SIGINT)
	for {
		sig := <-signals
		logger.Infof("get signal %s", sig.String())
		switch sig {
		case syscall.SIGHUP:
			// reload()
		default:
			time.AfterFunc(time.Duration(survivalTimeout), func() {
				logger.Warnf("app exit now by force...")
				os.Exit(1)
			})

			// The program exits normally or timeout forcibly exits.
			fmt.Println("app exit now...")
			return
		}
	}
}

var userProvider = new(UserProvider)

func init() {
	config.SetConsumerService(userProvider)
	hessian.RegisterPOJO(&User{})
}

type User struct {
	Id   string
	Name string
	Age  int32
	Time time.Time
}

type UserProvider struct {
	GetUser func(ctx context.Context, req []interface{}, rsp *User) error
}

func (u *UserProvider) Reference() string {
	return "UserProvider"
}

func (User) JavaClassName() string {
	return "com.ikurento.user.User"
}
