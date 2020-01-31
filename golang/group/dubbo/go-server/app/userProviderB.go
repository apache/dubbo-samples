package main

import (
	"context"
	"github.com/apache/dubbo-go/config"
	"time"
)

func init() {
	config.SetProviderService(new(UserProviderB))
}

type UserProviderB struct {
}

func (u *UserProviderB) GetUser(ctx context.Context, req []interface{}) (*User, error) {
	rsp := User{"GroupB", "Alex Stocks", 18, time.Now()}
	println("rsp:%#v", rsp)
	return &rsp, nil
}

func (u *UserProviderB) Reference() string {
	return "UserProviderB"
}
