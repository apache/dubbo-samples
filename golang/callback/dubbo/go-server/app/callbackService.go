package main

import (
	"context"
	"fmt"
	"github.com/apache/dubbo-go-hessian2"
	"github.com/apache/dubbo-go/config"
	"sync"
	"time"
)

func init() {
	config.SetProviderService(NewCallbackService())
	hessian.RegisterPOJO(&CallbackListener{})
}

type CallbackService struct {
	listeners sync.Map
}

func NewCallbackService() *CallbackService {
	srv := &CallbackService{listeners: sync.Map{}}
	go func() {
		srv.StartCallBack()
	}()
	return srv
}

func (c *CallbackService) AddListener(ctx context.Context, req []interface{}) (*CallbackListener, error) {
	key := req[0].(string)
	listener := new(CallbackListener)
	c.listeners.Store(key, listener)
	listener.changed(c.getChanged(key)) // send notification for change
	return listener, nil
}

func (*CallbackService) getChanged(key string) string {
	return fmt.Sprintf("Changed: %s", time.Now().Format("2006-01-02 15:04:05"))
}

func (*CallbackService) Reference() string {
	return "CallbackService"
}

func (c *CallbackService) StartCallBack() {
	for {
		c.listeners.Range(func(key, value interface{}) bool {
			println("\ncallback.key==>%v, value===>%v\n", key, value)
			return true
		})
		time.Sleep(time.Second * 1)
	}
}

func println(format string, args ...interface{}) {
	fmt.Printf("\033[32;40m"+format+"\033[0m\n", args...)
}
