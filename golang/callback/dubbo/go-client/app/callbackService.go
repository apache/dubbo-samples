package main

import (
	"context"
	"github.com/apache/dubbo-go-hessian2"
	"github.com/apache/dubbo-go/config"
)

var callBackService = new(CallbackService)

func init() {
	config.SetConsumerService(callBackService)
	hessian.RegisterPOJO(&CallbackListener{})
}

type CallbackService struct {
	AddListener func(ctx context.Context, req []interface{}, res *CallbackListener) error
}

func (*CallbackService) Reference() string {
	return "CallbackService"
}
