package main

import (
	"context"
	"github.com/apache/dubbo-go/config"
	"time"
)

func init() {
	config.SetProviderService(new(UserProviderA))
}

type UserProviderA struct {
}

func (u *UserProviderA) GetUser(ctx context.Context, req []interface{}) (*User, error) {
	println("req:%#v", req)
	rsp := User{"GroupA", "Alex Stocks", 18, time.Now()}
	println("rsp:%#v", rsp)
	return &rsp, nil
}

func (u *UserProviderA) Reference() string {
	return "UserProviderA"
}
